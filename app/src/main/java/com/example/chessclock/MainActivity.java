package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText mInput;
    private Button mButtonSet;
    private   long start_time=600000; //10 minutes
    private TextView mTextViewCountDown;
    private TextView mTextViewCountDown2;
    private Button mButtonStartPause;
    private Button mButtonStartPause2;
    private Button mButtonReset;
    private Button mButtonReset2;
    private Button changeClock;
    private Button changeClock2;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer mCountDownTimer2;
    private boolean mTimerRunning;
    private boolean mTimerRunning2;
    private long start_time2;
    private long lefttime=start_time;
    private long lefttime2=start_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInput=findViewById(R.id.input_time);
        mButtonSet=findViewById(R.id.set_button);
        mTextViewCountDown=findViewById(R.id.timer);
        mTextViewCountDown2=findViewById(R.id.timer2);
       changeClock=findViewById(R.id.changeclock1);
        changeClock2=findViewById(R.id.changeclock2);
        mButtonStartPause=findViewById(R.id.start);
        mButtonStartPause2=findViewById(R.id.start2);
        mButtonReset=findViewById(R.id.reset1);
        mButtonReset2=findViewById(R.id.reset2);
        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=mInput.getText().toString();
                if(input.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Field can't be empty.", Toast.LENGTH_SHORT).show();
                }
                long millisInput=Long.parseLong(input)*60000;
                if(millisInput==0)
                {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                }
                setTime(millisInput);
                mInput.setText("");

            }
        });
        changeClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning2)
                    pauseTimer2();
                else
                    startTimer2();
            }
        });
        changeClock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning)
                    pauseTimer();
                else
                    startTimer();
            }
        });
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning)
            pauseTimer();
                else
                    startTimer();
            }
        });
        mButtonStartPause2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning2)
                    pauseTimer2();
                else
                    startTimer2();
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        updateCountDownText();
        mButtonReset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer2();
            }
        });
        updateCountDownText2();


    }
    private void setTime(long milliseconds){
        start_time2=milliseconds;
        start_time=start_time2;
        resetTimer();
        resetTimer2();
    }
    private void updateWatchInterface() {
        if (mTimerRunning) {
            mInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (lefttime < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (lefttime < start_time) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(lefttime, 1000) {
            @Override
            public void onTick(long l) {
                lefttime = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
            mTimerRunning=false;
            mButtonStartPause.setText("Start");
            mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);

            }


        }.start();
        mTimerRunning=true;
        mButtonStartPause.setText("Pause");
        updateWatchInterface();
    }
    private void startTimer2() {
        mCountDownTimer2 = new CountDownTimer(lefttime2, 1000) {
            @Override
            public void onTick(long l) {
                lefttime2 = l;
                updateCountDownText2();

            }

            @Override
            public void onFinish() {
                mTimerRunning2=false;
                mButtonStartPause2.setText("Start");
                mButtonStartPause2.setVisibility(View.INVISIBLE);
                mButtonReset2.setVisibility(View.VISIBLE);

            }


        }.start();
        mTimerRunning2=true;
        mButtonStartPause2.setText("Pause");
        mButtonReset2.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer(){
    mCountDownTimer.cancel();
    mTimerRunning=false;
    mButtonStartPause.setText("Start");
    mButtonReset.setVisibility(View.VISIBLE);
    }
    private void resetTimer(){
        lefttime=start_time;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void pauseTimer2(){
        mCountDownTimer2.cancel();
        mTimerRunning2=false;
        mButtonStartPause2.setText("Start");
        mButtonReset2.setVisibility(View.VISIBLE);
    }
    private void resetTimer2(){
        lefttime2=start_time;
        updateCountDownText2();
        mButtonReset2.setVisibility(View.INVISIBLE);
        mButtonStartPause2.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText(){
        int minutes= (int) (lefttime/1000)/60;
        int seconds=(int)(lefttime/1000)%60;
        String timeLeft=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        mTextViewCountDown.setText(timeLeft);

    }
    private void updateCountDownText2(){
        int minutes= (int) (lefttime2/1000)/60;
        int seconds=(int)(lefttime2/1000)%60;
        String timeLeft=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        mTextViewCountDown2.setText(timeLeft);

    }

}