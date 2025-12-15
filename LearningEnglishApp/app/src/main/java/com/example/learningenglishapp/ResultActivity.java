package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Получаем данные
        int correct = getIntent().getIntExtra("correct", 0);
        int total = getIntent().getIntExtra("total", 1);
        String mistakes = getIntent().getStringExtra("mistakes");
        String topic = getIntent().getStringExtra("topic");
        String topicName = getIntent().getStringExtra("topic_name");
        String taskType = getIntent().getStringExtra("task_type");

        // Сохраняем результат
        if (topic != null && taskType != null) {
            ProgressManager.saveTaskResult(this, topic, taskType, correct, total);
        }

        // Получаем прогресс по курсу
        int courseProgress = ProgressManager.getCourseProgress(this, topic);
        boolean isCourseCompleted = ProgressManager.isCourseCompleted(this, topic);

        TextView tvResult = findViewById(R.id.tvResult);
        TextView tvExtra = findViewById(R.id.tvExtra);
        Button btnRepeat = findViewById(R.id.btnRepeat);
        Button btnBackToTasks = findViewById(R.id.btnBackToTasks);

        // Устанавливаем результат задания
        int taskPercent = total > 0 ? (correct * 100) / total : 0;
        String resultText = String.format("Задание: %d/%d (%d%%)\n", correct, total, taskPercent);
        resultText += String.format("Прогресс курса: %d%%", courseProgress);
        tvResult.setText(resultText);

        // Меняем цвет текста результата в зависимости от прогресса
        if (courseProgress >= 80) {
            tvResult.setTextColor(getResources().getColor(R.color.success_green));
        } else if (courseProgress >= 50) {
            tvResult.setTextColor(getResources().getColor(R.color.warning_orange));
        } else {
            tvResult.setTextColor(getResources().getColor(R.color.error_red));
        }

        // Формируем дополнительную информацию
        StringBuilder extraText = new StringBuilder();

        // Показываем статус курса
        if (isCourseCompleted) {
            extraText.append("🎉 ПОЗДРАВЛЯЕМ! Курс '").append(topicName).append("' пройден!\n\n");
            extraText.append("Все задания выполнены на 80% или выше.\n\n");
        } else if (courseProgress >= 80) {
            extraText.append("✅ Отлично! Курс пройден.\n\n");
        }

        // ВСЕГДА показываем статистику со шкалой
        extraText.append("📊 Прогресс по заданиям:\n\n");
        Map<String, Integer> details = ProgressManager.getCourseDetails(this, topic);

        for (Map.Entry<String, Integer> entry : details.entrySet()) {
            String typeName = getTaskTypeName(entry.getKey());
            int progress = entry.getValue();
            String progressBar = getProgressBar(progress);

            if (progress >= 80) {
                extraText.append("✅ ");
            } else if (progress > 0) {
                extraText.append("⏳ ");
            } else {
                extraText.append("◻️ ");
            }

            extraText.append(typeName).append(": ").append(progressBar)
                    .append(" ").append(progress).append("%\n");
        }

        // Показываем требование только если курс не пройден
        if (!isCourseCompleted) {
            extraText.append("\nДля завершения курса нужно набрать минимум 80% по всем заданиям\n");
        }

        // Добавляем ошибки если есть
        if (mistakes != null && !mistakes.isEmpty()) {
            extraText.append("\nОшибки и правильные ответы:\n\n");

            if (taskType != null) {
                // Разделяем ошибки по строкам
                String[] errorLines = mistakes.split("\n");

                // Для разных типов заданий разный формат отображения
                switch (taskType) {
                    case "matching":
                        // Для сопоставления: слово → неправильный перевод (правильно: правильный перевод)
                        for (String line : errorLines) {
                            if (!line.trim().isEmpty()) {
                                extraText.append("• ").append(formatMatchingError(line)).append("\n");
                            }
                        }
                        break;

                    case "yesno":
                        // Для Да/Нет: вопрос - ваш ответ ❌ (правильный ответ: ...)
                        for (String line : errorLines) {
                            if (!line.trim().isEmpty()) {
                                extraText.append("• ").append(formatYesNoError(line)).append("\n");
                            }
                        }
                        break;

                    case "spelling":
                        // Для правописания: задание - ваш ответ ❌ (правильно: ...)
                        for (String line : errorLines) {
                            if (!line.trim().isEmpty()) {
                                extraText.append("• ").append(formatSpellingError(line)).append("\n");
                            }
                        }
                        break;

                    default:
                        // Для неизвестных типов показываем как есть
                        for (String line : errorLines) {
                            if (!line.trim().isEmpty()) {
                                extraText.append("• ").append(line).append("\n");
                            }
                        }
                        break;
                }
            }
        }

        tvExtra.setText(extraText.toString());
        tvExtra.setTextColor(getResources().getColor(R.color.text_gray));

        // Настраиваем кнопки
        setupButtons(btnRepeat, btnBackToTasks, courseProgress, topic, topicName);
    }

    private String getTaskTypeName(String typeKey) {
        switch (typeKey) {
            case "yesno": return "Да/Нет";
            case "spelling": return "Правописание";
            case "matching": return "Сопоставление";
            default: return typeKey;
        }
    }

    private String getProgressBar(int percent) {
        int bars = percent / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            if (i < bars) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Форматирует ошибки для задания "Сопоставление"
     * Пример входной строки: "cat → кошка (правильно: кот)"
     */
    private String formatMatchingError(String errorLine) {
        if (errorLine.contains("(правильно:")) {
            return errorLine.replace("(правильно:", "❌ (правильно:");
        } else {
            return errorLine + " ❌";
        }
    }

    /**
     * Форматирует ошибки для задания "Да/Нет"
     * Пример входной строки: "cat — это кот? Вы ответили: Да ❌ (правильно: Да)"
     */
    private String formatYesNoError(String errorLine) {
        if (errorLine.contains("❌") || errorLine.contains("✅")) {
            // Уже содержит значок, возвращаем как есть
            return errorLine;
        } else if (errorLine.contains("(правильно:")) {
            // Есть правильный ответ, добавляем значок ошибки
            return errorLine.replace("(правильно:", "❌ (правильно:");
        } else {
            return errorLine + " ❌";
        }
    }

    /**
     * Форматирует ошибки для задания "Правописание"
     * Пример входной строки: "C_T - CAT ❌ (правильно: CAT)"
     */
    private String formatSpellingError(String errorLine) {
        if (errorLine.contains("❌") || errorLine.contains("✅")) {
            // Уже содержит значок, возвращаем как есть
            return errorLine;
        } else if (errorLine.contains("(правильно:")) {
            // Есть правильный ответ, добавляем значок ошибки
            return errorLine.replace("(правильно:", "❌ (правильно:");
        } else {
            return errorLine + " ❌";
        }
    }

    private void setupButtons(Button btnRepeat, Button btnBackToTasks,
                              int courseProgress, String topic, String topicName) {

        // Кнопка "Повторить курс" - проверяем завершение курса
        boolean isCompleted = ProgressManager.isCourseCompleted(this, topic);

        if (!isCompleted) {
            btnRepeat.setText("Повторить курс");
            btnRepeat.setBackgroundColor(getResources().getColor(R.color.warning_orange));
            btnRepeat.setOnClickListener(v -> {
                ProgressManager.resetCourseProgress(this, topic);
                Intent intent = new Intent(ResultActivity.this, TaskTypeActivity.class);
                intent.putExtra("TOPIC", topic);
                intent.putExtra("TOPIC_NAME", topicName);
                startActivity(intent);
                finish();
            });
        } else {
            btnRepeat.setText("Курс пройден!");
            btnRepeat.setBackgroundColor(getResources().getColor(R.color.success_green));
            btnRepeat.setEnabled(false);
        }

        // Белый текст на кнопках
        btnRepeat.setTextColor(getResources().getColor(R.color.white));

        // Кнопка "Назад к заданиям"
        btnBackToTasks.setText("Другие задания");
        btnBackToTasks.setBackgroundColor(getResources().getColor(R.color.primary_green));
        btnBackToTasks.setTextColor(getResources().getColor(R.color.white));

        btnBackToTasks.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, TaskTypeActivity.class);
            intent.putExtra("TOPIC", topic);
            intent.putExtra("TOPIC_NAME", topicName);
            startActivity(intent);
            finish();
        });
    }
}