package com.examplee.radu.braintrainer;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //Definitions
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button startButton;
    TextView sumTextView;
    TextView resultTextView;
    TextView pointsTextView;
    TextView timerTextView;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    Button playAgainButton;
    ConstraintLayout gameRelativeLayout;


    public void playAgain(View view)
    {
        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);

        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30 s");
        pointsTextView.setText("0/0");
        resultTextView.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestion();
        new CountDownTimer(31100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+" s");
            }

            @Override
            public void onFinish() {

                button0.setClickable(false);
                button1.setClickable(false);
                button2.setClickable(false);
                button3.setClickable(false);

                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0 s");
                resultTextView.setVisibility(View.INVISIBLE);
                resultTextView.setTextColor(Color.BLACK);
                if(score < 5)
                {
                    resultTextView.setText("Could do better.\nYour score: "+Integer.toString(score)
                            +"/" + Integer.toString(numberOfQuestions));
                }
                else if(score > 30)
                {
                    resultTextView.setText("Excellent work!\nYour score: "+Integer.toString(score)
                            +"/" + Integer.toString(numberOfQuestions));
                }
                else
                {
                    resultTextView.setText("Well done!\nYour score: "+Integer.toString(score)
                            +"/" + Integer.toString(numberOfQuestions));
                }

                resultTextView.setVisibility(View.VISIBLE);
            }
        }.start();

    }
    public void generateQuestion()
    {

        //generating random number between 0 and 20
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        locationOfCorrectAnswer = rand.nextInt(4);
        //if we don't delete answers
        //new elements will be addded 
        //after the 3rd position..
        //and the correct answers will be from the 4th position to the 7th
	//and we must delete the old answers because
	//we are intrested just in the first 4 positions
        answers.clear();

        int incorrectAnswer;
        for(int i = 0 ; i < 4; ++i)
        {
            if( i == locationOfCorrectAnswer)
            {
                answers.add(a + b);
            }
            else
            {
                //it's possible that the generated answer
                //will be the correct answer

                incorrectAnswer = rand.nextInt(41);
                //loop-ul merge pana cand raspounsul corect difera de raspunsul corect
                while(incorrectAnswer == (a + b))
                {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view)
    {
        Log.i("Button",(String) view.getTag());

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer)))
        {
            Log.i("correct"," correct");
            ++score;
            resultTextView.setTextColor(Color.parseColor("#008000"));
            resultTextView.setText("Correct!");
            resultTextView.setVisibility(View.VISIBLE);
        }
        else
        {
            resultTextView.setTextColor(Color.RED);
            resultTextView.setText("Wrong!");
            resultTextView.setVisibility(View.VISIBLE);
        }
        ++numberOfQuestions;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        generateQuestion();
    }


    public void start(View view)
    {
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
		//we want the counter to start when we press the "GO" button
		//not in onCreate function, because the countdown will start as soon as we run the app
        playAgain(findViewById(R.id.playAgainButton));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView)findViewById(R.id.restultTextView);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gameRelativeLayout = (ConstraintLayout)findViewById(R.id.gameRelativeLayout);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        startButton = (Button) findViewById(R.id.startButton);


    }
}
