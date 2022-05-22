package song;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	static Clip clip;
	static URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("./backMusic.wav");
		soundURL[1] = getClass().getResource("./door.wav");
		soundURL[2] = getClass().getResource("./moveFrame.wav");
		soundURL[3] = getClass().getResource("./clic.wav");
		soundURL[4] = getClass().getResource("./translateIn.wav");
	}
	
	public static void setFile(int i) {
		try {
			AudioInputStream als = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(als);
		}catch(Exception e) {
			
		}
	}
	
	public static void play() {
		clip.start();
		clip.toString();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	
}

