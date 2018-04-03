package com.example.neel.scarnedice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int TotUserScore=0;
    int TotComputerScore=0;
    int CurrentUserScore=0;
    int CurrentComputerScore=0;
    TextView current_yourScore;
    TextView yourScore;
    TextView current_computerScore;
    TextView computerScore;
    TextView scored;
    TextView status;
    Random r = new Random();
    private Random random = new Random();

    Button roll;
    Button hold;
    Button reset;
    ImageView img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current_yourScore = (TextView)findViewById(R.id.textView);
        yourScore = (TextView)findViewById(R.id.textView2);
        current_computerScore = (TextView)findViewById(R.id.textView3);
        computerScore = (TextView)findViewById(R.id.textView4);
        scored = (TextView)findViewById(R.id.score);
        status = (TextView)findViewById(R.id.status);

        roll = (Button)findViewById(R.id.roll);
        hold = (Button)findViewById(R.id.hold);
        reset = (Button)findViewById(R.id.reset);

        img = (ImageView)findViewById(R.id.imageView);

        roll.setOnClickListener(this);
        hold.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    void buttons(int q){
        if(q==0){
            roll.setEnabled(false);
            reset.setEnabled(false);
            hold.setEnabled(false);
        }
        else{
            roll.setEnabled(true);
            reset.setEnabled(true);
            hold.setEnabled(true);
        }
    }

    void getParticularImage(int x){
        switch(x){
            case 1:
                img.setImageResource(R.drawable.dice1);
                break;
            case 2:
                img.setImageResource(R.drawable.dice2);
                break;
            case 3:
                img.setImageResource(R.drawable.dice3);
                break;
            case 4:
                img.setImageResource(R.drawable.dice4);
                break;
            case 5:
                img.setImageResource(R.drawable.dice5);
                break;
            case 6:
                img.setImageResource(R.drawable.dice6);
                break;
        }
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.roll:
                int score = rollingDice(6,1);
                getParticularImage(score);
                scored.setText(String.valueOf(score));
                if(score == 1){
                    CurrentUserScore = 0;
                    current_yourScore.setText(String.valueOf(CurrentUserScore));
                    createToast("You Encounter 1",1000);
                    computerTurn();
                }
                else{
                    CurrentUserScore+=score;
                    current_yourScore.setText(String.valueOf(CurrentUserScore));
                }
                break;
            case R.id.hold:
                TotUserScore += CurrentUserScore;
                CurrentUserScore=0;
                current_yourScore.setText(String.valueOf(CurrentUserScore));
                yourScore.setText((String.valueOf(TotUserScore)));
                if(TotUserScore >= 100){
                    createToast("You Wins! Keep Playing....!!!",1000);
                    status.setVisibility(View.VISIBLE);
                    status.setText("YOU Win");
                    status.setTextColor(0xFF0000FF);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startNewGame();
                        }
                    },1000);

                }else {
                    computerTurn();
                }
                break;
            case R.id.reset:
                resetGame();
                break;
        }
    }

    private void computerTurn() {
        createToast("Computer Turn",1000);
        CurrentComputerScore = 0;
        current_computerScore.setText(String.valueOf(CurrentComputerScore));
        buttons(0);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                computerPlays();
            }
        },1000);
    }

    private void computerPlays() {
        int score;
        score = rollingDice(6, 1);
        getParticularImage(score);
        scored.setText(String.valueOf(score));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {}
        },1000);
        if(score == 1){
            CurrentComputerScore = 0;
            current_computerScore.setText(String.valueOf(CurrentComputerScore));
            createToast("Computer Encounter 1",500);
            yourTurn();
        }
        else {
            CurrentComputerScore += score;
            current_computerScore.setText(String.valueOf(CurrentComputerScore));
            if (TotComputerScore + CurrentComputerScore >= 100) {
                computerHold();
            } else {
                if (CurrentComputerScore >= 20) {
                    computerHold();
                } else {
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computerPlays();
                        }
                    }, 1000);
                }
            }
        }
    }

    private void computerHold() {
        createToast("Computer Holds",500);
        TotComputerScore += CurrentComputerScore;
        CurrentComputerScore=0;
        current_computerScore.setText(String.valueOf(CurrentComputerScore));
        computerScore.setText(String.valueOf(TotComputerScore));
        if(TotComputerScore >= 100){
            createToast("Computer Wins! Keep Trying",1000);
            status.setVisibility(View.VISIBLE);
            status.setText("COMPUTER Wins");
            status.setTextColor(0xFFFF0000);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNewGame();
                }
            },1000);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    yourTurn();;
                }
            },1000);
        }
    }

    private void startNewGame() {
        status.setVisibility(View.INVISIBLE);
        createToast("Starting New Game.....!!!",1000);
        rollingDice(6,1);
        resetGame();
    }

    private void resetGame() {
        TotUserScore=0;
        TotComputerScore=0;
        CurrentUserScore=0;
        CurrentComputerScore=0;
        current_computerScore.setText(String.valueOf(CurrentComputerScore));
        current_yourScore.setText(String.valueOf(current_yourScore));
        yourScore.setText(String.valueOf(TotUserScore));
        computerScore.setText(String.valueOf(TotComputerScore));
        yourTurn();
    }

    private void yourTurn() {
        createToast("Your Turn",500);
        CurrentUserScore = 0;
        current_yourScore.setText(String.valueOf(CurrentUserScore));
        buttons(1);
    }

    private int rollingDice(int x,int y) {
        int diceFront = random.nextInt(x)+y;
        return diceFront;
    }

    private void createToast(String msg,int time){
        final Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },time);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}