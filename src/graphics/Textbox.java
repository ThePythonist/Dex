package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.Person;
import sound.Sound;

public class Textbox {

	public static final int X_PADDING = 50;
	public static final int Y_PADDING = 10;
	public static final int HEIGHT = 300;
	public static final int CHAR_BOX_SIZE = 100;

	private Person person;
	private String[] text;
	private int textCounter = 0;
	private int charCounter = 0;
	private int gap = 50;
	private long lastCharTime = System.currentTimeMillis();
	private Font font = loadFont("cmu.ttf", 24f);
	private JPanel panel;
	private boolean ended = false;
	private KeyListener k;

	public Textbox(Person person, String[] text, JPanel panel) {
		setPerson(person);
		this.panel = panel;

		for (int k = 0; k < text.length; k++) {
			String t = text[k];
			String newText = "";
			char[] c = t.toCharArray();
			boolean inBrackets = false;
			String number = "";
			for (int i = 0; i < t.length(); i++) {
				if (c[i] == '[') {
					inBrackets = true;
				} else if (c[i] == ']' && inBrackets) {
					inBrackets = false;
					int num = Integer.parseInt(number);
					for (int j = 0; j < num; j++) {
						newText += "#";
					}
					number = "";
				} else if (inBrackets) {
					number += c[i];
				} else {
					newText += c[i];
				}
			}
			text[k] = newText;
		}
		setText(text);

		panel.addKeyListener(k = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if (charCounter == text[textCounter].length()) {
						if (textCounter < text.length - 1) {
							charCounter = 0;
							textCounter++;
						} else {
							end();
						}
					} else {
						charCounter = text[textCounter].length();
					}
				}

			}

		});
	}

	public void end() {
		ended = true;
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JLabel) {
				panel.remove(comp);
			}
		}
		panel.removeKeyListener(k);
	}

	public void draw(Graphics g, Dimension d) {
		if (ended) {
			return;
		}
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JLabel) {
				panel.remove(comp);
			}
		}

		Color previous = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(X_PADDING, d.height - HEIGHT - Y_PADDING, d.width - 2 * X_PADDING, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawRect(X_PADDING, d.height - HEIGHT - Y_PADDING, d.width - 2 * X_PADDING, HEIGHT);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(X_PADDING + CHAR_BOX_SIZE / 2, d.height - HEIGHT - Y_PADDING - CHAR_BOX_SIZE / 2, CHAR_BOX_SIZE,
				CHAR_BOX_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(X_PADDING + CHAR_BOX_SIZE / 2, d.height - HEIGHT - Y_PADDING - CHAR_BOX_SIZE / 2, CHAR_BOX_SIZE,
				CHAR_BOX_SIZE);

		g.drawImage(person.getIcon(), X_PADDING + CHAR_BOX_SIZE / 2, d.height - HEIGHT - Y_PADDING - CHAR_BOX_SIZE / 2,
				CHAR_BOX_SIZE, CHAR_BOX_SIZE, null);

		if (System.currentTimeMillis() >= lastCharTime + gap && charCounter < text[textCounter].length()) {
			charCounter++;
			if (text[textCounter].toCharArray()[charCounter - 1] != '#') {
				Sound speech = new Sound(person.getSpeechSound());
				speech.setVolume(-10);
				speech.play(1);
			}
			lastCharTime = System.currentTimeMillis();
		}

		String textToDisplay = text[textCounter].substring(0, charCounter).replaceAll("#", "");
		List<String> lines = new ArrayList<String>();
		lines.add(textToDisplay);

		double lineOffset = 0;
		for (String line : lines) {
			JLabel label = new JLabel(line);
			label.setFont(font);
			label.setForeground(Color.BLACK);
			panel.add(label);
			label.setLocation(new Point(X_PADDING + CHAR_BOX_SIZE,
					(int) (d.height - HEIGHT - Y_PADDING + CHAR_BOX_SIZE + lineOffset)));
			label.setSize(label.getPreferredSize());
			lineOffset += label.getSize().height * 1.5;
		}

		JLabel next = new JLabel(
				charCounter == text[textCounter].length() ? "Press Enter to Continue" : "Press Enter to Skip");
		next.setFont(font);
		next.setForeground(Color.BLUE);
		panel.add(next);
		next.setLocation(new Point((int) (panel.getWidth() - 2 * X_PADDING - next.getPreferredSize().getWidth()),
				(int) (panel.getHeight() - 2 * Y_PADDING - next.getPreferredSize().getHeight())));
		next.setSize(next.getPreferredSize());

		JLabel name = new JLabel(person.getName());
		name.setFont(font);
		name.setForeground(Color.BLUE);
		panel.add(name);
		name.setLocation(new Point((int) (X_PADDING + 1.5 * CHAR_BOX_SIZE + 10), d.height - HEIGHT - Y_PADDING + 10));
		name.setSize(name.getPreferredSize());

		g.setColor(previous);

	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String[] getText() {
		return text;
	}

	public void setText(String[] text) {
		this.text = text;
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

	public boolean isEnded() {
		return ended;
	}

	public boolean equals(Textbox arg0) {
		if (text.length != arg0.text.length){
			return false;
		}
		for (int i=0; i<text.length; i++){
			if (!text[i].equals(arg0.text[i])){
				return false;
			}
		}
		return arg0.person.equals(person);
	}
}
