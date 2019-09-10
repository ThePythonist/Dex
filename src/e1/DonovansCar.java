package e1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import genericscenes.Slides;
import logic.ObjectCollider;
import logic.Program;
import people.Dexter;
import people.DexterInternal;
import people.Donovan;
import sound.Sound;

public class DonovansCar extends Slides {

	private static final long serialVersionUID = 1L;
	private Donovan donovan = new Donovan();
	private ObjectCollider donovansCar;
	private int roadWidth = 200, conversationCounter = -1;
	private float dexterRaise = 0, dexterRaiseRate = 0.2f;
	private Sound bgMusic = new Sound("music/blood theme.wav"), carStart = new Sound("carstart.wav");
	private long carEnterTime, dexterRaisedTime = -1, carEnterDelay = 3000, wireDelay = 3000, wireTime = -1,
			conversationTimer = -1, conversationDelay = 1000;
	private BufferedImage donovanImage = loadImage("characters/donovan/forward.png", 7),
			dexterImage = loadImage("characters/dexter/forward.png", 7), wire = loadImage("wire.png", 7);
	private boolean wired = false;

	public DonovansCar() {
		setBackground(new Color(0, 50, 0));
		setCharacter(donovan);
		initMovement();
		donovansCar = new ObjectCollider("cars/donovan.png", 5, 0, 0);
		donovansCar.setY(-donovansCar.getHeight());
		donovan.setY(-donovan.getFacingImage().getHeight(null));
		bgMusic.play(-1);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (getSlide() == 0) {
			if (donovansCar.getY() == -donovansCar.getHeight()) {
				donovansCar.setX((getWidth() - donovansCar.getWidth()) / 2);
				donovansCar.setY(getHeight() / 2 - donovansCar.getHeight());
			}

			if (donovan.getY() == -donovan.getFacingImage().getHeight(null)) {
				donovan.setY(getHeight() / 2 - donovan.getFacingImage().getHeight(null));
			}

			if (getSlideMove() == Slides.DIR_LEFT) {
				donovan.setX(0);
			}
			if (getSlideMove() == Slides.DIR_DOWN) {
				donovan.setY(getHeight() - donovan.getFacingImage().getHeight(null));
			}
			if (getSlideMove() == Slides.DIR_UP) {
				donovan.setY(1);
			}
			if (getSlideMove() == Slides.DIR_RIGHT) {
				donovan.setX(getWidth() - donovan.getFacingImage().getWidth(null));
			}

			if (donovan.collidesWith(donovansCar, getVerticalSpeed(), getHorizontalSpeed())) {
				setVerticalSpeed(0);
				setHorizontalSpeed(0);
				setSlide(1);
				carEnterTime = System.currentTimeMillis();
				setCanMove(false);
				carStart.play(1);
			}

			g.fillRect(0, (getHeight() - roadWidth) / 2, getWidth(), roadWidth);
			donovansCar.draw(g);
			move();
			donovan.draw(g);
		} else if (getSlide() == 1) {
			carStart.changeVolume(-0.1f);
			if (System.currentTimeMillis() - carEnterTime >= carEnterDelay && dexterRaise < 100) {
				dexterRaise += dexterRaiseRate;
			} else if (dexterRaisedTime < 0 && dexterRaise >= 100 && !wired) {
				dexterRaisedTime = System.currentTimeMillis();
			}

			if (dexterRaisedTime > 0 && System.currentTimeMillis() - dexterRaisedTime >= wireDelay) {
				dexterRaisedTime = -1;
				donovanImage = loadImage("characters/donovan/wired.png", 7);
				wired = true;
				new Sound("wire.wav").play(1);
				wireTime = System.currentTimeMillis();
			}

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth() / 2 - getHeight() / 4, getHeight());
			g.fillRect(getWidth() - getWidth() / 2 + getHeight() / 4, 0, getWidth() / 2 - getHeight() / 4, getHeight());
			g.setColor(new Color(100, 100, 100));
			g.fillRect(getWidth() / 2 - getHeight() / 4, 0, getHeight() / 2, getHeight());
			g.setColor(new Color(156, 141, 114));
			g.fillRect(getWidth() / 2 - getHeight() / 4, getHeight() / 6, getHeight() / 2, getHeight() / 2);

			g.setColor(new Color(107, 70, 0));
			g.fillRect(getWidth() / 2 - getHeight() / 4, getHeight() / 6, getHeight() / 6, getHeight() / 4);
			g.setColor(new Color(59, 38, 0));
			g.fillRect(getWidth() / 2 - getHeight() / 4, 17 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 32);

			g.setColor(new Color(107, 70, 0));
			g.fillRect(getWidth() / 2 + getHeight() / 12, getHeight() / 6, getHeight() / 6, getHeight() / 4);
			g.setColor(new Color(59, 38, 0));
			g.fillRect(getWidth() / 2 + getHeight() / 12, 17 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 32);

			g.setColor(Color.BLACK);
			g.drawRect(getWidth() / 2 - getHeight() / 4, getHeight() / 6, getHeight() / 2, getHeight() / 2);

			g.drawRect(getWidth() / 2 - getHeight() / 4, getHeight() / 6, getHeight() / 6, 3 * getHeight() / 16);
			g.drawRect(getWidth() / 2 - getHeight() / 4, 17 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 32);

			g.drawRect(getWidth() / 2 + getHeight() / 12, getHeight() / 6, getHeight() / 6, 3 * getHeight() / 16);
			g.drawRect(getWidth() / 2 + getHeight() / 12, 17 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 32);

			g.drawImage(dexterImage, getWidth() / 2 + getHeight() / 6 - dexterImage.getWidth(null) / 2,
					(int) (5 * getHeight() / 12 - 15 - dexterRaise), null);

			g.setColor(new Color(107, 70, 0));
			g.fillRect(getWidth() / 2 - getHeight() / 4, 23 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 16);

			g.fillRect(getWidth() / 2 + getHeight() / 12, 23 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 16);

			g.setColor(Color.BLACK);
			g.drawRect(getWidth() / 2 - getHeight() / 4, 23 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 16);

			g.drawRect(getWidth() / 2 + getHeight() / 12, 23 * getHeight() / 48, getHeight() / 6, 3 * getHeight() / 16);

			g.drawImage(donovanImage, getWidth() / 2 + getHeight() / 6 - donovanImage.getWidth(null) / 2,
					5 * getHeight() / 12 + 3 * getHeight() / 16 - 100, null);

			if (wired) {
				g.drawImage(wire, getWidth() / 2 + getHeight() / 6 - wire.getWidth(null) / 2, 23 * getHeight() / 48,
						null);
			}

			g.setColor(new Color(100, 100, 100));
			g.fillRect(getWidth() / 2 - getHeight() / 4, 2 * getHeight() / 3, getHeight() / 2, getHeight() / 3);

			g.setColor(Color.BLACK);
			g.drawRect(getWidth() / 2 - getHeight() / 4, 2 * getHeight() / 3, getHeight() / 2, getHeight() / 3);
		}
		if (getTextbox() != null) {
			getTextbox().draw(g, getSize());
			if (getTextbox().isEnded()) {
				setTextbox(null);
			}
		} else {
			if ((wireTime > 0 || conversationCounter >= 0) && conversationTimer < 0) {
				conversationTimer = System.currentTimeMillis();
			}

			if (conversationTimer > 0 && System.currentTimeMillis() >= conversationTimer + conversationDelay) {
				conversationTimer = -1;
				conversationCounter++;
				Dexter dex = new Dexter();
				switch (conversationCounter) {
				case 0:
					String[] text0 = {
							"My name is Dexter.[5] Dexter Morgan[5] and I'm going to kill this man tonight." };
					setTextbox(new DexterInternal(), text0);
					break;
				case 1:
					String[] text1 = { "You're mine now,[3] so do exactly as I say." };
					setTextbox(dex, text1);
					break;
				case 2:
					String[] text2 = { "[7]What do you want[2].[2].[2].[2]?" };
					setTextbox(donovan, text2);
					break;
				case 3:
					String[] text3 = { "I want you to be quiet.",
							"Be good and maybe I'll let you live a little longer." };
					setTextbox(dex, text3);
					break;
				case 4:
					conversationTimer = System.currentTimeMillis();
					break;
				case 5:
					String[] text5 = { "Turn here." };
					setTextbox(dex, text5);
					break;
				case 6:
					conversationTimer = System.currentTimeMillis();
					new Sound("tirescreech.wav").play(1);
					break;
				case 7:
					String[] text7 = { "Get out." };
					setTextbox(dex, text7);
					break;
				case 8:
					conversationTimer = System.currentTimeMillis();
					break;
				case 9:
					conversationTimer = System.currentTimeMillis();
					break;
				case 10:
					nextScene();
				}
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
				Program.ex.play(new IntoTheHouse());
			}
		});
	}

}
