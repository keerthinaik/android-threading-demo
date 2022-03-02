package com.example.threadingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button buttonStartThread;

    private ExampleThread exampleThread;

    private Handler mainHandler = new Handler();

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartThread = findViewById(R.id.button_start_thread);
    }

    public void startThread(View view) {
        stopThread = false;
        exampleThread = new ExampleThread(10);
        exampleThread.start();
    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExampleThread extends Thread {

        private int seconds;

        public ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i=0; i<seconds; i++) {
                if (stopThread) {
                    return;
                }
                Log.d(TAG, "startThread " + i);
                if (i == seconds/2) {

                    runOnUiThread(() -> {
                        buttonStartThread.setText("50%");
                    });

                    /*mainHandler.post(() -> {
                        buttonStartThread.setText("50%");
                    });*/

                    /*Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(() -> {
                        buttonStartThread.setText("50%");
                    });*/

                    /*buttonStartThread.post(() -> {
                        buttonStartThread.setText("50%");
                    });*/

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}