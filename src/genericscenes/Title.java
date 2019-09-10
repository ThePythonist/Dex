package genericscenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import graphics.CustomButton;
import logic.Scene;
import sound.Sound;

public abstract class Title extends Scene {

	private static final long serialVersionUID = 1L;

	private Font titleFont, buttonFont;
	private JLabel titleLabel;
	private CustomButton playButton;
	private double fade = 0;
	private long startTime;
	private long buttonDelay = 9300;
	private Sound bgMusic;

	public Title(String text) {
		init();
		startTime = System.currentTimeMillis();
		setBackground(Color.BLACK);
		titleFont = loadFont("soda_script_bold_extras.ttf", 75f);
		titleLabel = new JLabel(" " + text + " ");
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(Color.WHITE);

		buttonFont = loadFont("soda_script_bold_extras.ttf", 50f);
		playButton = new CustomButton(" Play Episode ");
		playButton.setHoverBackgroundColor(getBackground());
		playButton.setPressedBackgroundColor(getBackground());
		UIManager.put("Button.disabledText", getBackground());
		playButton.setFont(buttonFont);
		playButton.setForeground(Color.RED);
		playButton.setBackground(getBackground());
		playButton.setBorder(null);
		playButton.setEnabled(false);
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton playButton = (JButton) e.getSource();
				playButton.setEnabled(false);
			}

		});

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel labels = new JPanel();
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.setBackground(getBackground());
		upperPanel.add(Box.createHorizontalGlue());
		upperPanel.add(titleLabel);
		upperPanel.add(Box.createHorizontalGlue());

		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		lowerPanel.setBackground(getBackground());
		lowerPanel.add(Box.createHorizontalGlue());
		lowerPanel.add(playButton);
		lowerPanel.add(Box.createHorizontalGlue());

		labels.setBackground(getBackground());
		labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
		labels.add(Box.createVerticalGlue());
		labels.add(upperPanel);
		labels.add(Box.createVerticalGlue());
		labels.add(lowerPanel);
		labels.add(Box.createVerticalGlue());
		add(Box.createHorizontalGlue());
		add(labels);
		add(Box.createHorizontalGlue());

		bgMusic = new Sound("music/theme.wav");
		bgMusic.play(-1);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color color = new Color((int) fade, (int) fade, (int) fade);
		titleLabel.setForeground(color);
		if (fade < 255 && !playButton.isClicked()) {
			fade += 0.25;
		}

		if (fade > 0 && playButton.isClicked()) {
			fade -= 0.25;
			bgMusic.changeVolume(-0.05f);
		}

		if (fade == 0 && playButton.isClicked()) {
			bgMusic.stop();
			nextScene();
		}

		if (System.currentTimeMillis() >= startTime + buttonDelay && !playButton.isClicked()) {
			playButton.setEnabled(true);
		}

		repaint();
	}

}
