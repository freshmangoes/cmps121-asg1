// Kyle Cilia
// CMPS121 Assignment 1

package com.dealfaro.luca.KitchenTImer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;

    public boolean flag_modified = false;
    public boolean flag_stopped = false;

    // Temp variables to store previous times as variables
    public int tempm = 0;
    public int temps = 0;

    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
        flag_modified = false;
    }

    public void updateButtonTimes(){


        //my buttons
        TextView r0 = (TextView) findViewById(R.id.time_butt_0);
        TextView r1 = (TextView) findViewById(R.id.time_butt_1);
        TextView r2 = (TextView) findViewById(R.id.time_butt_2);

        //My Buttons
        Button recentButton0 = (Button) findViewById(R.id.time_butt_0);
        Button recentButton1 = (Button) findViewById(R.id.time_butt_1);
        Button recentButton2 = (Button) findViewById(R.id.time_butt_2);

        r0.setText(String.format("%d:%02d", tempm, temps));

    }

    public void onClickPlus(View v) {
        seconds += 60;
        flag_modified = true;
        displayTime();
    };

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        flag_modified = true;
        displayTime();
    };

    public void onReset(View v) {
        seconds = 0;
        flag_modified = true;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();
            if ((flag_modified = true) && (flag_stopped = true)){

            }
            flag_modified = false;
            updateButtonTimes();
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        flag_stopped = true;
        displayTime();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);

        TextView v = (TextView) findViewById(R.id.display);

        int m = seconds / 60;
        int s = seconds % 60;

        //storing the previous time in temp variables
        tempm = m;
        temps = s;

        v.setText(String.format("%d:%02d", m, s));

        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button)findViewById(R.id.button_start);

        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }
}
