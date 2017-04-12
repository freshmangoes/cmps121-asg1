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
    public int recent_min_0 = 0;
    public int recent_sec_0 = 0;
    public int recent_min_1 = 0;
    public int recent_sec_1 = 0;
    public int recent_min_2 = 0;
    public int recent_sec_2 = 0;

    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 1000;

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
//        Button recentButton0 = (Button) findViewById(R.id.time_butt_0);
//        Button recentButton1 = (Button) findViewById(R.id.time_butt_1);
//        Button recentButton2 = (Button) findViewById(R.id.time_butt_2);
        r0.setText(String.format("%d:%02d", recent_min_0, recent_sec_0));

        // Check to make sure + or - was hit
        if(flag_modified){
            // If the temp variable for the first recent time already has something stored
            if((tempm != recent_min_0) && (temps != recent_sec_0)){
                // Set it to the second recent time
                recent_min_1 = tempm;
                recent_sec_1 = temps;
                r1.setText(String.format("%d:%02d", recent_min_1, recent_sec_1));
                // Update the temp variable for the first recent time
                tempm = recent_min_0;
                temps = recent_sec_0;
                // If the temp variable for the second recent time already has something stored
                if((tempm00 != recent_min_1) && (temps00 != recent_sec_1)){
                    // Set it to the third recent time
                    recent_min_2 = tempm00;
                    recent_sec_2 = temps00;
                    r2.setText(String.format("%d:%02d", recent_min_2, recent_sec_2));
                    // Update the temp variable for the second recent time
                    tempm00 = recent_min_1;
                    temps00 = recent_sec_1;
                }
                // Initial set for the 2nd recent time
                tempm00 = recent_min_1;
                temps00 = recent_sec_1;
            }
            // Initial set for the first recent time
            tempm = recent_min_0;
            temps = recent_sec_0;
        }
    }

    // There was probably a more efficient way to do this than make function
    // for each recent button, but I am too inexperienced in Android development T_T
    public void onClickRecent0(View v){
        seconds = (recent_min_0*60) + (recent_sec_0);
        displayTime();
        startTimer();
    }

    public void onClickRecent1(View v){
        seconds = (recent_min_1*60) + (recent_sec_1);
        displayTime();
        startTimer();
    }

    public void onClickRecent2(View v){
        seconds = (recent_min_2*60) + (recent_sec_2);
        displayTime();
        startTimer();
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
        cancelTimer();
        displayTime();
    }

    // Took Professor's suggestion and moved this section to a helper function
    // so I don't have to call the entirety of onClickStart
    public void startTimer(){
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
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        startTimer();
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
            recent_min_0 = m;
            recent_sec_0 = s;
        }

        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button)findViewById(R.id.button_start);

        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }
}
