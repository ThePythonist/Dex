package genericscenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import logic.Person;
import logic.Scene;

public abstract class Slides extends Scene {

	private static final long serialVersionUID = 1L;
	private Person character;
	private int horizontalSpeed, verticalSpeed, slide = 0, lastDir;
	private boolean canMove = true;
	public static final int DIR_LEFT = 0, DIR_RIGHT = 1, DIR_UP = 2, DIR_DOWN = 3;

	public int getSlideMove() {
		if (character.getX() <= 0) {
			return DIR_LEFT;
		} else if (character.getY() <= 0) {
			return DIR_UP;
		} else if (character.getX() > getWidth() - character.getFacingImage().getWidth(null)) {
			return DIR_RIGHT;
		} else if (character.getY() > getHeight() - character.getFacingImage().getHeight(null)) {
			return DIR_DOWN;
		}
		return -1;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (canMove){
			character.setLastDirection(lastDir);
		}
		if (horizontalSpeed != 0 && verticalSpeed == 0) {
			if (horizontalSpeed < 0) {
				character.setLastDirection(Person.IM_LEFT);
			} else {
				character.setLastDirection(Person.IM_RIGHT);
			}
		}
		if (horizontalSpeed == 0 && verticalSpeed != 0) {
			if (verticalSpeed < 0) {
				character.setLastDirection(Person.IM_BACKWARD);
			} else {
				character.setLastDirection(Person.IM_FORWARD);
			}
		}
	}

	public void move() {
		if (canMove) {
			character.moveHorizontal(horizontalSpeed);
			character.moveVertical(verticalSpeed);
		}
	}

	public void initMovement() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					horizontalSpeed = 0;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					verticalSpeed = 0;
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					horizontalSpeed = -character.getWalkSpeed();
					lastDir = Person.IM_LEFT;
				} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					horizontalSpeed = character.getWalkSpeed();
					lastDir = Person.IM_RIGHT;
				} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					verticalSpeed = -character.getWalkSpeed();
					lastDir = Person.IM_BACKWARD;
				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					verticalSpeed = character.getWalkSpeed();
					lastDir = Person.IM_FORWARD;
				}
			}
		});
	}

	public Person getCharacter() {
		return character;
	}

	public void setCharacter(Person character) {
		this.character = character;
	}

	public int getHorizontalSpeed() {
		return horizontalSpeed;
	}

	public void setHorizontalSpeed(int horizontalSpeed) {
		this.horizontalSpeed = horizontalSpeed;
	}

	public int getVerticalSpeed() {
		return verticalSpeed;
	}

	public void setVerticalSpeed(int verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}

	public int getSlide() {
		return slide;
	}

	public void setSlide(int slide) {
		this.slide = slide;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

}
