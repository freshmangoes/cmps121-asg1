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

    // Temp variables to store previous times as variables
    public int tempm = 0;
    public int temps = 0;

    public int tempm00 = 0;
    public int temps00 = 0;

    public int tempm0 = 0;
    public int temps0 = 0;
    public int tempm1 = 0;
    public int temps1 = 0;
    public int tempm2 = 0;
    public int temps2 = 0;

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
        r0.setText(String.format("%d:%02d", tempm0, temps0));

        if(flag_modified){

            // If the temp variable for the first recent time already has something stored
            if((tempm != tempm0) && (temps != temps0)){
                // Set it to the second recent time
                tempm1 = tempm;
                temps1 = temps;
                r1.setText(String.format("%d:%02d", tempm1, temps1));
                // Update the temp variable for the first recent time
                tempm = tempm0;
                temps = temps0;
                // If the temp variable for the second recent time already has something stored
                if((tempm00 != tempm1) && (temps00 != temps1)){
                    // Set it to the third recent time
                    tempm2 = tempm00;
                    temps2 = temps00;
                    r2.setText(String.format("%d:%02d", tempm2, temps2));
                    // Update the temp variable for the second recent time
                    tempm00 = tempm1;
                    temps00 = temps1;
                }
                // Initial set for the 2nd recent time
                tempm00 = tempm1;
                temps00 = temps1;
            }
            // Initial set for the first recent time
            tempm = tempm0;
            temps = temps0;
        }
    }

    public void onClickPlus(View v) {
        seconds += 60;
        displayTime();
        flag_modified = true;
    };

    public void onClickMinus(View v) {
        seconds = Math.max(0, seconds - 60);
        displayTime();
        flag_modified = true;
    };

    public void onReset(View v) {
        seconds = 0;
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

        }
        updateButtonTimes();
        flag_modified = false;
    }

    public void onClickStop(View v) {
        cancelTimer();
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
        Log.d(LOG_TAG, "Modified: " + flag_modified);

        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));

        //storing the previous time in temp variables
        if(flag_modified){
            tempm0 = m;
            temps0 = s;
        }

        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button)findViewById(R.id.button_start);

        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }
}
