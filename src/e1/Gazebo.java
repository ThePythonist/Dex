package e1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import genericscenes.Slides;
import logic.ObjectCollider;
import logic.Person;
import logic.Program;
import people.ChoirBoy;
import people.Dexter;
import people.DexterInternal;
import people.Donovan;
import sound.Sound;

public class Gazebo extends Slides {

	private static final long serialVersionUID = 1L;
	private Donovan donovan = new Donovan();
	private Dexter dexter = new Dexter();
	private int roadWidth = 200;
	private ObjectCollider gazebo, donovansCar;
	private ChoirBoy[] choirboys = new ChoirBoy[6];
	private boolean spokenToDonovan = false, seenDonovansCar = false;
	private Sound bgMusic, choirMusic;

	public Gazebo() {
		bgMusic = new Sound("music/blood theme.wav");
		choirMusic = new Sound("music/bach magnificat.wav");
		choirMusic.changeVolume(10);
		bgMusic.play(-1);
		setBackground(new Color(0, 50, 0));
		dexter.setX(-dexter.getFacingImage().getWidth(null));
		gazebo = new ObjectCollider("scenery/gazebo.png", 5, 0, 0);
		donovansCar = new ObjectCollider("cars/donovan.png", 5, 0, 0);
		setCharacter(dexter);
		initMovement();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCanMove(true);
		if (dexter.getX() == -dexter.getFacingImage().getWidth(null)) {
			dexter.setX(1);
			dexter.setY(getHeight() / 2 - dexter.getFacingImage().getHeight(null));
		}

		if (getSlide() == 0) {
			if (getSlideMove() == Slides.DIR_LEFT) {
				dexter.setX(1);
				setTextbox(new DexterInternal(), getBackwardsText());
			}
			if (getSlideMove() == Slides.DIR_DOWN) {
				dexter.setY(getHeight() - dexter.getFacingImage().getHeight(null));
			}
			if (getSlideMove() == Slides.DIR_UP) {
				dexter.setY(getHeight() - dexter.getFacingImage().getHeight(null));
				setSlide(1);
				bgMusic.pause();
				choirMusic.play(-1);
				donovan.setX((getWidth() - donovan.getFacingImage().getWidth(null)) / 2);
				donovan.setY((getHeight() - donovan.getFacingImage().getHeight(null)) / 2);
				donovan.setLastDirection(Person.IM_BACKWARD);
			}
			if (getSlideMove() == Slides.DIR_RIGHT) {
				if (spokenToDonovan) {
					setSlide(2);
					dexter.setX(1);
				} else {
					dexter.setX(getWidth() - dexter.getFacingImage().getWidth(null));
					String[] text = { "Not yet.", "There's more do do here, first." };
					setTextbox(new DexterInternal(), text);
				}
			}

			g.fillRect(0, (getHeight() - roadWidth) / 2, getWidth(), roadWidth);
			g.fillRect((getWidth() - roadWidth) / 2, 0, roadWidth, (getHeight() - roadWidth) / 2);
		} else if (getSlide() == 1) {
			gazebo.setX((getWidth() - gazebo.getWidth()) / 2);
			gazebo.setY(getHeight() / 4 - gazebo.getHeight());
			if (choirboys[0] == null) {
				for (int i = 0; i < 6; i++) {
					choirboys[i] = new ChoirBoy();
					choirboys[i].setX(
							(int) (gazebo.getX() + 3 * 5 + (i + 0.125) * choirboys[i].getFacingImage().getWidth(null)));
					choirboys[i]
							.setY(gazebo.getY() + gazebo.getHeight() - choirboys[i].getFacingImage().getHeight(null));
				}
			}

			if (dexter.collidesWith(donovan, getVerticalSpeed(), getHorizontalSpeed())) {
				setVerticalSpeed(0);
				setHorizontalSpeed(0);
				setCanMove(false);
				String[] text = { "There he is.[5] Mike Donovan.", "He is the one." };
				setTextbox(new DexterInternal(), text);
				spokenToDonovan = true;
			} else if (dexter.collidesWith(gazebo, getVerticalSpeed(), getHorizontalSpeed())) {
				setVerticalSpeed(0);
				setHorizontalSpeed(0);
				setCanMove(false);
				String[] text = { "Magnificat anima mea Dominum. Et exultavit spiritus meus in Deo salutari meo." };
				setTextbox(new ChoirBoy(), text);
			}

			if (getSlideMove() == Slides.DIR_UP) {
				dexter.setY(1);
			}
			if (getSlideMove() == Slides.DIR_DOWN) {
				dexter.setY(1);
				setSlide(0);
				choirMusic.pause();
				bgMusic.play(-1);
			}
			if (getSlideMove() == Slides.DIR_LEFT) {
				dexter.setX(1);
			}
			if (getSlideMove() == Slides.DIR_RIGHT) {
				dexter.setX(getWidth() - dexter.getFacingImage().getWidth(null));
			}

			g.fillRect((getWidth() - roadWidth) / 2, (getHeight() - roadWidth) / 4, roadWidth, (getHeight()));
			g.fillRect((getWidth() - roadWidth) / 4, (getHeight() - 2 * roadWidth) / 4, (getWidth() + roadWidth) / 2,
					roadWidth);
			gazebo.draw(g);
			for (ChoirBoy i : choirboys) {
				i.draw(g);
			}
			donovan.draw(g);
		} else if (getSlide() == 2) {
			if (donovansCar.getX() == 0) {
				donovansCar.setX((getWidth() - donovansCar.getWidth()) / 2);
				donovansCar.setY(getHeight() / 2 - donovansCar.getHeight());
			}
			if (getSlideMove() == Slides.DIR_LEFT) {
				dexter.setX(getWidth() - dexter.getFacingImage().getWidth(null));
				setSlide(0);
			}
			if (getSlideMove() == Slides.DIR_DOWN) {
				dexter.setY(getHeight() - dexter.getFacingImage().getHeight(null));
			}
			if (getSlideMove() == Slides.DIR_UP) {
				dexter.setY(1);
			}
			if (getSlideMove() == Slides.DIR_RIGHT) {
				dexter.setX(getWidth() - dexter.getFacingImage().getWidth(null));
				setTextbox(new DexterInternal(), getBackwardsText());
			}

			if (dexter.collidesWith(donovansCar, getVerticalSpeed(), getHorizontalSpeed())) {
				setVerticalSpeed(0);
				setHorizontalSpeed(0);
				setCanMove(false);
				String[] text = { "It's Donovan's car[2].[2].[2].", "I'm going to pick the lock." };
				seenDonovansCar = true;
				setTextbox(new DexterInternal(), text);
			}

			g.fillRect(0, (getHeight() - roadWidth) / 2, getWidth(), roadWidth);
			donovansCar.draw(g);
		}

		move();
		dexter.draw(g);

		if (getTextbox() != null) {
			getTextbox().draw(g, getSize());
			if (getTextbox().isEnded()) {
				setTextbox(null);
			}
		} else {
			if (seenDonovansCar) {
				nextScene();
			}
		}

		repaint();
	}

	@Override
	public void nextScene() {
		choirMusic.stop();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Program.ex.play(new DonovansCarLock(bgMusic));
			}
		});
	}

}
