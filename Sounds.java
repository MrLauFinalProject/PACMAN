import sun.audio.*;
import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Sounds {
	
	static AudioPlayer thePlayer;
  public static void main(String[] args){
	  
	  playOnce("nomnom.WAV");
  }
  public static void playContinous(String sound){
	
	  
	  try{
		  AudioData data = new AudioStream(new FileInputStream(sound)).getData();
		  ContinuousAudioDataStream audio = new ContinuousAudioDataStream(data);
		  thePlayer.player.start(audio);
		  
	  }catch(Exception e){
		  
		  System.out.println("Error");
	  }
  }
  public static void playOnce(String sound){

	  InputStream music;
	  try{
		  music = new FileInputStream(new File(sound));
		  AudioStream audio = new AudioStream(music);
		  thePlayer.player.start(audio);
		  
	  }catch(Exception e){
		  
		  System.out.println("Error");
	  }
  }
  public static void stopAudio(){
	    
	  thePlayer.player.stop(); 
  }
}
