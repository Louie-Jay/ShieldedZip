package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import controller.CompressController;

public class CompressWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtLocation;
	private JPasswordField txtPassword;
	private JPanelFiles pnlFiles;
	private JPanel pnlCustomSettings;
	
	private final String encryptMethods[] = {"NONE","AES","STANDARD","STANDARD_STRONG"};
	private final String encryptStrengths[] = {"128","256"};
	private final String compressLvls[] = {"NORMAL", "FAST","FASTEST","MAXIMUM","ULTRA"};
	private final String compressMethods[] = {"DEFLATE","AES_INTERNAL","STORE"};
	private JComboBox<String> chkEncryptMethod;
	private JComboBox<String> cbxCompressMethod;
	private JComboBox<String> cbxCompressLevel;
	private JComboBox<String> cbxCompressStrength;
	private HashMap<String, JComboBox<String>> hashCbx;
	private CompressController controller;
	

	public CompressWindow(MainWindow prmMainParent) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/res/shieldedzip_small.png")));
		pnlFiles = new JPanelFiles();
		pnlFiles.setToolTipText("Drag files here or Click to add files.");
		txtPassword = new JPasswordField();
		controller = new CompressController(this,pnlFiles,txtPassword);
		hashCbx = new HashMap<String, JComboBox<String>>();
		setTitle("ShieldedZip - Compress Files");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(prmMainParent);
		JPanel panel = new JPanel();
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Compression Settings:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_12.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_4.add(panel_12);
		
		ButtonGroup btnGroup = new ButtonGroup();
		
		JRadioButton chkDefault = new JRadioButton("Default");
		chkDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showCustomSettings(e.getActionCommand(),pnlCustomSettings);
			}
		});
		panel_12.add(chkDefault);
		
		JRadioButton chkCustom = new JRadioButton("Custom");
		chkCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showCustomSettings(e.getActionCommand(),pnlCustomSettings);
			}
		});
		panel_12.add(chkCustom);
		
		btnGroup.add(chkDefault);
		btnGroup.add(chkCustom);
		chkDefault.setSelected(true);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		FlowLayout flowLayout_1 = (FlowLayout) panel_11.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_4.add(panel_11);
		
		JLabel lblNewLabel_4 = new JLabel("Password:");
		panel_11.add(lblNewLabel_4);
		
		
		lblNewLabel_4.setLabelFor(txtPassword);
		panel_11.add(txtPassword);
		txtPassword.setColumns(15);
		txtPassword.setEnabled(false);
		
		JCheckBox chkPassword = new JCheckBox("Password Protected");
		chkPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.enablePassword(chkPassword,txtPassword);
			}
		});
		panel_11.add(chkPassword);
		
		pnlCustomSettings = new JPanel();
		pnlCustomSettings.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Custom Settings:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(pnlCustomSettings);
		pnlCustomSettings.setLayout(new GridLayout(1, 2, 5, 0));
		
		JPanel panel_5 = new JPanel();
		pnlCustomSettings.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("Compression Method: ");
		panel_7.add(lblNewLabel);
		
		cbxCompressMethod = new JComboBox<String>(compressMethods);
		cbxCompressMethod.setMaximumSize(new Dimension(32767, 25));
		cbxCompressMethod.setMinimumSize(new Dimension(28, 25));
		cbxCompressMethod.setPreferredSize(new Dimension(28, 25));
		lblNewLabel.setLabelFor(cbxCompressMethod);
		panel_7.add(cbxCompressMethod);
		
		JPanel panel_8 = new JPanel();
		panel_5.add(panel_8);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Compression Level:");
		panel_8.add(lblNewLabel_1);
		
		cbxCompressLevel = new JComboBox<String>(compressLvls);
		cbxCompressLevel.setPreferredSize(new Dimension(28, 25));
		cbxCompressLevel.setMinimumSize(new Dimension(28, 25));
		cbxCompressLevel.setMaximumSize(new Dimension(32767, 25));
		panel_8.add(cbxCompressLevel);
		
		JPanel panel_6 = new JPanel();
		pnlCustomSettings.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		pnlCustomSettings.setVisible(false);
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_2 = new JLabel("Encryption Method: ");
		panel_9.add(lblNewLabel_2);
		
		chkEncryptMethod = new JComboBox<String>(encryptMethods);
		chkEncryptMethod.setMinimumSize(new Dimension(28, 25));
		chkEncryptMethod.setMaximumSize(new Dimension(32767, 25));
		chkEncryptMethod.setPreferredSize(new Dimension(28, 25));
		lblNewLabel_2.setLabelFor(chkEncryptMethod);
		panel_9.add(chkEncryptMethod);
		
		JPanel panel_10 = new JPanel();
		panel_6.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("Encryption Strength:");
		panel_10.add(lblNewLabel_3);
		
		cbxCompressStrength = new JComboBox<String>(encryptStrengths);
		cbxCompressStrength.setPreferredSize(new Dimension(28, 25));
		cbxCompressStrength.setMinimumSize(new Dimension(28, 25));
		cbxCompressStrength.setMaximumSize(new Dimension(32767, 25));
		panel_10.add(cbxCompressStrength);
		
		hashCbx.put("cMethod", cbxCompressMethod);
		hashCbx.put("cLevel", cbxCompressLevel);
		hashCbx.put("eMethod", chkEncryptMethod);
		hashCbx.put("cStrength", cbxCompressStrength);
		
		controller.addCbx(hashCbx);
		controller.addFilesOnClick();
		controller.addFilesOnDrag();
		
		contentPane.add(pnlFiles, BorderLayout.CENTER);
		pnlFiles.setLayout(new GridLayout(6, 6, 5, 5));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_2.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnCompressTo = new JButton("Compress to:");
		btnCompressTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setDestination();
				controller.updateLocation(txtLocation);
			}
		});
		btnCompressTo.setToolTipText("Select output directory of compressed file(s) .zip .");
		btnCompressTo.setPreferredSize(new Dimension(97, 25));
		panel_2.add(btnCompressTo);
		
		txtLocation = new JTextField(controller.getDestination());
		txtLocation.setEnabled(false);
		txtLocation.setToolTipText("Output directory of the compressed file(s).");
		panel_2.add(txtLocation);
		txtLocation.setColumns(11);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.back(prmMainParent);
			}
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clearSelectedFiles();
			}
		});
		btnClear.setToolTipText("Clear added file(s).");
		panel_2.add(btnClear);
		btnBack.setPreferredSize(new Dimension(55, 25));
		panel_2.add(btnBack);
		
		JButton btnCompress = new JButton("Compress");
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.compress();
			}
		});
		
		btnCompress.setPreferredSize(new Dimension(79, 25));
		panel_2.add(btnCompress);
	}

}
