package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.learningenglishapp.dictionary.BaseVocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SpellingActivity extends Activity {

    private TextView tvQ;
    private EditText et;
    private Button btnNext;
    private Button btnBack;

    private List<String> englishWords;
    private List<String> questions;
    private List<String> answers;
    private int index = 0;
    private int correct = 0;
    private Random random = new Random();
    private BaseVocabulary vocabulary;

    // Для сбора ошибок
    private StringBuilder mistakes = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling);

        tvQ = findViewById(R.id.tvSpellQ);
        et = findViewById(R.id.etSpell);
        btnNext = findViewById(R.id.btnSpellNext);
        btnBack = findViewById(R.id.btnBackSpell);

        // Получаем тему
        String topic = getIntent().getStringExtra("TOPIC");
        String topicName = getIntent().getStringExtra("TOPIC_NAME");

        // Устанавливаем заголовок
        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null && topicName != null) {
            tvTitle.setText("Допиши слово: " + topicName);
        }

        // Инициализируем словарь через VocabularyManager
        initializeVocabulary(topic);

        // Генерируем вопросы
        generateSpellingQuestions();

        load();

        btnNext.setOnClickListener(v -> check());
        btnBack.setOnClickListener(v -> goBack());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Обработка кнопки "Назад" на устройстве
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initializeVocabulary(String topic) {
        // Получаем словарь через VocabularyManager
        vocabulary = VocabularyManager.getVocabulary(topic);

        // Получаем случайные английские слова
        englishWords = VocabularyManager.getRandomEnglishWords(topic, 10);
    }

    private void generateSpellingQuestions() {
        questions = new ArrayList<>();
        answers = new ArrayList<>();

        if (englishWords == null || englishWords.isEmpty()) {
            // Если нет слов, создаем заглушки
            questions.add("CAT");
            answers.add("CAT");
            return;
        }

        // Выбираем случайные слова для задания (максимум 5)
        Collections.shuffle(englishWords);
        int count = Math.min(5, englishWords.size());

        for (int i = 0; i < count; i++) {
            String word = englishWords.get(i).toUpperCase();
            String question = createSpellingQuestion(word);
            questions.add(question);
            answers.add(word);
        }
    }

    private String createSpellingQuestion(String word) {
        if (word == null || word.length() <= 2) {
            return word; // Слишком короткое слово, не скрываем буквы
        }

        char[] chars = word.toCharArray();
        int lettersToHide = Math.max(1, word.length() / 3); // Скрываем примерно 1/3 букв

        // Создаем массив индексов для скрытия
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            // Не скрываем первую и последнюю буквы в длинных словах
            if (word.length() > 4 && (i == 0 || i == word.length() - 1)) {
                continue;
            }
            indices.add(i);
        }

        // Если после фильтрации не осталось индексов, добавляем все
        if (indices.isEmpty()) {
            for (int i = 0; i < word.length(); i++) {
                indices.add(i);
            }
        }

        Collections.shuffle(indices);

        // Скрываем буквы
        int hiddenCount = 0;
        for (int i = 0; i < indices.size() && hiddenCount < lettersToHide; i++) {
            int indexToHide = indices.get(i);
            chars[indexToHide] = '_';
            hiddenCount++;
        }

        return new String(chars);
    }

    private void load() {
        if (index < questions.size()) {
            String questionText = "Допишите слово: " + questions.get(index);
            tvQ.setText(questionText);
            et.setText("");
            et.requestFocus();

            // Обновляем счетчик вопросов
            TextView tvCounter = findViewById(R.id.tvCounter);
            if (tvCounter != null) {
                tvCounter.setText(String.format("Вопрос %d/%d", index + 1, questions.size()));
            }
        } else {
            finishTest();
        }
    }

    private void check() {
        String userAnswer = et.getText().toString().trim().toUpperCase();

        if (userAnswer.isEmpty()) {
            et.setError("Введите ответ");
            et.requestFocus();
            return;
        }

        String correctAnswer = answers.get(index);
        String currentQuestion = questions.get(index);

        if (userAnswer.equals(correctAnswer)) {
            correct++;
            et.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            et.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

            // Добавляем ошибку с правильным ответом
            mistakes.append(currentQuestion)
                    .append(" → ")
                    .append(userAnswer)
                    .append(" ❌ (правильно: ")
                    .append(correctAnswer)
                    .append(")\n");
        }

        index++;

        // Небольшая задержка перед следующим вопросом
        et.postDelayed(() -> {
            et.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            load();
        }, 500);
    }

    private void finishTest() {
        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra("correct", correct);
        i.putExtra("total", questions.size());
        i.putExtra("mistakes", mistakes.toString()); // Передаем собранные ошибки
        i.putExtra("topic", getIntent().getStringExtra("TOPIC"));
        i.putExtra("topic_name", getIntent().getStringExtra("TOPIC_NAME"));
        i.putExtra("task_type", "spelling");
        startActivity(i);
        finish();
    }

    private void goBack() {
        Intent intent = new Intent(this, TaskTypeActivity.class);
        intent.putExtra("TOPIC", getIntent().getStringExtra("TOPIC"));
        intent.putExtra("TOPIC_NAME", getIntent().getStringExtra("TOPIC_NAME"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}