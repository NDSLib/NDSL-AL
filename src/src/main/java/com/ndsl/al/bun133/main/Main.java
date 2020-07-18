package com.ndsl.al.bun133.main;

import com.ndsl.al.bun133.clip.AudioClip;
import com.ndsl.al.bun133.clip.FileNotSupportedException;

import java.io.File;

public class Main {
    public static void main(String[] args) throws FileNotSupportedException, InterruptedException {
        new Main().play_test();
    }

    public void play_test() throws FileNotSupportedException, InterruptedException {
        AudioClip clip=new AudioClip(new File("src\\src\\main\\resources\\se1.wav"));
        clip.player.setVolume(6.0206f);
        clip.player.asyncPlay();
        Thread.sleep(1000);
        clip.player.setVolume(1f);
        clip.player.asyncPlay();
        clip.close();
    }
}
