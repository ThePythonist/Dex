package graphics;

import java.awt.Dimension;

import javax.swing.JFrame;

import logic.Scene;

public class Application extends JFrame {

	private static final long serialVersionUID = 1L;
	private Scene activeScene = null;

	public Scene getActiveScene() {
		return activeScene;
	}

	public Application(boolean undecorated) {
		initUI(undecorated);
	}

	private void initUI(boolean undecorated) {
		setSize(new Dimension());

		setTitle("Dexter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(undecorated);

		setLocationRelativeTo(null);
	}

	public void play(Scene scene) {
		if (activeScene != null) {
			remove(activeScene);
		}
		activeScene = scene;
		add(scene);
		setVisible(true);
	}

}
