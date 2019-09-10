package logic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Person implements Positioned{

	public static final int IM_FORWARD = 0, IM_BACKWARD = 1, IM_LEFT = 2, IM_RIGHT = 3, AX_HORIZONTAL = 4,
			AX_VERTICAL = 5, COLLISION_HORIZONTAL = 6, COLLISION_VERTICAL = 7;
	private String name, speechSound;
	private Image forward, left, right, backward, icon;
	private int x, y, lastDirection, walkSpeed;
	public static final int DEFAULT_WALK_SPEED = 4;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getForward() {
		return forward;
	}

	public void setForward(Image forward) {
		this.forward = forward;
	}

	public Image getImage(int image) {
		return image == IM_FORWARD ? getForward()
				: (image == IM_BACKWARD ? getBackward() : (image == IM_LEFT ? getLeft() : getRight()));
	}

	public void setImage(int imageRef, Image image) {
		if (imageRef == IM_FORWARD) {
			setForward(image);
		} else if (imageRef == IM_BACKWARD) {
			setBackward(image);
		} else if (imageRef == IM_LEFT) {
			setLeft(image);
		} else {
			setRight(image);
		}
	}

	public Image getLeft() {
		return left;
	}

	public void setLeft(Image left) {
		this.left = left;
	}

	public Image getRight() {
		return right;
	}

	public void setRight(Image right) {
		this.right = right;
	}

	public Image getBackward() {
		return backward;
	}

	public void setBackward(Image backward) {
		this.backward = backward;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public String getSpeechSound() {
		return speechSound;
	}

	public void setSpeechSound(String speechSound) {
		this.speechSound = speechSound;
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

	public void draw(Graphics g) {
		g.drawImage(getFacingImage(), x, y, null);
	}

	public int getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(int lastDirection) {
		this.lastDirection = lastDirection;
	}

	public Image getFacingImage() {
		return getImage(lastDirection);
	}

	public void moveHorizontal(int distance) {
		setX(x + distance);
	}

	public void moveVertical(int distance) {
		setY(y + distance);
	}

	public void move(int axis, int distance) {
		if (axis == AX_HORIZONTAL) {
			moveHorizontal(distance);
		} else {
			moveVertical(distance);
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

	public boolean collidesWith(Person p, int verticalOffset, int horizontalOffset) {
		if (getX() + getFacingImage().getWidth(null) + horizontalOffset >= p.getX()) {
			if (getX() + horizontalOffset <= p.getX() + p.getFacingImage().getWidth(null)) {
				if (getY() + getFacingImage().getHeight(null) + verticalOffset >= p.getY()) {
					if (getY() + verticalOffset <= p.getY() + p.getFacingImage().getHeight(null)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean collidesWith(ObjectCollider p, int verticalOffset, int horizontalOffset) {
		if (getX() + getFacingImage().getWidth(null) + horizontalOffset >= p.getX()) {
			if (getX() + horizontalOffset <= p.getX() + p.getWidth()) {
				if (getY() + getFacingImage().getHeight(null) + verticalOffset >= p.getY()) {
					if (getY() + verticalOffset <= p.getY() + p.getHeight()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public int getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(int walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public boolean equals(Person arg0) {
		return arg0.name == name;
	}

}
