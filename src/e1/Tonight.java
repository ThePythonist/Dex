package e1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import graphics.Textbox;
import logic.Person;
import logic.Program;
import logic.Scene;
import people.Dexter;
import people.DexterInternal;
import sound.Sound;

public class Tonight extends Scene {

	private static final long serialVersionUID = 1L;
	private BufferedImage stars, dextersCar;
	private long startTime;
	private boolean started = false;
	private boolean startPanning = false;
	private boolean outofcar = false;
	private int pan = 0, stopPan = 0, scroll = 0, car;
	private long panningTime = -1, stopPanningTime = -1;
	private Dexter dexter = null;
	private int dexterHorizantalSpeed = 0;
	private Sound bgMusic;

	public Tonight() {
		startTime = System.currentTimeMillis();
		setBackground(new Color(0, 0, 50));
		stars = loadImage("scenery/stars.png");
		dextersCar = loadImage("cars/dexter.png", 5);
		car = -dextersCar.getWidth(null);

		bgMusic = new Sound("music/blood theme.wav");
		bgMusic.setVolume(10);
		bgMusic.play(-1);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (startPanning) {
			pan++;
			if (System.currentTimeMillis() >= panningTime + 3000 && getTextbox() == null && stopPanningTime < 0) {
				String[] text = new String[3];
				text[0] = "Miami's a great town.";
				text[1] = "Love the Cuban food.[10] Pork sandwiches,[5] my favorite[2].[2].[2].";
				text[2] = "But I'm hungry for something different now.";
				setTextbox(new DexterInternal(), text);
			}
		}

		int starWidth = stars.getWidth(null);
		int starHeight = stars.getHeight(null);
		for (int x = 0; x - scroll % starHeight <= getSize().getWidth(); x += starWidth) {
			for (int y = 0; y - pan % starHeight <= getSize().getHeight(); y += starHeight) {
				g.drawImage(stars, x - scroll % starHeight, y - pan % starHeight, null);
			}
		}
		g.fillRect(0, getHeight() - stopPan, getWidth(), getHeight());
		if (stopPanningTime > 0 && startPanning) {
			stopPan++;
		}

		if (System.currentTimeMillis() >= startTime + 3000 && !started) {
			started = true;
			String[] text = new String[2];
			text[0] = "Tonight's the night.[10] And it's going to happen again[5] and again.[15] Has to happen.";
			text[1] = "Nice night.";
			setTextbox(new DexterInternal(), text);
		}

		g.drawImage(dextersCar, car, getHeight() - stopPan - dextersCar.getHeight(null), null);

		if (car > -dextersCar.getWidth(null) && car < (getWidth() - dextersCar.getWidth(null)) / 2) {
			car += 3;
		} else if (car > -dextersCar.getWidth(null) && scroll < 700) {
			scroll++;
		}

		if (scroll > 0 && scroll < 700) {
			scroll += 2;
		}

		if (scroll >= 700 && !outofcar) {
			outofcar = true;
			dexter = new Dexter();
			dexter.setX(car + dextersCar.getWidth(null));
			dexter.setY(getHeight() - stopPan - dexter.getForward().getHeight(null));
			addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
						dexterHorizantalSpeed = 0;
					}
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						dexterHorizantalSpeed = -dexter.getWalkSpeed();
						dexter.setLastDirection(Person.IM_LEFT);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						dexterHorizantalSpeed = dexter.getWalkSpeed();
						dexter.setLastDirection(Person.IM_RIGHT);
					}
				}
			});
		}

		if (System.currentTimeMillis() >= stopPanningTime + 3000 && startPanning && stopPanningTime > 0) {
			startPanning = false;
			car += 3;
		}

		if (outofcar) {
			dexter.draw(g);
			dexter.moveHorizontal(dexterHorizantalSpeed);
			if (dexter.getX() + dexter.getFacingImage().getWidth(null) >= getWidth()) {
				nextScene();
			} else if (dexter.getX() <= 0) {
				dexter.setX(1);
				setTextbox(new Textbox(new DexterInternal(), getBackwardsText(), this));
			}
		}

		if (getTextbox() != null) {
			getTextbox().draw(g, getSize());
			if (getTextbox().isEnded()) {
				setTextbox(null);
				if (startPanning) {
					stopPanningTime = System.currentTimeMillis();
				} else {
					startPanning = true;
					panningTime = System.currentTimeMillis();
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
				Program.ex.play(new Gazebo());
			}
		});
	}

}
