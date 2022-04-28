package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ExtractController;

public class ExtractWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtLocation;
	private JPanelFiles pnlFiles;
	private ExtractController controller;
	
	public ExtractWindow(MainWindow prmMainParent) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/res/shieldedzip_small.png")));
		pnlFiles = new JPanelFiles();
		pnlFiles.setToolTipText("Drag files here or Click to add files.");
		controller = new ExtractController(this, pnlFiles);
		setTitle("ShieldedZip - Extract Files");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(prmMainParent);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panel.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnExtractTo = new JButton("Extract To:");
		btnExtractTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setDestination();
				controller.updateLocation(txtLocation);
			}
		});
		btnExtractTo.setPreferredSize(new Dimension(87, 25));
		panel_3.add(btnExtractTo);
		
		txtLocation = new JTextField(controller.getDestination());
		txtLocation.setEnabled(false);
		panel_3.add(txtLocation);
		txtLocation.setColumns(11);
		
		JButton btnBack = new JButton("Back");
		btnBack.setPreferredSize(new Dimension(55, 25));
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
		panel_3.add(btnClear);
		panel_3.add(btnBack);
		
		JButton btnExtract = new JButton("Extract");
		btnExtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.extract();
			}
		});
		btnExtract.setPreferredSize(new Dimension(67, 25));
		panel_3.add(btnExtract);
		
		controller.addFilesOnClick();
		controller.addFilesOnDrag();
		
		contentPane.add(pnlFiles, BorderLayout.CENTER);
		pnlFiles.setLayout(new GridLayout(6, 6, 5, 5));
		
	}

}
