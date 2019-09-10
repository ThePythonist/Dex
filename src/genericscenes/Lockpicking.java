package genericscenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import logic.Scene;
import people.Dexter;
import sound.Sound;

public abstract class Lockpicking extends Scene {

	private static final long serialVersionUID = 1L;
	private static final int RADIUS = 200, DEPTH = 50, KEY_RATIO = 4, BAR_WIDTH = 400, BAR_HEIGHT = 50;
	public static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3;
	private int health, maxHealth, keySpeed, direction, position;
	private String name;
	private Font titleFont = loadFont("soda_script_bold_extras.ttf", 75f), countFont = loadFont("cmu.ttf", 25f);
	private long startTime = System.currentTimeMillis();
	private long startDelay = 1000;
	private Sound bgMusic;

	private Random rng;

	public Lockpicking() {
		rng = new Random();
		direction = rng.nextInt(RIGHT + 1);

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					float bound = -1;
					switch (direction) {
					case UP:
						bound = (getHeight() + RADIUS) / 2;
						break;
					case LEFT:
						bound = getWidth() / 2 + RADIUS / KEY_RATIO;
						break;
					case DOWN:
						bound = (getHeight() + RADIUS) / 2;
						break;
					case RIGHT:
						bound = getWidth() / 2 + RADIUS / KEY_RATIO;
					}
					double difference = Math.abs(position - bound) / bound;
					health -= 50 - 50 * difference;
					position = 0;
					direction = rng.nextInt(RIGHT + 1);
					if (Dexter.lockpicks > 0) {
						Dexter.lockpicks--;
					}
				}

			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(new Color(200, 200, 0));
		g.fillOval(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS - DEPTH, 2 * RADIUS, 2 * RADIUS);
		g.fillRect(getWidth() / 2 - RADIUS, getHeight() / 2 - DEPTH, 2 * RADIUS, DEPTH);

		g.setColor(Color.YELLOW);
		g.fillOval(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, 2 * RADIUS, 2 * RADIUS);
		g.setColor(Color.BLACK);
		g.drawOval(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, 2 * RADIUS, 2 * RADIUS);
		g.fillOval(getWidth() / 2 - RADIUS / KEY_RATIO, (getHeight() - RADIUS) / 2, 2 * RADIUS / KEY_RATIO,
				2 * RADIUS / KEY_RATIO);
		g.fillRect(getWidth() / 2 - RADIUS / KEY_RATIO / 2, (getHeight() - RADIUS) / 2 + RADIUS / KEY_RATIO,
				RADIUS / KEY_RATIO, RADIUS - RADIUS / KEY_RATIO);

		if (health <= 0) {
			nextScene();
		} else if (System.currentTimeMillis() - startTime >= startDelay && Dexter.lockpicks > 0) {
			int x = 0, y = 0;
			boolean done = false;
			switch (direction) {
			case UP:
				x = getWidth() / 2 - RADIUS / KEY_RATIO;
				y = getHeight() - position;
				if (position >= getHeight() + RADIUS) {
					done = true;
				}
				break;
			case LEFT:
				x = getWidth() - position;
				y = (getHeight() - RADIUS) / 2;
				if (position >= getWidth() + 2 * RADIUS / KEY_RATIO) {
					done = true;
				}
				break;
			case DOWN:
				x = getWidth() / 2 - RADIUS / KEY_RATIO;
				y = -RADIUS + position;
				if (position >= getHeight() + RADIUS) {
					done = true;
				}
				break;
			case RIGHT:
				x = -2 * RADIUS / KEY_RATIO + position;
				y = (getHeight() - RADIUS) / 2;
				if (position >= getWidth() + 2 * RADIUS / KEY_RATIO) {
					done = true;
				}
			}
			g.setColor(new Color(255, 200, 0));
			g.fillOval(x, y, 2 * RADIUS / KEY_RATIO, 2 * RADIUS / KEY_RATIO);
			g.fillRect(x + RADIUS / KEY_RATIO / 2, y + RADIUS / KEY_RATIO, RADIUS / KEY_RATIO,
					RADIUS - RADIUS / KEY_RATIO);
			position += keySpeed;
			if (done) {
				position = 0;
				direction = rng.nextInt(RIGHT + 1);
			}
		}

		g.setColor(Color.WHITE);
		g.drawRect((getWidth() - BAR_WIDTH) / 2, getHeight() / 4 - (RADIUS + BAR_HEIGHT) / 2, BAR_WIDTH, BAR_HEIGHT);
		g.fillRect((getWidth() - BAR_WIDTH) / 2, getHeight() / 4 - (RADIUS + BAR_HEIGHT) / 2,
				(int) (BAR_WIDTH * ((0.0f + health) / (0.0f + maxHealth))), BAR_HEIGHT);

		g.setFont(titleFont);
		g.drawString(name, (getWidth() - g.getFontMetrics().stringWidth(name)) / 2,
				getHeight() - getHeight() / 4 + (RADIUS - g.getFontMetrics().getHeight()) / 2);

		g.setFont(countFont);
		String count = " x " + Dexter.lockpicks;
		g.drawString(count, getWidth() - g.getFontMetrics().stringWidth(count) - g.getFontMetrics().getHeight(),
				g.getFontMetrics().getHeight());

		g.setColor(new Color(255, 200, 0));
		int size = 25;
		g.fillOval(getWidth() - g.getFontMetrics().stringWidth(count) - g.getFontMetrics().getHeight() - 10 - size, 10,
				size, size);
		g.fillRect(
				getWidth() - g.getFontMetrics().stringWidth(count) - g.getFontMetrics().getHeight() - 10 - 3 * size / 4,
				10 + size / 2, size / 2, (int) (size * 1.5));

		repaint();

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
		this.maxHealth = health;
	}

	public int getKeySpeed() {
		return keySpeed;
	}

	public void setKeySpeed(int keySpeed) {
		this.keySpeed = keySpeed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sound getBgMusic() {
		return bgMusic;
	}

	public void setBgMusic(Sound bgMusic) {
		this.bgMusic = bgMusic;
	}

}
