package com.example.learningenglishapp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class ProgressManager {
    private static final String PREFS_NAME = "CourseProgress";
    private static final String KEY_PREFIX = "progress_";

    // Темы курсов (должны совпадать с темами из MainMenuActivity)
    public static final String[] COURSES = {
            "animals",      // Животные
            "food",         // Еда
            "phrases",      // Базовые фразы
            "clothing",     // Одежда
            "nature"        // Природа
    };

    // Типы заданий в каждом курсе
    public static final String[] TASK_TYPES = {
            "yesno",        // Да/Нет
            "spelling",     // Допиши слово
            "matching"      // Сопоставление
    };

    /**
     * Сохранить результат выполнения задания
     */
    public static void saveTaskResult(Context context, String course, String taskType,
                                      int correct, int total) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String key = KEY_PREFIX + course + "_" + taskType;

        // Рассчитываем процент
        int percent = (total > 0) ? (correct * 100) / total : 0;
        int currentBest = prefs.getInt(key, 0);

        // Сохраняем лучший результат
        if (percent > currentBest) {
            editor.putInt(key, percent);
        }

        // Сохраняем количество попыток
        int attempts = prefs.getInt(key + "_attempts", 0) + 1;
        editor.putInt(key + "_attempts", attempts);

        // Сохраняем дату последней попытки
        editor.putLong(key + "_lastAttempt", System.currentTimeMillis());

        // Сохраняем последний результат (даже если не лучший)
        editor.putInt(key + "_lastScore", percent);

        editor.apply();

        // Проверяем статус курса
        checkCourseCompletion(context, course);
    }

    /**
     * Получить прогресс по курсу (в процентах)
     * Считает среднее по ВСЕМ заданиям курса
     */
    public static int getCourseProgress(Context context, String course) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int totalPercent = 0;

        for (String taskType : TASK_TYPES) {
            String key = KEY_PREFIX + course + "_" + taskType;
            int taskPercent = prefs.getInt(key, 0);
            totalPercent += taskPercent;
        }

        // Средний процент по ВСЕМ заданиям курса
        return totalPercent / TASK_TYPES.length;
    }

    /**
     * Получить детальный прогресс по всем заданиям курса
     */
    public static Map<String, Integer> getCourseDetails(Context context, String course) {
        Map<String, Integer> details = new HashMap<>();
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        for (String taskType : TASK_TYPES) {
            String key = KEY_PREFIX + course + "_" + taskType;
            int progress = prefs.getInt(key, 0);
            details.put(taskType, progress);
        }

        return details;
    }

    /**
     * Проверить, завершен ли курс (все задания >= 80%)
     */
    public static boolean isCourseCompleted(Context context, String course) {
        Map<String, Integer> details = getCourseDetails(context, course);

        // Проверяем каждое задание
        for (int progress : details.values()) {
            if (progress < 80) {
                return false;  // Если хоть одно задание < 80%, курс не завершен
            }
        }
        return true;  // Все задания >= 80%
    }

    /**
     * Проверить и обновить статус завершения курса
     */
    private static void checkCourseCompletion(Context context, String course) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isCompleted = isCourseCompleted(context, course);

        prefs.edit()
                .putBoolean(KEY_PREFIX + course + "_completed", isCompleted)
                .putLong(KEY_PREFIX + course + "_completionDate",
                        isCompleted ? System.currentTimeMillis() : 0)
                .apply();
    }

    /**
     * Получить статус курса
     */
    public static String getCourseStatus(Context context, String course) {
        int progress = getCourseProgress(context, course);

        if (progress == 0) {
            return "Не начат";
        } else if (!isCourseCompleted(context, course)) {
            return "В процессе";
        } else {
            return "Завершен";
        }
    }

    /**
     * Сбросить прогресс по курсу
     */
    public static void resetCourseProgress(Context context, String course) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (String taskType : TASK_TYPES) {
            String key = KEY_PREFIX + course + "_" + taskType;
            editor.remove(key);
            editor.remove(key + "_attempts");
            editor.remove(key + "_lastAttempt");
            editor.remove(key + "_lastScore");
        }

        editor.remove(KEY_PREFIX + course + "_completed");
        editor.remove(KEY_PREFIX + course + "_completionDate");
        editor.apply();
    }

    /**
     * Получить общий прогресс по всем курсам
     */
    public static Map<String, Integer> getAllCoursesProgress(Context context) {
        Map<String, Integer> progress = new HashMap<>();

        for (String course : COURSES) {
            progress.put(course, getCourseProgress(context, course));
        }

        return progress;
    }

    /**
     * Получить статистику по всем курсам
     */
    public static Map<String, String> getAllCoursesStatus(Context context) {
        Map<String, String> status = new HashMap<>();

        for (String course : COURSES) {
            status.put(course, getCourseStatus(context, course));
        }

        return status;
    }

    /**
     * Получить количество завершенных курсов
     */
    public static int getCompletedCoursesCount(Context context) {
        int count = 0;

        for (String course : COURSES) {
            if (isCourseCompleted(context, course)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Получить общий прогресс по всем курсам (в процентах)
     */
    public static int getOverallProgress(Context context) {
        int totalProgress = 0;

        for (String course : COURSES) {
            totalProgress += getCourseProgress(context, course);
        }

        return COURSES.length > 0 ? totalProgress / COURSES.length : 0;
    }
}