package com.ndsl.al.bun133.clip;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioClip{
    public static final String[] SUPPORT_FILE={".wav"};

    public File AudioFile;
    public Clip clip;
    public AudioInputStream inputStream;
    public AudioFormat format;
    public DataLine.Info dataLine;

    public AudioClip(File file) throws FileNotSupportedException {
        for(String suffix : SUPPORT_FILE) {
            if (file.getName().endsWith(suffix)){
                init(file);
                return;
            }
        }
        throw new FileNotSupportedException(file.getName());
    }

    private void init(File file){
        try {
            AudioFile = file;
            inputStream= AudioSystem.getAudioInputStream(file);
            format = inputStream.getFormat();
            dataLine = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(dataLine);
            clip.open(inputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        this.player = new Player(this);
    }

    public void close(){
        clip.close();
    }

    public void flush(){
        clip.flush();
    }

    public Player player;
    public static class Player {
        public AudioClip clip;
        public Player(AudioClip clip){
            this.clip=clip;
            length=clip.clip.getMicrosecondLength();
        }

        public long now_pos=0;
        /**
         * MicroSec
         */
        public long length=0;

        public FloatControl GAIN_Control=null;

        public Player setVolume(float value){
            if(GAIN_Control==null) GAIN_Control = (FloatControl)clip.clip.getControl(FloatControl.Type.MASTER_GAIN);
            GAIN_Control.setValue(value);
            return this;
        }

        public Player setLoop(boolean EndlessLoop){
            if(EndlessLoop) clip.clip.loop(Clip.LOOP_CONTINUOUSLY);
            return this;
        }

        public void start(){
            clip.clip.start();
            sync();
        }

        public void asyncPlay() throws InterruptedException {
            reset();
            sync();
            start();
            sync();
            Thread.sleep(length/1000);
            sync();
        }

        private void sync(){
            now_pos=clip.clip.getMicrosecondPosition();
        }

        public void pause(){
            clip.clip.stop();
        }

        public void reset(){
            setNowPos(0);
        }

        public void setNowPos(int now_pos){
            clip.clip.setFramePosition(now_pos);
            sync();
        }

        public void setNowMicroSec(long now){
            clip.clip.setMicrosecondPosition(now);
            sync();
        }
    }
}
