package com.sgcom;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadConfictTest implements Runnable {
    private ConcurrentHashMap<String, String> instanceFields = new ConcurrentHashMap();
    private String id = "";

    public ThreadConfictTest(int id) {
        Thread.currentThread().setName("SGcomThread_" + id);
        System.out.println(Thread.currentThread().getName() + " initialized !");
    }

    public void run() {
        for (;;) {
            String curTname = Thread.currentThread().getName();
            this.id = curTname;
            this.instanceFields.put("id", curTname);
            java.util.Random r = new java.util.Random();
            try {
                Thread.sleep(Math.abs(r.nextInt(1000)));
                test(curTname);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test(String name) {
        if (!name.equals(this.id)) {
            System.out.println("Current : " + Thread.currentThread().getName() + ", Field : " + this.id);
        }
        if (!name.equals(this.instanceFields.get("id"))) {
            System.out.println(Thread.currentThread().getName() + "_" + (String) this.instanceFields.get("id"));
        }
    }
}
