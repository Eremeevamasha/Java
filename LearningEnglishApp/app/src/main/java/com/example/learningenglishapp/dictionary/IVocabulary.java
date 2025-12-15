package com.example.learningenglishapp.dictionary;


public interface IVocabulary {
    boolean checkEnglishToRussian(String englishWord, String userAnswer);
    boolean checkRussianToEnglish(String russianWord, String userAnswer);
    String getRussianTranslation(String englishWord);
    String getEnglishTranslation(String russianWord);
    boolean wordExists(String word);
    int getVocabularySize();
    String[] getAllEnglishWords();
    String[] getAllRussianWords();
}