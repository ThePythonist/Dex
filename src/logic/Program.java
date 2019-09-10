package logic;

import java.awt.EventQueue;

import e1.DexterTitle;
import graphics.Application;

public class Program {

	public static Application ex;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				ex = new Application(true); 
				ex.setVisible(true);
				ex.setResizable(false);

				ex.play(new DexterTitle());
			}
		});

	}

	public static void toggleFullscreen() {
		Scene scene = ex.getActiveScene();
		boolean undecorated = ex.isUndecorated();
		ex.dispose();
		ex = new Application(!undecorated);
		ex.setVisible(true);
		ex.setResizable(false);

		ex.play(scene);
	}

}
