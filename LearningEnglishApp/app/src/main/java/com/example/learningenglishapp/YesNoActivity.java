package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learningenglishapp.dictionary.BaseVocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class YesNoActivity extends Activity {

    private TextView tvQ;
    private TextView tvCounter;
    private Button btnSkip;
    private Button btnBack;
    private LinearLayout swipeCard;

    private List<String> englishWords;
    private List<String> allRussianWords;
    private List<String> correctRussianWords;
    private List<QuestionData> questions;
    private int index = 0;
    private int correct = 0;
    private Random random = new Random();
    private BaseVocabulary vocabulary;
    private GestureDetector gestureDetector;

    // Для сбора ошибок
    private StringBuilder mistakes = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_no);

        tvQ = findViewById(R.id.tvQ);
        tvCounter = findViewById(R.id.tvCounter);
        swipeCard = findViewById(R.id.swipeCard);
        btnSkip = findViewById(R.id.btnSkip);
        btnBack = findViewById(R.id.btnBack);

        String topic = getIntent().getStringExtra("TOPIC");
        String topicName = getIntent().getStringExtra("TOPIC_NAME");

        TextView tvTitle = findViewById(R.id.tvTitle);
        if (tvTitle != null && topicName != null) {
            tvTitle.setText("Да/Нет: " + topicName);
        }

        initializeVocabulary(topic);
        generateQuestions();
        showQuestion();
        initializeSwipeGestures();

        btnSkip.setOnClickListener(v -> skipQuestion());
        btnBack.setOnClickListener(v -> goBack());
    }

    private void initializeSwipeGestures() {
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    float diffX = e2.getX() - e1.getX();
                    float diffY = e2.getY() - e1.getY();

                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                handleSwipeRight();
                            } else {
                                handleSwipeLeft();
                            }
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        swipeCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    private void handleSwipeRight() {
        animateCardSwipe(true);
        processAnswer(true);
    }

    private void handleSwipeLeft() {
        animateCardSwipe(false);
        processAnswer(false);
    }

    private void animateCardSwipe(boolean isRight) {
        TranslateAnimation animation;
        if (isRight) {
            animation = new TranslateAnimation(0, 300, 0, 0);
            swipeCard.setBackgroundResource(R.drawable.card_swipe_right_background);
        } else {
            animation = new TranslateAnimation(0, -300, 0, 0);
            swipeCard.setBackgroundResource(R.drawable.card_swipe_left_background);
        }

        animation.setDuration(250);
        animation.setFillAfter(false);
        swipeCard.startAnimation(animation);

        animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                TranslateAnimation returnAnimation = new TranslateAnimation(
                        isRight ? 300 : -300, 0, 0, 0);
                returnAnimation.setDuration(150);
                returnAnimation.setFillAfter(true);
                swipeCard.startAnimation(returnAnimation);

                btnSkip.postDelayed(() -> {
                    swipeCard.setBackgroundResource(R.drawable.card_swipeable_background);
                }, 150);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}
        });
    }

    private void processAnswer(boolean userAnswer) {
        if (index >= questions.size()) return;

        QuestionData question = questions.get(index);
        boolean correctAnswer = question.isCorrect;
        boolean isTranslationCorrect = question.russianWord.equals(question.correctTranslation);

        if (question.isCorrect != isTranslationCorrect) {
            correctAnswer = isTranslationCorrect;
        }

        String userAnswerText = userAnswer ? "Да" : "Нет";
        String correctAnswerText = correctAnswer ? "Да" : "Нет";

        if (userAnswer == correctAnswer) {
            correct++;
            // Toast.makeText(this, "✓ Верно!", Toast.LENGTH_SHORT).show();
        } else {
            // Toast.makeText(this, "✗ Неверно!", Toast.LENGTH_SHORT).show();

            // Добавляем ошибку с правильным ответом
            mistakes.append(question.englishWord)
                    .append(" — это ")
                    .append(question.russianWord)
                    .append("? Вы ответили: ")
                    .append(userAnswerText)
                    .append(" ❌ (правильно: ")
                    .append(correctAnswerText)
                    .append(")\n");
        }

        swipeCard.setOnTouchListener(null);

        btnSkip.postDelayed(() -> {
            initializeSwipeGestures();
            index++;
            showQuestion();
        }, 1000);
    }

    private void skipQuestion() {
        if (index >= questions.size()) {
            finishTest();
            return;
        }

        // Toast.makeText(this, "Вопрос пропущен", Toast.LENGTH_SHORT).show();

        // Добавляем пропущенный вопрос как ошибку
        QuestionData question = questions.get(index);
        mistakes.append(question.englishWord)
                .append(" — это ")
                .append(question.russianWord)
                .append("? ❌ (вопрос пропущен)")
                .append("\n");

        index++;
        showQuestion();
    }

    private void initializeVocabulary(String topic) {
        vocabulary = VocabularyManager.getVocabulary(topic);

        if (vocabulary == null) {
            // Toast.makeText(this, "Ошибка загрузки словаря", Toast.LENGTH_SHORT).show();
            goBack();
            return;
        }

        englishWords = new ArrayList<>();
        correctRussianWords = new ArrayList<>();
        allRussianWords = new ArrayList<>();

        List<String> randomEnglishWords = VocabularyManager.getRandomEnglishWords(topic, 15);
        if (randomEnglishWords != null && !randomEnglishWords.isEmpty()) {
            englishWords.addAll(randomEnglishWords);
        } else {
            englishWords.add("cat");
            englishWords.add("dog");
            englishWords.add("house");
            englishWords.add("book");
            englishWords.add("table");
        }

        for (String englishWord : englishWords) {
            String russianTranslation = vocabulary.getRussianTranslation(englishWord);
            if (russianTranslation != null) {
                correctRussianWords.add(russianTranslation);
            } else {
                correctRussianWords.add("перевод не найден");
            }
        }

        if (vocabulary != null) {
            String[] allRussianArray = vocabulary.getAllRussianWords();
            if (allRussianArray != null && allRussianArray.length > 0) {
                Set<String> uniqueRussianWords = new HashSet<>();
                Collections.addAll(uniqueRussianWords, allRussianArray);
                allRussianWords.addAll(uniqueRussianWords);
            } else {
                allRussianWords.add("кот");
                allRussianWords.add("собака");
                allRussianWords.add("дом");
                allRussianWords.add("книга");
                allRussianWords.add("стол");
                allRussianWords.add("окно");
                allRussianWords.add("стул");
            }
        }
    }

    private void generateQuestions() {
        questions = new ArrayList<>();

        if (englishWords == null || englishWords.isEmpty() ||
                correctRussianWords == null || correctRussianWords.isEmpty() ||
                englishWords.size() != correctRussianWords.size()) {

            questions.add(new QuestionData("cat", "кот", true, "кот"));
            questions.add(new QuestionData("dog", "кошка", false, "собака"));
            questions.add(new QuestionData("house", "дом", true, "дом"));
            questions.add(new QuestionData("book", "стол", false, "книга"));
            return;
        }

        int maxQuestions = Math.min(10, englishWords.size());

        List<Integer> availableIndices = new ArrayList<>();
        for (int i = 0; i < englishWords.size(); i++) {
            availableIndices.add(i);
        }
        Collections.shuffle(availableIndices);

        for (int i = 0; i < maxQuestions; i++) {
            int englishIndex = availableIndices.get(i % availableIndices.size());

            String englishWord = englishWords.get(englishIndex);
            String correctRussian = correctRussianWords.get(englishIndex);

            if (correctRussian == null || correctRussian.equals("перевод не найден")) {
                continue;
            }

            boolean isCorrectQuestion = random.nextBoolean();
            String displayedRussian;

            if (isCorrectQuestion) {
                displayedRussian = correctRussian;
            } else {
                if (allRussianWords.size() <= 1) {
                    displayedRussian = correctRussian + "XXX";
                } else {
                    String wrongRussian;
                    int attempts = 0;
                    do {
                        int randomIndex = random.nextInt(allRussianWords.size());
                        wrongRussian = allRussianWords.get(randomIndex);
                        attempts++;
                    } while (wrongRussian.equals(correctRussian) && attempts < 50);

                    if (attempts >= 50) {
                        wrongRussian = correctRussian + " (неправильно)";
                    }
                    displayedRussian = wrongRussian;
                }
            }

            questions.add(new QuestionData(englishWord, displayedRussian, isCorrectQuestion, correctRussian));
        }
    }

    private void showQuestion() {
        if (index < questions.size()) {
            QuestionData question = questions.get(index);
            String questionText = question.englishWord + " — это " + question.russianWord + "?";
            tvQ.setText(questionText);

            if (tvCounter != null) {
                tvCounter.setText(String.format("Вопрос %d/%d", index + 1, questions.size()));
            }
        } else {
            finishTest();
        }
    }

    private void finishTest() {
        int percentage = questions.size() > 0 ? (correct * 100) / questions.size() : 0;

        if (questions.isEmpty()) {
            // Toast.makeText(this, "Не удалось загрузить вопросы", Toast.LENGTH_LONG).show();
            goBack();
            return;
        }

        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra("correct", correct);
        i.putExtra("total", questions.size());
        i.putExtra("mistakes", mistakes.toString()); // Передаем собранные ошибки
        i.putExtra("topic", getIntent().getStringExtra("TOPIC"));
        i.putExtra("topic_name", getIntent().getStringExtra("TOPIC_NAME"));
        i.putExtra("task_type", "yesno");

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private static class QuestionData {
        String englishWord;
        String russianWord;
        boolean isCorrect;
        String correctTranslation;

        QuestionData(String englishWord, String russianWord, boolean isCorrect, String correctTranslation) {
            this.englishWord = englishWord;
            this.russianWord = russianWord;
            this.isCorrect = isCorrect;
            this.correctTranslation = correctTranslation;
        }
    }
}