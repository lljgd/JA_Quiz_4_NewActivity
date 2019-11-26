package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends LoggingActivity {

    private static final String KEY_ALL_RESULT = "key_all_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        int[] saveResult = intent.getIntArrayExtra(KEY_ALL_RESULT);

        int sumAllQuestion = saveResult.length;
        int sumAllAnswers = 0;
        int sumTrueAnswers = 0;

        int oneAnswerIsTrue = 2;
        int oneNotAnswered = 0;

        for (int i = 0; i < sumAllQuestion; i++) {
            if (saveResult[i] != oneNotAnswered) {
                sumAllAnswers++;
                if (saveResult[i] == oneAnswerIsTrue) {
                    sumTrueAnswers++;
                }
            }
        }

        StringBuilder sb = new StringBuilder()
                .append("Отвечено ")
                .append(sumAllAnswers)
                .append("\\")
                .append(sumAllQuestion)
                .append(" вопросов\nПравильных ответов: ")
                .append(sumTrueAnswers);
        TextView statsAnswers = findViewById(R.id.stats_answers);
        statsAnswers.setText(sb.toString());
    }

    public static Intent makeIntentStats (Context context, int[] saveResult) {
        Intent intent = new Intent(context, StatsActivity.class);
        intent.putExtra(KEY_ALL_RESULT, saveResult);
        return intent;
    }
}
