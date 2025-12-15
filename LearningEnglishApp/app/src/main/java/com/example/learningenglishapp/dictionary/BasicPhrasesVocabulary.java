package com.example.learningenglishapp.dictionary;

public class BasicPhrasesVocabulary extends BaseVocabulary {
    private static final BasicPhrasesVocabulary instance = new BasicPhrasesVocabulary();

    public static BasicPhrasesVocabulary getInstance() {
        return instance;
    }

    private BasicPhrasesVocabulary() {
        // Конструктор базового класса вызовет initializeVocabulary()
    }

    @Override
    protected void initializeVocabulary() {
        // Приветствия
        addWord("hello", "привет");
        addWord("hi", "привет");
        addWord("good morning", "доброе утро");
        addWord("good afternoon", "добрый день");
        addWord("good evening", "добрый вечер");
        addWord("good night", "спокойной ночи");

        // Прощания
        addWord("goodbye", "до свидания");
        addWord("bye", "пока");
        addWord("see you", "увидимся");
        addWord("see you later", "увидимся позже");
        addWord("take care", "береги себя");

        // Вопросы о самочувствии
        addWord("how are you", "как дела");
        addWord("how are you doing", "как поживаешь");
        addWord("what's up", "как ты");
        addWord("how is it going", "как идет");
        addWord("how have you been", "как ты был");

        // Ответы
        addWord("fine", "хорошо");
        addWord("good", "хорошо");
        addWord("very well", "очень хорошо");
        addWord("not bad", "неплохо");
        addWord("so-so", "так себе");
        addWord("bad", "плохо");
        addWord("tired", "устал");

        // Благодарность
        addWord("thank you", "спасибо");
        addWord("thanks", "спасибо");
        addWord("thank you very much", "большое спасибо");
        addWord("you're welcome", "пожалуйста");
        addWord("please", "пожалуйста");
        addWord("no problem", "нет проблем");

        // Извинения
        addWord("sorry", "извини");
        addWord("excuse me", "извините");
        addWord("pardon", "простите");
        addWord("I'm sorry", "мне жаль");
        addWord("my apologies", "мои извинения");

        // Знакомство
        addWord("what is your name", "как тебя зовут");
        addWord("my name is", "меня зовут");
        addWord("where are you from", "откуда ты");
        addWord("I am from", "я из");
        addWord("how old are you", "сколько тебе лет");
        addWord("I am ... years old", "мне ... лет");
        addWord("nice to meet you", "приятно познакомиться");

        // Основные слова
        addWord("yes", "да");
        addWord("no", "нет");
        addWord("maybe", "может быть");
        addWord("okay", "окей");
        addWord("understand", "понимать");
        addWord("I understand", "я понимаю");
        addWord("I don't understand", "я не понимаю");
        addWord("repeat please", "повторите пожалуйста");

        // Просьбы
        addWord("can you help me", "ты можешь помочь мне");
        addWord("I need help", "мне нужна помощь");
        addWord("can I", "могу я");
        addWord("could you", "не могли бы вы");
        addWord("please help", "помогите пожалуйста");

        // Время
        addWord("what time is it", "который час");
        addWord("today", "сегодня");
        addWord("tomorrow", "завтра");
        addWord("yesterday", "вчера");
        addWord("now", "сейчас");
        addWord("later", "позже");
        addWord("soon", "скоро");

        // Основные вопросы
        addWord("what", "что");
        addWord("where", "где");
        addWord("when", "когда");
        addWord("why", "почему");
        addWord("how", "как");
        addWord("who", "кто");

        // Основные глаголы
        addWord("go", "идти");
        addWord("come", "приходить");
        addWord("eat", "есть");
        addWord("drink", "пить");
        addWord("sleep", "спать");
        addWord("work", "работать");
        addWord("study", "учиться");
    }
}