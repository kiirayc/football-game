package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		// Assign URLs for sound files
		soundURL[0] = getClass().getResource("/sound/Theme.wav");
		soundURL[1] = getClass().getResource("/sound/ShoesGained.wav");
		soundURL[2] = getClass().getResource("/sound/WaterSplash.wav");
		soundURL[3] = getClass().getResource("/sound/BallKick.wav");
		soundURL[4] = getClass().getResource("/sound/Success.wav");
		soundURL[5] = getClass().getResource("/sound/GrassRunning.wav");
		soundURL[6] = getClass().getResource("/sound/EndGame.wav");

	}
	
	// Set sound files to be played
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais); //open audio file in java
			
		} catch (Exception e) {
			
		}
		
	}
	
	// Play sound once
	public void play() {
		clip.start();
	}
	
	// Loop the sound continuously
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}

}
