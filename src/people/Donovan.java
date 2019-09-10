package people;

import logic.Person;

public class Donovan extends Person {

	public Donovan() {
		setName("Mike Donovan");
		setSpeechSound("speech/donovan.wav");
		setForward(loadImage("characters/donovan/forward.png", 3));
		setBackward(loadImage("characters/donovan/backward.png", 3));
		setLeft(loadImage("characters/donovan/left.png", 3));
		setRight(loadImage("characters/donovan/right.png", 3));
		setIcon(loadImage("characters/donovan/icon.png"));
		setWalkSpeed(4);
	}

}
