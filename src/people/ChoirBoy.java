package people;

import logic.Person;

public class ChoirBoy extends Person {
	
	public static final int DEFAULT_WALK_SPEED = 0;
	public ChoirBoy() {
		setName("Choir Boy");
		setSpeechSound("speech/choirboy.wav");
		setForward(loadImage("characters/choirboy/choirboy.png", 5));
		setBackward(loadImage("characters/choirboy/choirboy.png", 5));
		setLeft(loadImage("characters/choirboy/choirboy.png", 5));
		setRight(loadImage("characters/choirboy/choirboy.png", 5));
		setIcon(loadImage("characters/choirboy/icon.png"));
		setWalkSpeed(0);
	}

}
