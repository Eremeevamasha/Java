package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.learningenglishapp.dictionary.BaseVocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchingActivity extends Activity {

    private Button[] engButtons = new Button[3];
    private Button[] rusButtons = new Button[3];
    private Button btnCheck, btnBack;

    // Храним пары: английское слово -> русское слово
    private Map<String, String> selectedPairs = new HashMap<>();
    private Map<String, Button> englishButtonMap = new HashMap<>();
    private Map<String, Button> russianButtonMap = new HashMap<>();

    private List<String> englishWords = new ArrayList<>();
    private List<String> russianWords = new ArrayList<>();
    private Map<String, String> correctPairs = new HashMap<>();

    private int correct = 0;
    private StringBuilder mistakes = new StringBuilder();
    private BaseVocabulary vocabulary;

    // Отслеживаем выбранные кнопки
    private String selectedEnglish = null;
    private String selectedRussian = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        // Инициализация кнопок английских слов
        engButtons[0] = findViewById(R.id.eng1);
        engButtons[1] = findViewById(R.id.eng2);
        engButtons[2] = findViewById(R.id.eng3);

        // Инициализация кнопок русских слов
        rusButtons[0] = findViewById(R.id.rus1);
        rusButtons[1] = findViewById(R.id.rus2);
        rusButtons[2] = findViewById(R.id.rus3);

        btnCheck = findViewById(R.id.btnCheckMatching);
        btnBack = findViewById(R.id.btnBackMatching);

        // Получаем тему
        String topic = getIntent().getStringExtra("TOPIC");
        String topicName = getIntent().getStringExtra("TOPIC_NAME");

        // Устанавливаем заголовок с названием темы
        android.widget.TextView tvTitle = findViewById(R.id.tvMatchingTitle);
        if (tvTitle != null && topicName != null) {
            tvTitle.setText("Сопоставьте: " + topicName);
        }

        // Инициализируем словарь
        initializeVocabulary(topic);

        // Назначаем обработчики кликов - используем финальные копии
        for (int i = 0; i < 3; i++) {
            final Button engButton = engButtons[i];
            final Button rusButton = rusButtons[i];

            final String engWord = englishWords.get(i);
            final String rusWord = russianWords.get(i);

            engButton.setText(engWord);
            rusButton.setText(rusWord);

            englishButtonMap.put(engWord, engButton);
            russianButtonMap.put(rusWord, rusButton);

            // Обработчики для английских кнопок
            engButton.setOnClickListener(v -> onEnglishClick(engWord, engButton));

            // Обработчики для русских кнопок
            rusButton.setOnClickListener(v -> onRussianClick(rusWord, rusButton));
        }

        btnCheck.setOnClickListener(v -> checkResults());
        btnBack.setOnClickListener(v -> goBack());

        // Обновляем начальный статус
        updateStatus();

        // Сначала кнопка проверки отключена
        btnCheck.setEnabled(false);
    }

    private void initializeVocabulary(String topic) {
        // Используем VocabularyManager для получения словаря
        vocabulary = VocabularyManager.getVocabulary(topic);

        // Получаем случайные пары слов
        Map<String, String> pairs = VocabularyManager.getRandomWordPairs(topic, 3);

        englishWords = new ArrayList<>(pairs.keySet());
        russianWords = new ArrayList<>(pairs.values());
        correctPairs = new HashMap<>(pairs);

        // Перемешиваем русские слова для отображения
        Collections.shuffle(russianWords);
    }

    private void onEnglishClick(String engWord, Button button) {
        // Если это слово уже сопоставлено, игнорируем
        if (selectedPairs.containsKey(engWord)) {
            return;
        }

        // Сбрасываем предыдущий выбор английского слова
        if (selectedEnglish != null && !selectedEnglish.equals(engWord)) {
            // Только если предыдущий выбор не сопоставлен
            if (!selectedPairs.containsKey(selectedEnglish)) {
                englishButtonMap.get(selectedEnglish).setBackgroundColor(
                        getResources().getColor(R.color.primary_green));
            }
        }

        // Выбираем новое английское слово
        selectedEnglish = engWord;
        button.setBackgroundColor(getResources().getColor(R.color.accent_green));

        // Проверяем, выбрано ли уже русское слово
        if (selectedRussian != null) {
            createPair(selectedEnglish, selectedRussian);
        }
    }

    private void onRussianClick(String rusWord, Button button) {
        // Проверяем, не сопоставлено ли уже это русское слово
        if (selectedPairs.containsValue(rusWord)) {
            return;
        }

        // Сбрасываем предыдущий выбор русского слова
        if (selectedRussian != null && !selectedRussian.equals(rusWord)) {
            // Только если предыдущий выбор не сопоставлен
            if (!selectedPairs.containsValue(selectedRussian)) {
                russianButtonMap.get(selectedRussian).setBackgroundColor(
                        getResources().getColor(R.color.primary_green));
            }
        }

        // Выбираем новое русское слово
        selectedRussian = rusWord;
        button.setBackgroundColor(getResources().getColor(R.color.warning_orange));

        // Проверяем, выбрано ли уже английское слово
        if (selectedEnglish != null) {
            createPair(selectedEnglish, selectedRussian);
        }
    }

    private void createPair(String english, String russian) {
        // Сохраняем пару
        selectedPairs.put(english, russian);

        // Проверяем правильность
        String correctRussian = correctPairs.get(english);
        if (correctRussian != null && correctRussian.equals(russian)) {
            correct++;
            // Правильная пара
            englishButtonMap.get(english).setBackgroundColor(
                    getResources().getColor(R.color.success_green));
            russianButtonMap.get(russian).setBackgroundColor(
                    getResources().getColor(R.color.success_green));
        } else {
            // Неправильная пара
            mistakes.append(english).append(" → ").append(russian)
                    .append(" (правильно: ").append(correctRussian).append(")\n");
            englishButtonMap.get(english).setBackgroundColor(
                    getResources().getColor(R.color.error_red));
            russianButtonMap.get(russian).setBackgroundColor(
                    getResources().getColor(R.color.error_red));
        }

        // Делаем кнопки неактивными
        englishButtonMap.get(english).setEnabled(false);
        russianButtonMap.get(russian).setEnabled(false);

        // Сбрасываем выбор
        selectedEnglish = null;
        selectedRussian = null;

        // Обновляем статус
        updateStatus();

        // Проверяем, все ли пары найдены
        if (selectedPairs.size() == 3) {
            btnCheck.setEnabled(true);
            // Toast.makeText(this, "Все пары найдены! Проверьте результат", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStatus() {
        // Обновляем TextView статуса, если он есть
        android.widget.TextView tvStatus = findViewById(R.id.tvStatus);
        if (tvStatus != null) {
            tvStatus.setText("Сопоставлено: " + selectedPairs.size() + " из 3");
        }
    }

    private void checkResults() {
        // Добавляем task_type для ProgressManager
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("correct", correct);
        intent.putExtra("total", 3);
        intent.putExtra("mistakes", mistakes.toString());
        intent.putExtra("topic", getIntent().getStringExtra("TOPIC"));
        intent.putExtra("topic_name", getIntent().getStringExtra("TOPIC_NAME"));
        intent.putExtra("task_type", "matching"); // Важно для сохранения прогресса
        intent.putExtra("TASK_TYPE", "matching");
        startActivity(intent);
        finish();
    }

    private void goBack() {
        Intent i = new Intent(this, TaskTypeActivity.class);
        i.putExtra("TOPIC", getIntent().getStringExtra("TOPIC"));
        i.putExtra("TOPIC_NAME", getIntent().getStringExtra("TOPIC_NAME"));
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}