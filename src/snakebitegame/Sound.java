package snakebitegame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    private Clip clip;
    URL[] soundURL = new URL[4];

    public Sound(){
        soundURL[0] = getClass().getResource("Sounds\\eating-sound.wav");
        soundURL[1] = getClass().getResource("Sounds\\game-running.wav");
        soundURL[2] = getClass().getResource("Sounds\\game-over.wav");
        soundURL[3] = getClass().getResource("Sounds\\eating-sound.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e){System.out.println(e);
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
