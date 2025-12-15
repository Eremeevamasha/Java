package com.example.learningenglishapp;

import com.example.learningenglishapp.dictionary.BaseVocabulary;
import com.example.learningenglishapp.dictionary.AnimalVocabulary;
import com.example.learningenglishapp.dictionary.FoodVocabulary;
import com.example.learningenglishapp.dictionary.BasicPhrasesVocabulary;
import com.example.learningenglishapp.dictionary.ClothingVocabulary;
import com.example.learningenglishapp.dictionary.NatureVocabulary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VocabularyManager {
    private static final Random random = new Random();
    private static final Map<String, BaseVocabulary> vocabularies = new HashMap<>();

    static {
        // Регистрируем все словари
        registerVocabulary("animals", AnimalVocabulary.getInstance());
        registerVocabulary("food", FoodVocabulary.getInstance());
        registerVocabulary("phrases", BasicPhrasesVocabulary.getInstance());
        registerVocabulary("clothing", ClothingVocabulary.getInstance());
        registerVocabulary("nature", NatureVocabulary.getInstance());
    }

    public static void registerVocabulary(String topic, BaseVocabulary vocabulary) {
        vocabularies.put(topic, vocabulary);
    }

    public static BaseVocabulary getVocabulary(String topic) {
        BaseVocabulary vocab = vocabularies.get(topic);
        return vocab != null ? vocab : vocabularies.get("animals");
    }

    public static List<String> getRandomEnglishWords(String topic, int count) {
        BaseVocabulary vocab = getVocabulary(topic);
        String[] allWords = vocab.getAllEnglishWords();

        if (allWords.length <= count) {
            List<String> result = new ArrayList<>();
            Collections.addAll(result, allWords);
            return result;
        }

        List<String> result = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < allWords.length; i++) {
            indices.add(i);
        }

        Collections.shuffle(indices);

        for (int i = 0; i < count && i < indices.size(); i++) {
            result.add(allWords[indices.get(i)]);
        }

        return result;
    }

    public static Map<String, String> getRandomWordPairs(String topic, int count) {
        BaseVocabulary vocab = getVocabulary(topic);
        List<String> englishWords = getRandomEnglishWords(topic, count);
        Map<String, String> pairs = new HashMap<>();

        for (String englishWord : englishWords) {
            pairs.put(englishWord, vocab.getRussianTranslation(englishWord));
        }

        return pairs;
    }

    public static List<String> getAvailableTopics() {
        return new ArrayList<>(vocabularies.keySet());
    }

    public static String getTopicDisplayName(String topic) {
        switch (topic) {
            case "animals": return "Животные";
            case "food": return "Еда";
            case "phrases": return "Базовые фразы";
            case "clothing": return "Одежда";
            case "nature": return "Природа";
            default: return "Животные";
        }
    }
}