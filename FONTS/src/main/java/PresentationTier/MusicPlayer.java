package PresentationTier;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.*;

public class MusicPlayer {
    private Clip clip;
    private FloatControl volumeControl;
    private boolean isStopped;

    public MusicPlayer(String fileName) {
        try {
            File musicPath = new File(fileName);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                isStopped = false;
            }
            else{
                throw new FileNotFoundException("File not found: " + fileName);
            }
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void stopMusic(){
        clip.stop();
        isStopped = true;
    }

    public void startMusic() {
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        isStopped = false;
    }

    public void setVolume(float volume) {
        if (volume >= 0 && volume <= 1) {
            float gain = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            volumeControl.setValue(gain);
        } else {
            throw new IllegalArgumentException("Volume should be between 0 and 1");
        }
    }

    public void changeMusic(String fileName) {
        clip.stop();
        try {
            File musicPath = new File(fileName);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip.close();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                if (isStopped) clip.stop();
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
