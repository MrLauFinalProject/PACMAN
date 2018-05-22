import sun.audio.*;
import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Sounds {
  public static void main(String[] args) {
	  
	  playsound("nomnom.WAV");
  }
  public static void playsound(String sound){
	
	  
	  
	  try{
		  AudioData data = new AudioStream(new FileInputStream(sound)).getData();
		  ContinuousAudioDataStream audio = new ContinuousAudioDataStream(data);
		  AudioPlayer.player.start(audio);
		  
	  }catch(Exception e){
		  
		  System.out.println("Error");
	  }
  }
  public void stopSound(String sound){

	  try{
		  AudioData data = new AudioStream(new FileInputStream(sound)).getData();
		  ContinuousAudioDataStream audio = new ContinuousAudioDataStream(data);
		  AudioPlayer.player.stop(audio);
		  
	  }catch(Exception e){
		  
		  System.out.println("Error");
	  }
  }
}
