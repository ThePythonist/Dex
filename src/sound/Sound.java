package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private String resource;
	private Clip clip;
	private AudioInputStream audioInputStream;
	private FloatControl gainControl;

	public Sound(String resource) {
		setResource(resource);
		load();
	}

	public void load() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("src/assets/sounds/" + resource));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}

	public void setVolume(float volume) {
		if (getVolume() + volume > gainControl.getMaximum()) {
			gainControl.setValue(gainControl.getMaximum());
		} else if (getVolume() + volume < gainControl.getMinimum()) {
			gainControl.setValue(gainControl.getMinimum());
		} else {
			gainControl.setValue(volume);
		}
	}

	public float getVolume() {
		return gainControl.getValue();
	}

	public void changeVolume(float change) {
		setVolume(getVolume() + change);
	}

	public void play(int loops) {
		if (loops < 0) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		clip.start();
	}

	public void pause() {
		clip.stop();
	}

	public void stop() {
		clip.stop();
		clip.close();
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Clip getClip() {
		return clip;
	}

	public void setClip(Clip clip) {
		this.clip = clip;
	}

	public AudioInputStream getAudioInputStream() {
		return audioInputStream;
	}

	public void setAudioInputStream(AudioInputStream audioInputStream) {
		this.audioInputStream = audioInputStream;
	}

	public FloatControl getGainControl() {
		return gainControl;
	}

	public void setGainControl(FloatControl gainControl) {
		this.gainControl = gainControl;
	}

}
