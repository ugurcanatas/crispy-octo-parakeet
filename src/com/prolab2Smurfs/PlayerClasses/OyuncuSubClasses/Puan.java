package com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Puan extends Oyuncu {

    OnPointInterface onPointInterface;
    private Clip clipGoldPickup;
    private Clip clipMushroomPickup;
    private Clip clipAzrael, clipGargamel;

    int Skor;
    public Puan(int Skor, OnPointInterface onPointInterface) {
        super(Skor);
        this.Skor = Skor;
        this.onPointInterface = onPointInterface;
        try {
            this.clipGoldPickup = AudioSystem.getClip();
            this.clipGoldPickup.open(AudioSystem.getAudioInputStream(new File(soundGoldPickUp)));
            this.clipMushroomPickup = AudioSystem.getClip();
            this.clipMushroomPickup.open(AudioSystem.getAudioInputStream(new File(soundMushroomPickUp)));
            this.clipAzrael = AudioSystem.getClip();
            this.clipAzrael.open(AudioSystem.getAudioInputStream(new File(soundAzrael)));
            this.clipGargamel = AudioSystem.getClip();
            this.clipGargamel.open(AudioSystem.getAudioInputStream(new File(soundGargamel)));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }

    public void scoreGold() {
        this.Skor += 5;
        onPointInterface.onPointUpdate(this.Skor);
        clipGoldPickup.stop();
        clipGoldPickup.setFramePosition(0);
        clipGoldPickup.start();
    }

    public void scoreMushroom() {
        this.Skor += 50;
        onPointInterface.onPointUpdate(this.Skor);
        clipMushroomPickup.stop();
        clipMushroomPickup.setFramePosition(0);
        clipMushroomPickup.start();
    }

    public void lowerScoreAzman() {
        this.Skor -= 5;
        onPointInterface.onPointUpdate(this.Skor);
        clipAzrael.stop();
        clipAzrael.setFramePosition(0);
        clipAzrael.start();
    }

    public void lowerScoreGargamel() {
        this.Skor -= 15;
        onPointInterface.onPointUpdate(this.Skor);
        clipGargamel.stop();
        clipGargamel.setFramePosition(0);
        clipGargamel.start();
    }

    @Override
    public int PuaniGoster() {
        return this.Skor;
    }

    public interface OnPointInterface {
        void onPointUpdate(int Skor);
    }
}
