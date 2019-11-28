package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends LoggingActivity {

    private static final String KEY_ALL_QUESTIONS = "key_all_questions";
    private static final String KEY_ALL_ANSWERED = "key_all_answered";
    private static final String KEY_TRUE_ANSWERED = "key_true_answered";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        int sumAllQuestion = intent.getIntExtra(KEY_ALL_QUESTIONS, 0);
        int sumAllAnswers = intent.getIntExtra(KEY_ALL_ANSWERED, 0);
        int sumTrueAnswers = intent.getIntExtra(KEY_TRUE_ANSWERED, 0);

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
}
