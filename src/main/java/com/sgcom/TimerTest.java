package com.sgcom;

import java.util.Calendar;
import java.util.Timer;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimerTest {
    private class StatusManager extends java.util.TimerTask {
        private StatusManager() {}
        public void run() {
            TimerTest.this.getStatus();
        }
    }
  
    public TimerTest() {
        StatusManager m = new StatusManager();
        
        Timer jobScheduler = new Timer(true);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(5, 0);
        cal.set(10, 0);
        cal.set(12, 0);
        cal.set(13, 1);
        cal.set(14, 0);
        Date tommorow = cal.getTime();
        
        jobScheduler.scheduleAtFixedRate(m, 0L, 5000L);
    }
  
    public static void main(String[] args) throws InterruptedException { 
        TimerTest t = new TimerTest();
        for (;;) {
            Thread.sleep(1000L);
        }
    }

    public void getStatus() { 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        System.out.println("System is normal : " + df.format(new Date(System.currentTimeMillis())));
    }
}
