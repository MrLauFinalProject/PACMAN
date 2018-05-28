/*
 * Ajay is responsible for writing this class on his own
 */

import sun.audio.*;
import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Sounds {
	
	static AudioPlayer thePlayer;
  public static void main(String[] args){
	  
	  //playOnce("nomnom.WAV");
  }
  public static void playContinous(String sound){
	
	  
	  try{
		  AudioData data = new AudioStream(new FileInputStream("C:\\Users\\alexs\\Desktop\\PacManFiles\\" + sound)).getData();
		  ContinuousAudioDataStream audio = new ContinuousAudioDataStream(data);
		  thePlayer.player.start(audio);
		  
	  }catch(Exception e){
		  
		  System.out.println("Error 1");
	  }
  }
  public static void playOnce(String sound){

	  InputStream music;
	  try{
		  music = new FileInputStream(new File("C:\\Users\\alexs\\Desktop\\PacManFiles\\" + sound));
		  AudioStream audio = new AudioStream(music);
		  thePlayer.player.start(audio);
		  
	  }catch(Exception e){
		  
		  System.out.println("Error 2");
	  }
  }
  public static void stopAudio(){
	    
	  thePlayer.player.stop(); 
  }
}
