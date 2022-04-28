package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;

import model.ExtractModel;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import view.ExtractWindow;
import view.MainWindow;

public class ExtractController {
	
	private ExtractWindow parent;
	private JPanel pnlFiles;
	private ExtractModel model;
	private String errorMsg;
	
	public ExtractController(ExtractWindow prmParent, JPanel prmPnlFiles) {
		model = new ExtractModel();
		parent = prmParent;
		pnlFiles = prmPnlFiles;
		errorMsg = "Unknown";
	}
	
	public void extract() {

		boolean success = false;
		
		for(int i = 0; i <= ((model.getFiles().size())-1); i++) {
			if (isPasswordProtected(i)) {
				JPasswordField pf = new JPasswordField();
				int okCxl = JOptionPane.showConfirmDialog(parent, pf, "Input: \""+model.getFiles().get(i).getName()+"\" password:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (okCxl == JOptionPane.OK_OPTION) {
					if (model.extract(model.getFiles().get(i), pf.getPassword())) {
						success = true;
					}else {
						success = false;
						break;
					}
				}else {
					break;
				}
			}else {
				if (model.extract(model.getFiles().get(i))) {
					success = true;
				}else {
					break;
				}
			}
		}
		
		if (success) {
			this.clearSelectedFiles();
			JOptionPane.showMessageDialog(parent, "<html>Extraction completed!<br>Saved at: "+this.getDestination()+"</html>", "Success", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(parent, "<html>Compression failed!<br>Error: "+this.getErrorMsg()+"</html>", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean isPasswordProtected(int prmIndex) {
		ZipFile curZip = new ZipFile(model.getFiles().get(prmIndex).getAbsolutePath());
		try {
			if (curZip.isEncrypted()) {
				return true;
			}else {
				return false;
			}
		} catch (ZipException e) {
			String parseError = e.getMessage().substring(e.getMessage().indexOf("("), e.getMessage().indexOf(")"));
			errorMsg = parseError+")...";
			return false;
		}
	}
	
	public String getErrorMsg() {
		return model.getErrorMsg();
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
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parent, model.getErrorMsg()+"...", "Error", JOptionPane.ERROR_MESSAGE);
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
	          	ExtractController.this.loadSelectedFiles(files);
	          }   
	      });
	}
	
	public void addFilesOnClick() {
		pnlFiles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				fc.setFileFilter(new FileNameExtensionFilter("Zip Files", "zip"));
				int input = fc.showOpenDialog(parent);
				
				if (input == JFileChooser.APPROVE_OPTION) {
					ExtractController.this.loadSelectedFiles(fc.getSelectedFiles());
				}
			}
		});
	}
	
	private boolean validFile(File[] prmSelectedFiles) {
		for (File file : prmSelectedFiles) {
			String ext = FilenameUtils.getExtension(file.getName());
			if (!ext.equals("zip")) {
				return false;
			}
		}
		return true;
	}
	
	private void loadSelectedFiles(File[] prmSelectedFiles) {
		if (this.validFile(prmSelectedFiles)) {
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
		}else {
			JOptionPane.showMessageDialog(parent, "<html>Invalid File Extension <br>\".zip\" extension supported only.</html>", "Error", JOptionPane.ERROR_MESSAGE);
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

}
