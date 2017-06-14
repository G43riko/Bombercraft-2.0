package utils;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import utils.resouces.ResourceLoader;


public final class AudioPlayer {
	private Clip clip;
	
	public AudioPlayer(String name) {
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(ResourceLoader.load(name));
				AudioFormat baseFormat = ais.getFormat();
				AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
														   baseFormat.getSampleRate(),
														   16,
														   baseFormat.getChannels(),
														   baseFormat.getChannels() * 2,
														   baseFormat.getSampleRate(),
														   false);
				AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
				clip = AudioSystem.getClip();
				clip.open(dais);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	}
	
	public void play(){
		if(clip == null){
			return;
		}
		
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop(){
		if(clip == null){
			return;
		}
		
		if(clip.isRunning()){
			clip.stop();
		}
	}
	
	public void close(){
		if(clip == null){
			return;
		}
		
		stop();
		clip.close();
	}
}
