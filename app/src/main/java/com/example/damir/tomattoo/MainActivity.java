package com.example.damir.tomattoo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
//import java.math.*;


public class MainActivity extends Activity implements View.OnClickListener {

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private ImageButton startB;
    private Button stopB;
    public TextView text;
    public ProgressBar pBar;
    public TextView status;
    private final long startTime = 30 * 1000;
    private final long interval = 250;
    private final int[] tt = splitToComponentTimes(startTime/1000);
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startB = (ImageButton) this.findViewById(R.id.imageButton);
        stopB = (Button) this.findViewById(R.id.button);
        startB.setOnClickListener(this);
        stopB.setOnClickListener(this);

        text = (TextView) this.findViewById(R.id.timer);
        status = (TextView) this.findViewById(R.id.status);
        pBar = (ProgressBar) this.findViewById(R.id.progressBar);

        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(tt[1]+"m:"+tt[2]+"s"));


    }

    @Override
    public void onClick(View v) {

        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setVisibility(View.INVISIBLE);
            pBar.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            stopB.setVisibility(View.VISIBLE);

            status.setText("Working hard now...");
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.angels);
            mp.start();
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setVisibility(View.VISIBLE);
            pBar.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
            stopB.setVisibility(View.INVISIBLE);

            status.setText("Press button to start!");
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            countDownTimer.cancel();
            timerHasStarted = false;
            //startB.setVisibility(View.VISIBLE);
            pBar.setVisibility(View.INVISIBLE);
            stopB.setText("Restart");
            text.setText("Time's up!");
            status.setText("Good Job!");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //text.setText("" + millisUntilFinished / 1000);
            int h = splitToComponentTimes(millisUntilFinished/1000)[0];
            int m = splitToComponentTimes(millisUntilFinished/1000)[1];
            int s = splitToComponentTimes(millisUntilFinished/1000)[2];

            text.setText(m+"m:"+s+"s");

            double progr = (100*millisUntilFinished)/startTime;
            progr = 100 - progr;
            Log.d(LOG_TAG, String.valueOf(progr));
            if(progr>83.3){
                status.setText("Having a break now...");
            }
            if((int) progr == 83){
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.awesome);
                mp.start();
            }
            pBar.setProgress((int) progr);
        }
    }

    public static int[] splitToComponentTimes(long longVal)
    {
        //long longVal = biggy.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
