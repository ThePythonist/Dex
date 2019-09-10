package logic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectCollider implements Positioned{

	private BufferedImage image;
	private int x, y;

	public ObjectCollider(String image, int x, int y) {
		setImage(loadImage(image));
		setX(x);
		setY(y);
	}

	public ObjectCollider(String image, float scale, int x, int y) {
		setImage(loadImage(image, scale));
		setX(x);
		setY(y);
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

	public boolean collidesWith(ObjectCollider p, int verticalOffset, int horizontalOffset) {
		if (getX() + getWidth() + horizontalOffset >= p.getX()) {
			if (getX() + horizontalOffset <= p.getX() + p.getWidth()) {
				if (getY() + getHeight() + verticalOffset >= p.getY()) {
					if (getY() + verticalOffset <= p.getY() + p.getHeight()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void draw(Graphics g) {
		g.drawImage(image, getX(), getY(), null);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return image.getWidth(null);
	}

	public int getHeight() {
		return image.getHeight(null);
	}

}
