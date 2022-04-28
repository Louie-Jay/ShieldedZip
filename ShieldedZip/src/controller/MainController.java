package controller;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import view.CompressWindow;
import view.ExtractWindow;
import view.MainWindow;

public class MainController {
	
	MainWindow parent = null;
	public MainController(MainWindow prmParent) {
		parent = prmParent;
	}
	
	public void openWindow(String prmOperation) {
		switch (prmOperation) {
		case "Compress":
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						CompressWindow frame = new CompressWindow(parent);
						parent.setVisible(false);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			break;
		case "Extract":
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ExtractWindow frame = new ExtractWindow(parent);
						parent.setVisible(false);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			break;
		default:
			break;
		}
		
	}
	
	public void exit() {
		
		int input = JOptionPane.showConfirmDialog(parent, "Are you sure you want to exit the program?");
		if (input == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
		
	}
	
	public void about() {
		JOptionPane.showMessageDialog(parent, "<html><strong>Created by Louie Jay F. Lomibao.</strong><br><br>"
				+ "Attributions:<br>Add Document icon by Creative Freedom (Backlink:www.creativefreedom.co.uk)<br>"
				+ "App x compress icon by Papirus Development Team<br>"
				+ "Extract object icon by Custom Icon Design (Backlink:www.customicondesign.com)<br>"
				+ "Shield icon by Icojam<br>"
				+ "Zip file icon by Rokey<br>"
				+ "Tools:www.logomakr.com/5K0BxH</html>");
	}

}
