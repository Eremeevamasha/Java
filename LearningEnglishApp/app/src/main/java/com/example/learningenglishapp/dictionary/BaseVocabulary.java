package com.example.learningenglishapp.dictionary;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseVocabulary implements IVocabulary {
    protected final Map<String, String> vocabulary = new HashMap<>();
    protected final Map<String, String> reverseVocabulary = new HashMap<>();

    protected BaseVocabulary() {
        initializeVocabulary();
    }

    protected abstract void initializeVocabulary();

    protected void addWord(String english, String russian) {
        vocabulary.put(english.toLowerCase(), russian.toLowerCase());
        reverseVocabulary.put(russian.toLowerCase(), english.toLowerCase());
    }

    @Override
    public boolean checkEnglishToRussian(String englishWord, String userAnswer) {
        if (englishWord == null || userAnswer == null) return false;
        String correct = vocabulary.get(englishWord.toLowerCase());
        return correct != null && correct.equals(userAnswer.toLowerCase());
    }

    @Override
    public boolean checkRussianToEnglish(String russianWord, String userAnswer) {
        if (russianWord == null || userAnswer == null) return false;
        String correct = reverseVocabulary.get(russianWord.toLowerCase());
        return correct != null && correct.equals(userAnswer.toLowerCase());
    }

    @Override
    public String getRussianTranslation(String englishWord) {
        return vocabulary.get(englishWord.toLowerCase());
    }

    @Override
    public String getEnglishTranslation(String russianWord) {
        return reverseVocabulary.get(russianWord.toLowerCase());
    }

    @Override
    public boolean wordExists(String word) {
        if (word == null) return false;
        String lowerWord = word.toLowerCase();
        return vocabulary.containsKey(lowerWord) || reverseVocabulary.containsKey(lowerWord);
    }

    @Override
    public int getVocabularySize() {
        return vocabulary.size();
    }

    @Override
    public String[] getAllEnglishWords() {
        return vocabulary.keySet().toArray(new String[0]);
    }

    @Override
    public String[] getAllRussianWords() {
        return reverseVocabulary.keySet().toArray(new String[0]);
    }
}