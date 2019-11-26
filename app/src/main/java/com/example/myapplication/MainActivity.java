package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends LoggingActivity {

    private static final int REQUEST_CODE_CHEAT = 1;
    private static final int REQUEST_CODE_STATS = 2;

    private static final String KEY_CURRENT_INDEX = "key_current_index";
    private static final String KEY_SAVE_RESULT = "key_save_result";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    private int[] allResult = new int[mQuestionBank.length];
    private int oneResultCheat = 3;
    private int oneResultTrue = 2;
    private int oneResultFalse = 1;
    private int oneNotAnswered = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            allResult = savedInstanceState.getIntArray(KEY_SAVE_RESULT);
        }

        final TextView questionString = findViewById(R.id.question_string);
        final Question currentQuestion = mQuestionBank[mCurrentIndex];
        questionString.setText(currentQuestion.getQuestionResId());

        Button trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(true);
            }
        });

        Button falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(false);
            }
        });

        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                final Question currentQuestion = mQuestionBank[mCurrentIndex];
                questionString.setText(currentQuestion.getQuestionResId());
            }
        });

        Button cheatButton = findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Question currentQuestion = mQuestionBank[mCurrentIndex];
                Intent intent =
                        CheatActivity.makeIntent(MainActivity.this, currentQuestion.isCorrectAnswer());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        Button statsButton = findViewById(R.id.stats_button);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = StatsActivity.makeIntentStats(MainActivity.this, allResult);
                startActivityForResult(intent, REQUEST_CODE_STATS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (resultCode == RESULT_OK && CheatActivity.correctAnswerWasShown(data)) {
                allResult[mCurrentIndex] = oneResultCheat;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putIntArray(KEY_SAVE_RESULT, allResult);
    }

    private void onButtonClicked(boolean answer) {
        Question currentQuestion = mQuestionBank[mCurrentIndex];
        int toastMessage;

        if (allResult[mCurrentIndex] == oneResultCheat) {
            toastMessage = R.string.judgment_toast;
        } else if (currentQuestion.isCorrectAnswer() == answer){
            toastMessage = R.string.correct_toast;
            allResult[mCurrentIndex] = oneResultTrue;
        } else {
            toastMessage = R.string.incorrect_toast;
            if (allResult[mCurrentIndex] == oneNotAnswered) {
                allResult[mCurrentIndex] = oneResultFalse;
            }
        }

        Toast.makeText(
                MainActivity.this,
                toastMessage,
                Toast.LENGTH_SHORT
        ).show();
    }
}
