package com.example.hhoa.threadandhandler;

import android.util.Log;

public class Task implements Runnable{

    public static final String TAG = "TASK";
    private int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Log.i(TAG, "run: start thread " + id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run: end thread " + id);
    }
}
