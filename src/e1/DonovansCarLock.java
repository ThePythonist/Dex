package e1;

import java.awt.EventQueue;

import genericscenes.Lockpicking;
import logic.Program;
import sound.Sound;

public class DonovansCarLock extends Lockpicking {

	private static final long serialVersionUID = 1L;

	public DonovansCarLock(Sound bgMusic) {
		setHealth(90);
		setKeySpeed(5);
		setName("Donovan's Car");
		setBgMusic(bgMusic);
	}

	@Override
	public void nextScene() {
		getBgMusic().stop();
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Program.ex.play(new DonovansCar());
			}
		});
	}

}
