package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPanelFiles extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		try {
			if (getComponentCount() == 0) {
				BufferedImage img = ImageIO.read(getClass().getResource("/res/addfilebg.png"));
				int x = (this.getWidth() - img.getWidth(null)) / 2;
				int y = (this.getHeight() - img.getHeight(null)) / 2;

				g.drawImage(img, x, y, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
