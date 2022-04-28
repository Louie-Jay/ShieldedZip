package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.MainWindow;

public class Main {
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		loadLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow frame = new MainWindow();
				frame.setVisible(true);
				
			}
		});
		
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		double seconds = (double)totalTime / 1_000_000_000.0;
		System.out.println("Took "+(endTime - startTime)+ "ns" + " (" +seconds+ " seconds)");
		 
	}
	
	private static void loadLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
