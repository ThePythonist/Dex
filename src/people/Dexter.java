package people;

import logic.Person;

public class Dexter extends Person {

	public static int lockpicks = 20;

	public Dexter() {
		setName("Dexter Morgan");
		setSpeechSound("speech/dexter.wav");
		setForward(loadImage("characters/dexter/forward.png", 3));
		setBackward(loadImage("characters/dexter/backward.png", 3));
		setLeft(loadImage("characters/dexter/left.png", 3));
		setRight(loadImage("characters/dexter/right.png", 3));
		setIcon(loadImage("characters/dexter/icon.png"));
		setWalkSpeed(4);
	}

}
