package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import model.CompressModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import view.CompressWindow;
import view.MainWindow;

public class CompressController {
	
	private CompressWindow parent;
	private JPanel pnlFiles;
	private CompressModel model;
	private boolean customEnabled;
	private boolean passwordEnabled;
	private JComboBox<String> cbxCMethod;
	private JComboBox<String> cbxCLevel;
	private JComboBox<String> cbxEMethod;
	private JComboBox<String> cbxCStrength;
	private String errorMsg;
	private JPasswordField txtPassword;
	
	public CompressController(CompressWindow prmParent, JPanel prmPnlFiles, JPasswordField prmPassword) {
		model = new CompressModel();
		parent = prmParent;
		pnlFiles = prmPnlFiles;
		customEnabled = false;
		passwordEnabled = false;
		errorMsg = "Unknown";
		txtPassword = prmPassword;
	}
	
	public void compress() {
		
		if (model.getFiles().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "Nothing to compress, please add files.", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			Object input = JOptionPane.showInputDialog(parent, "Zip filename: ", "Input", JOptionPane.QUESTION_MESSAGE);
			
			if (input != null) {
				String zipName = String.valueOf(input);
				ZipParameters parameters = new ZipParameters();
				
				if (customEnabled) {
					parameters.setCompressionMethod(this.getCMethod());
					parameters.setCompressionLevel(this.getCLevel());
					parameters.setEncryptionMethod(this.getEMethod());
					parameters.setAesKeyStrength(this.getCStrength());
				}else {
					parameters.setCompressionMethod(CompressionMethod.DEFLATE);
					parameters.setCompressionLevel(CompressionLevel.NORMAL);
					if (passwordEnabled) {
						parameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
						parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
					}
					
					
				}
				if (passwordEnabled) {
					parameters.setEncryptFiles(true);
					if (model.compress(zipName, parameters, txtPassword.getPassword())) {
						this.clearSelectedFiles();
						JOptionPane.showMessageDialog(parent, "<html>Compression completed!<br>Saved at: "+this.getDestination()+"</html>", "Success", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(parent, "<html>Compression failed!<br>Error: "+this.getErrorMsg()+"</html>", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					parameters.setEncryptFiles(false);
					if (model.compress(zipName, parameters)) {
						this.clearSelectedFiles();
						JOptionPane.showMessageDialog(parent, "<html>Compression completed!<br>Saved at: "+this.getDestination()+"</html>", "Success", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(parent, "<html>Compression failed!<br>Error: "+this.getErrorMsg()+"</html>", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	
	
	private AesKeyStrength getCStrength() {
		String strength = String.valueOf(cbxCStrength);
		switch (strength) {
		case "256":
			return AesKeyStrength.KEY_STRENGTH_256;
		default:
			return AesKeyStrength.KEY_STRENGTH_128;
		}
	}
	
	private EncryptionMethod getEMethod() {
		String method = String.valueOf(cbxEMethod);
		switch (method) {
		case "AES":
			return EncryptionMethod.AES;
		case "NONE":
			return EncryptionMethod.NONE;
		case "STANDARD_STRONG":
			return EncryptionMethod.ZIP_STANDARD_VARIANT_STRONG;
		default:
			return EncryptionMethod.ZIP_STANDARD;
		}
	}
	
	private CompressionMethod getCMethod() {
		String method = String.valueOf(cbxCMethod);
		switch (method) {
		case "AES_INTERNAL":
			return CompressionMethod.AES_INTERNAL_ONLY;
		case "DEFLATE":
			return CompressionMethod.DEFLATE;
		default:
			return CompressionMethod.STORE;
		}
	}

	private CompressionLevel getCLevel() {
		String level = String.valueOf(cbxCLevel);
		switch (level) {
		case "FASTEST":
			return CompressionLevel.FASTEST;
		case "NORMAL":
			return CompressionLevel.NORMAL;
		case "MAXIMUM":
			return CompressionLevel.MAXIMUM;
		case "ULTRA":
			return CompressionLevel.ULTRA;
		default:
			return CompressionLevel.FAST;
		}
	}
	
	
	public void addCbx(HashMap<String, JComboBox<String>> prmComponent) {
		cbxCMethod = prmComponent.get("cMethod");
		cbxCLevel = prmComponent.get("cLevel");
		cbxEMethod = prmComponent.get("eMethod");
		cbxCStrength = prmComponent.get("cStrength");
	}
	
	public void setDestination() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int input = fc.showOpenDialog(parent);
		
		if (input == JFileChooser.APPROVE_OPTION) {
			try {
				model.setDestination(fc.getSelectedFile());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, e.getMessage().substring(0, 15)+"...", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void updateLocation(JTextField prmLocation) {
		prmLocation.setText(this.getDestination());
	}
	
	public String getDestination() {
		return model.getDestination();
	}
	
	public void addFilesOnDrag() {
		new FileDrop(pnlFiles, new FileDrop.Listener()
	      {   public void filesDropped( java.io.File[] files )
	          {   
	          	CompressController.this.loadSelectedFiles(files);
	          }   
	      });
	}
	
	public void addFilesOnClick() {
		pnlFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				int input = fc.showOpenDialog(parent);
				
				if (input == JFileChooser.APPROVE_OPTION) {
					CompressController.this.loadSelectedFiles(fc.getSelectedFiles());
				}
			}
		});
	}
	
	private void loadSelectedFiles(File[] prmSelectedFiles) {
		ArrayList<File> selectedFiles = new ArrayList<File>();
		Collections.addAll(selectedFiles, prmSelectedFiles);
		model.getFiles().removeAll(selectedFiles);
		model.getFiles().addAll(selectedFiles);
		
		pnlFiles.removeAll();
		pnlFiles.revalidate();
		pnlFiles.repaint();
		
		for (File file : model.getFiles()) {
			pnlFiles.add(new JLabel(file.getName(),FileSystemView.getFileSystemView().getSystemIcon(file),SwingConstants.LEFT));
		}
	}
	
	public void clearSelectedFiles() {
		pnlFiles.removeAll();
		pnlFiles.revalidate();
		pnlFiles.repaint();
		model.clearFiles();
	}
	
	public void back(MainWindow prmMainParent) {
		prmMainParent.setVisible(true);
		parent.setVisible(false);
	}
	
	public void showCustomSettings(String prmCommand, JPanel prmCustomSettings) {
		
		switch (prmCommand) {
		case "Default":
			customEnabled = false;
			prmCustomSettings.setVisible(false);
			break;
		case "Custom":
			prmCustomSettings.setVisible(true);
			customEnabled = true;
			break;
		default:
			break;
		}
	}
	
	public void enablePassword(JCheckBox prmEnablePassword,JPasswordField prmPassword) {
		
		if (prmEnablePassword.isSelected()) {
			passwordEnabled = true;
			prmPassword.setEnabled(true);
		}else {
			passwordEnabled = false;
			prmPassword.setEnabled(false);
		}
		prmPassword.setText("");
	}
	
	public String getErrorMsg() {
		return model.getErrorMsg();
	}
	
}
