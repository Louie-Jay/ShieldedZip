package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controller.MainController;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Toolkit;

public class MainWindow extends JFrame {
	
	private MainController controller;
	private JPanel contentPane;

	public MainWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/res/shieldedzip_small.png")));
		controller = new MainController(this);
		setMinimumSize(new Dimension(360, 240));
		setMaximumSize(new Dimension(360, 240));
		setPreferredSize(new Dimension(360, 240));
		setResizable(false);
		setTitle("ShieldedZip");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Choose an operation:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 2, 5, 0));
		
		JButton btnCompressWindow = new JButton("Compress Files");
		btnCompressWindow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCompressWindow.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCompressWindow.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCompressWindow.setIcon(new ImageIcon(MainWindow.class.getResource("/res/compress_raw.png")));
		btnCompressWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openWindow("Compress");
			}
		});
		panel.add(btnCompressWindow);
		
		JButton btnExtractWindow = new JButton("Extract Files");
		btnExtractWindow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExtractWindow.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExtractWindow.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnExtractWindow.setIcon(new ImageIcon(MainWindow.class.getResource("/res/extract_raw.png")));
		btnExtractWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openWindow("Extract");
			}
		});
		panel.add(btnExtractWindow);
		
		JLabel lblNewLabel = new JLabel("Welcome to ShieldedZip!\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.about();
			}
		});
		btnAbout.setPreferredSize(new Dimension(61, 25));
		btnAbout.setMinimumSize(new Dimension(61, 25));
		btnAbout.setMaximumSize(new Dimension(61, 25));
		panel_1.add(btnAbout);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});
		btnExit.setPreferredSize(new Dimension(51, 25));
		btnExit.setMaximumSize(new Dimension(51, 25));
		btnExit.setMinimumSize(new Dimension(51, 25));
		panel_1.add(btnExit);
	}

}
