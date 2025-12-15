// TaskUtils.java
package com.example.learningenglishapp;

import android.content.Context;
import android.graphics.Color;

public class TaskUtils {

    public static String getTaskTypeName(String typeKey) {
        switch (typeKey) {
            case "yesno": return "Да/Нет";
            case "spelling": return "Правописание";
            case "matching": return "Сопоставление";
            default: return typeKey;
        }
    }

    public static int getColorForProgress(Context context, int progress) {
        if (progress >= 80) {
            return context.getResources().getColor(R.color.success_green);
        } else if (progress >= 50) {
            return context.getResources().getColor(R.color.warning_orange);
        } else if (progress > 0) {
            return context.getResources().getColor(R.color.primary_green);
        } else {
            return context.getResources().getColor(R.color.dark_gray);
        }
    }

    public static String getProgressStatusText(int progress) {
        if (progress >= 80) {
            return "Завершено";
        } else if (progress > 0) {
            return "В процессе";
        } else {
            return "Не начато";
        }
    }
}