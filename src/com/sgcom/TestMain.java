package com.sgcom;

import java.util.Random;

public class TestMain {
    public static void main(String[] args) {
        try {
            Random r = new Random();
            for (int i = 0; i < 50; i++) {
                Thread.sleep(Math.abs(r.nextInt(200)));
                new Thread(new ThreadConfictTest(i)).start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
