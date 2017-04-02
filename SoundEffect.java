import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
     
	public enum SoundEffect {
	   HIT("sounds/pew.wav"),
	   EXPLODE("sounds/explode.wav");
	   	   // Nested class for specifying volume
	   public static enum Volume {
	      MUTE, LOW, MEDIUM, HIGH
	   }
	   
	   public static Volume volume = Volume.HIGH;
	   
	   // Each sound effect has its own clip, loaded with its own sound file.
	   private Clip clip;
	   
	   // Constructor to construct each element of the enum with its own sound file.
	   SoundEffect(String soundFileName) {
	      try {
	         // Use URL (instead of File) to read from disk and JAR.
	         URL url = this.getClass().getResource(soundFileName);
	         // Set up an audio input stream piped from the sound file.
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	         // Get a clip resource.
	         clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioInputStream);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   // Play or Re-play the sound effect from the beginning, by rewinding.
	   public void play(Boolean loop) {
	      if (volume != Volume.MUTE) {
	         if (clip.isRunning())
	            clip.stop();   // Stop the player if it is still running
	         clip.setFramePosition(0); // rewind to the beginning
	         clip.start();     // Start playing
	         if(loop)//Loop if loop parameter is true
		    	  clip.loop(Clip.LOOP_CONTINUOUSLY);
	      }
	     
	   }
	   	   
	   public void stop() //stop playing and rewind to be played again from the beginning
	   {
		   clip.stop();
		   clip.setFramePosition(0);
	   }
	   
	   public void mute() //don't play sounds(Mute Sound is selected from Options menu)
	   {
		 volume = Volume.MUTE;
	   }
	}
