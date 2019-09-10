package e1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import genericscenes.Slides;
import logic.NoClipObject;
import logic.ObjectCollider;
import logic.Program;
import people.Dexter;
import sound.Sound;

public class IntoTheHouse extends Slides {

	private static final long serialVersionUID = 1L;

	private int roadWidth = 200;
	private Sound bgMusic = new Sound("music/blood theme.wav");
	private ObjectCollider house = new ObjectCollider("scenery/donovanhouse.png", 5.0f, -1, -1);
	private Dexter dexter = new Dexter();
	private NoClipObject<Dexter> donovanObject;;
	private int speech = 0;
	private long startTime = -1;

	public IntoTheHouse() {
		setBackground(new Color(0, 50, 0));
		bgMusic.play(-1);
		donovanObject = new NoClipObject<Dexter>(rotate90(loadImage("characters/donovan/wired.png", 3)), dexter, 0, 0);
		donovanObject.setXOffset((int) (-donovanObject.getWidth() * 0.6));
		donovanObject.setYOffset((int) (dexter.getFacingImage().getHeight(null) - donovanObject.getHeight() * 0.9));
		dexter.setX(-dexter.getFacingImage().getWidth(null));
		setCharacter(dexter);
		initMovement();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (house.getX() < 0) {
			house.setX(getWidth() - house.getWidth());
			house.setY(getHeight() / 2 - house.getHeight());
		}
		if (dexter.getX() == -dexter.getFacingImage().getWidth(null)) {
			dexter.setX(10);
			dexter.setY(getHeight() / 2 - dexter.getFacingImage().getHeight(null));
		}

		if (getSlideMove() == Slides.DIR_LEFT) {
			dexter.setX(0);
		}
		if (getSlideMove() == Slides.DIR_DOWN) {
			dexter.setY(getHeight() - dexter.getFacingImage().getHeight(null));
		}
		if (getSlideMove() == Slides.DIR_UP) {
			dexter.setY(1);
		}
		if (getSlideMove() == Slides.DIR_RIGHT) {
			dexter.setX(getWidth() - dexter.getFacingImage().getWidth(null));
		}
		if (dexter.collidesWith(house, getVerticalSpeed(), getHorizontalSpeed())) {
			setVerticalSpeed(0);
			setHorizontalSpeed(0);
			nextScene();
		}

		if (dexter.getX() >= 500 && speech == 0) {
			dexter.setWalkSpeed(0);
			String[] text = { "You have to listen and do what I say.[3] You should know that.[3] It's important." };
			setTextbox(dexter, text);
			speech = 1;
		}

		g.setColor(new Color(51, 51, 51));
		g.fillRect(0, (getHeight() - roadWidth) / 2, getWidth(), roadWidth);

		house.draw(g);
		move();
		donovanObject.draw(g);
		dexter.draw(g);

		if (getTextbox() != null) {
			getTextbox().draw(g, getSize());
			if (getTextbox().isEnded()) {
				setTextbox(null);
			}
		} else {
			if (speech == 1) {
				if (startTime < 0) {
					startTime = System.currentTimeMillis();
				} else if (System.currentTimeMillis() >= startTime + 1000) {
					donovanObject.setImage(loadImage("characters/donovan/wired.png", 3));
					donovanObject.setXOffset(-dexter.getFacingImage().getWidth(null));
					donovanObject.setYOffset(0);
				}
				if (System.currentTimeMillis() >= startTime + 3000) {
					speech++;
				}
			} else if (speech == 2) {
				String[] text = { "In the house." };
				setTextbox(dexter, text);
				speech = 3;
			} else if (speech == 3) {
				dexter.setWalkSpeed(Dexter.DEFAULT_WALK_SPEED);
			}
		}

		repaint();
	}

	@Override
	public void nextScene() {
		bgMusic.stop();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Program.ex.play(new TheHouse());
			}
		});
	}

}
