package logic;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import graphics.Textbox;

public abstract class Scene extends JPanel {

	private static final long serialVersionUID = 1L;
	private Textbox textbox = null;

	public Scene() {
		init();
	}

	public void init() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F2) {
					System.out.println("Test");
					Program.toggleFullscreen();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});

	}

	@Override
	public void paintComponent(Graphics g) {
		if (!hasFocus()) {
			grabFocus();
		}
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		g2d.setRenderingHints(rh);

	}

	public Font loadFont(String resource, float size) {
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResource("/assets/fonts/" + resource).openStream());
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			return font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage loadImage(String resource) {
		try {
			return ImageIO.read(new File("src/assets/images/" + resource));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage loadImage(String resource, float scale) {
		BufferedImage unscaled = loadImage(resource);
		BufferedImage resized = new BufferedImage((int) (scale * unscaled.getWidth(null)),
				(int) (scale * unscaled.getHeight(null)), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resized.createGraphics();
		g.drawImage(unscaled, 0, 0, resized.getWidth(null), resized.getHeight(null), null);
		g.dispose();
		return resized;
	}

	public Textbox getTextbox() {
		return textbox;
	}

	public void setTextbox(Textbox textbox) {
		if (this.textbox == null || textbox == null) {
			this.textbox = textbox;
			return;
		}
		if (!this.textbox.equals(textbox)) {
			this.textbox = textbox;
		}
	}

	public void setTextbox(Person p, String[] text) {
		setTextbox(new Textbox(p, text, this));
	}

	public abstract void nextScene();

	public String[] getBackwardsText() {
		String[] text = { "There's nothing for me this way[2].[2].[2]." };
		return text;
	}
	
	public BufferedImage rotate90(BufferedImage bi) {
	    int width = bi.getWidth();
	    int height = bi.getHeight();
	    BufferedImage biFlip = new BufferedImage(height, width, bi.getType());
	    for(int i=0; i<width; i++)
	        for(int j=0; j<height; j++)
	            biFlip.setRGB(height-1-j, width-1-i, bi.getRGB(i, j));
	    return biFlip;
	}


}
