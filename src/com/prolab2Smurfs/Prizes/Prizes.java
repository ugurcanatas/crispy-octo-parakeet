package com.prolab2Smurfs.Prizes;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Prizes{
    private long seconds;
    Timer timer;
    String TYPE;
    OnTimerInterface onTimerInterface;
    boolean isTimerActive = true;

    public Prizes(long seconds, String TYPE, OnTimerInterface onTimerInterface) {
        this.TYPE = TYPE;
        this.onTimerInterface = onTimerInterface;
        this.seconds = seconds;
        int secondsRandom = new Random().nextInt((int) seconds);
        timer = new Timer();
        timer.schedule(new TimerClass(),1000,secondsRandom*1000);
    }

    class TimerClass extends TimerTask {
        @Override
        public void run() {
            if (isTimerActive) {
                if (TYPE.equals("GOLD")) {
                    onTimerInterface.onTimerUpdateGold();
                }else {
                    onTimerInterface.onTimerUpdateMushroom();
                }
            }
        }

        @Override
        public boolean cancel() {
            return super.cancel();
        }
    }

    public void resetTimer(){
        isTimerActive = false;
        timer.cancel();
        timer.purge();
    }

    public interface OnTimerInterface {
        void onTimerUpdateGold();
        void onTimerUpdateMushroom();
    }

    /*public interface OnGoldInterface {
        void onTimerUpdateGold();
    }

    public interface OnMushroomInterface {
        void onTimerUpdateMushroom();
    }*/
}
