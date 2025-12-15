package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class MainMenuActivity extends Activity {

    // ID кнопок (теперь как константы)
    private static final int BUTTON_ANIMALS = R.id.btnAnimals;
    private static final int BUTTON_FOOD = R.id.btnFood;
    private static final int BUTTON_PHRASES = R.id.btnPhrases;
    private static final int BUTTON_CLOTHING = R.id.btnClothing;
    private static final int BUTTON_NATURE = R.id.btnNature;

    // Массив ID кнопок (инициализируем в onCreate)
    private int[] buttonIds;

    // Соответствующие темы
    private String[] topics = {
            "animals",
            "food",
            "phrases",
            "clothing",
            "nature"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Инициализируем массив ID кнопок
        buttonIds = new int[] {
                BUTTON_ANIMALS,
                BUTTON_FOOD,
                BUTTON_PHRASES,
                BUTTON_CLOTHING,
                BUTTON_NATURE
        };

        // Инициализация кнопок
        for (int i = 0; i < buttonIds.length; i++) {
            Button button = findViewById(buttonIds[i]);
            final String topic = topics[i];

            button.setOnClickListener(v -> startTopic(topic));

            // Начальная установка стиля кнопки
            updateButtonStyle(button, 0);
        }

        // Обновляем прогресс и статистику
        updateProgressOnButtons();
        updateStatistics();
    }

    private void startTopic(String topic) {
        Intent intent = new Intent(MainMenuActivity.this, TaskTypeActivity.class);
        intent.putExtra("TOPIC", topic);
        intent.putExtra("TOPIC_NAME", getTopicDisplayName(topic));
        startActivity(intent);
    }

    private String getTopicDisplayName(String topic) {
        switch (topic) {
            case "animals": return "Животные";
            case "food": return "Еда";
            case "phrases": return "Базовые фразы";
            case "clothing": return "Одежда";
            case "nature": return "Природа";
            default: return "Животные";
        }
    }

    private void updateProgressOnButtons() {
        // Получаем прогресс по всем курсам
        Map<String, Integer> allProgress = ProgressManager.getAllCoursesProgress(this);

        for (int i = 0; i < topics.length; i++) {
            Button button = findViewById(buttonIds[i]);
            if (button != null) {
                int progress = allProgress.getOrDefault(topics[i], 0);

                // Формируем текст кнопки с прогрессом
                String originalText = getOriginalButtonText(buttonIds[i]);
                String progressText;

                if (progress > 0) {
                    if (progress >= 80) {
                        // Курс пройден - добавляем галочку
                        progressText = "✓ " + originalText + " (" + progress + "%)";
                    } else {
                        // Курс в процессе
                        progressText = originalText + " (" + progress + "%)";
                    }
                } else {
                    progressText = originalText;
                }

                button.setText(progressText);

                // Обновляем стиль кнопки в зависимости от прогресса
                updateButtonStyle(button, progress);
            }
        }
    }

    private String getOriginalButtonText(int buttonId) {
        if (buttonId == BUTTON_ANIMALS) {
            return "Животные";
        } else if (buttonId == BUTTON_FOOD) {
            return "Еда";
        } else if (buttonId == BUTTON_PHRASES) {
            return "Базовые фразы";
        } else if (buttonId == BUTTON_CLOTHING) {
            return "Одежда";
        } else if (buttonId == BUTTON_NATURE) {
            return "Природа";
        }
        return "";
    }

    private void updateButtonStyle(Button button, int progress) {
        int buttonId = button.getId();
        int baseColor = getOriginalButtonColor(buttonId);

        if (progress >= 80) {
            // Курс пройден - темнее и с зеленой обводкой
            button.setBackgroundColor(darkenColor(baseColor, 0.8f));
            button.getBackground().setColorFilter(baseColor, PorterDuff.Mode.SRC_ATOP);
            button.getBackground().setAlpha(230);
            button.setElevation(8f); // Добавляем тень
        } else if (progress > 0) {
            // Курс в процессе - полупрозрачный
            button.setBackgroundColor(baseColor);
            button.getBackground().setAlpha(200);
            button.setElevation(4f);
        } else {
            // Не начат - нормальный вид
            button.setBackgroundColor(baseColor);
            button.getBackground().setAlpha(255);
            button.setElevation(2f);
        }

        // Белый текст на всех кнопках
        button.setTextColor(getResources().getColor(R.color.white));
    }

    private int getOriginalButtonColor(int buttonId) {
        // Возвращает цвет кнопки из ресурсов
        if (buttonId == BUTTON_ANIMALS) {
            return getResources().getColor(R.color.animal_color);
        } else if (buttonId == BUTTON_FOOD) {
            return getResources().getColor(R.color.food_color);
        } else if (buttonId == BUTTON_PHRASES) {
            return getResources().getColor(R.color.phrases_color);
        } else if (buttonId == BUTTON_CLOTHING) {
            return getResources().getColor(R.color.clothing_color);
        } else if (buttonId == BUTTON_NATURE) {
            return getResources().getColor(R.color.nature_color);
        }
        return getResources().getColor(R.color.primary_green);
    }

    private int darkenColor(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= factor; // Уменьшаем значение (яркость)
        return Color.HSVToColor(hsv);
    }

    private void updateStatistics() {
        TextView tvStats = findViewById(R.id.tvStats);
        if (tvStats != null) {
            Map<String, Integer> allProgress = ProgressManager.getAllCoursesProgress(this);

            // Считаем статистику
            int completedCourses = 0;
            int inProgressCourses = 0;
            int totalProgress = 0;
            int coursesWithProgress = 0;

            for (int progress : allProgress.values()) {
                if (progress >= 80) {
                    completedCourses++;
                } else if (progress > 0) {
                    inProgressCourses++;
                }
                if (progress > 0) {
                    totalProgress += progress;
                    coursesWithProgress++;
                }
            }

            // Пример: общее количество слов во всех словарях
            int totalWords = 0;
            try {
                totalWords = VocabularyManager.getVocabulary("animals").getVocabularySize() +
                        VocabularyManager.getVocabulary("food").getVocabularySize() +
                        VocabularyManager.getVocabulary("phrases").getVocabularySize() +
                        VocabularyManager.getVocabulary("clothing").getVocabularySize() +
                        VocabularyManager.getVocabulary("nature").getVocabularySize();
            } catch (Exception e) {
                totalWords = 250; // Значение по умолчанию
            }

            // Формируем строку статистики с иконками
            StringBuilder stats = new StringBuilder();

            if (completedCourses > 0) {
                stats.append("Пройдено: ").append(completedCourses).append("/5\n");
            }

            if (inProgressCourses > 0) {
                stats.append("В процессе: ").append(inProgressCourses).append("\n");
            }

            if (coursesWithProgress > 0) {
                int averageProgress = totalProgress / coursesWithProgress;
                stats.append("Средний прогресс: ").append(averageProgress).append("%\n");
            }

            stats.append("Всего слов: ").append(totalWords);

            tvStats.setText(stats.toString());
            tvStats.setTextColor(getResources().getColor(R.color.dark_gray));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Обновляем прогресс при возвращении на экран
        updateProgressOnButtons();
        updateStatistics();
    }
}