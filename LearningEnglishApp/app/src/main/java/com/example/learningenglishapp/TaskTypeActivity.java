package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TaskTypeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_type);

        // Получаем тему из предыдущего экрана
        String topic = getIntent().getStringExtra("TOPIC");
        String topicName = getIntent().getStringExtra("TOPIC_NAME");

        if (topic == null) {
            topic = "animals";
            topicName = "Животные";
        }

        // Создаем final копии для использования в лямбдах
        final String finalTopic = topic;
        final String finalTopicName = topicName;

        // Устанавливаем заголовок
        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText("Тема: " + finalTopicName);
        }

        Button btnYesNo = findViewById(R.id.btnYesNo);
        Button btnSpelling = findViewById(R.id.btnSpelling);
        Button btnMatching = findViewById(R.id.btnMatching);

        // Передаем тему в каждое задание
        btnYesNo.setOnClickListener(v -> {
            Intent intent = new Intent(TaskTypeActivity.this, YesNoActivity.class);
            intent.putExtra("TOPIC", finalTopic);
            intent.putExtra("TOPIC_NAME", finalTopicName);
            startActivity(intent);
        });

        btnSpelling.setOnClickListener(v -> {
            Intent intent = new Intent(TaskTypeActivity.this, SpellingActivity.class);
            intent.putExtra("TOPIC", finalTopic);
            intent.putExtra("TOPIC_NAME", finalTopicName);
            startActivity(intent);
        });

        btnMatching.setOnClickListener(v -> {
            Intent intent = new Intent(TaskTypeActivity.this, MatchingActivity.class);
            intent.putExtra("TOPIC", finalTopic);
            intent.putExtra("TOPIC_NAME", finalTopicName);
            startActivity(intent);
        });

        // Кнопка назад
        // Кнопка назад
        Button btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Intent intent = new Intent(TaskTypeActivity.this, MainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
        }
    }
}