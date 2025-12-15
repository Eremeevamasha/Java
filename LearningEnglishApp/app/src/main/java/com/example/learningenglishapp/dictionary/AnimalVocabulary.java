package com.example.learningenglishapp.dictionary;

public class AnimalVocabulary extends BaseVocabulary {
    private static final AnimalVocabulary instance = new AnimalVocabulary();

    public static AnimalVocabulary getInstance() {
        return instance;
    }

    private AnimalVocabulary() {
        // Конструктор базового класса вызовет initializeVocabulary()
    }

    @Override
    protected void initializeVocabulary() {
        addWord("cat", "кот");
        addWord("dog", "собака");
        addWord("elephant", "слон");
        addWord("lion", "лев");
        addWord("tiger", "тигр");
        addWord("bear", "медведь");
        addWord("wolf", "волк");
        addWord("fox", "лиса");
        addWord("rabbit", "кролик");
        addWord("horse", "лошадь");
        addWord("cow", "корова");
        addWord("pig", "свинья");
        addWord("sheep", "овца");
        addWord("goat", "коза");
        addWord("chicken", "курица");
        addWord("duck", "утка");
        addWord("bird", "птица");
        addWord("fish", "рыба");
        addWord("frog", "лягушка");
        addWord("mouse", "мышь");
        addWord("snake", "змея");
        addWord("monkey", "обезьяна");
        addWord("giraffe", "жираф");
        addWord("zebra", "зебра");
        addWord("kangaroo", "кенгуру");
        addWord("penguin", "пингвин");
        addWord("dolphin", "дельфин");
        addWord("whale", "кит");
        addWord("shark", "акула");
        addWord("butterfly", "бабочка");
        addWord("bee", "пчела");
        addWord("spider", "паук");
        addWord("eagle", "орел");
        addWord("owl", "сова");
        addWord("parrot", "попугай");
        addWord("crocodile", "крокодил");
        addWord("turtle", "черепаха");
        addWord("hamster", "хомяк");
        addWord("squirrel", "белка");
        addWord("deer", "олень");
        addWord("rhinoceros", "носорог");
        addWord("hippopotamus", "бегемот");
        addWord("panda", "панда");
        addWord("koala", "коала");
        addWord("bat", "летучая мышь");
        addWord("ant", "муравей");
        addWord("grasshopper", "кузнечик");
        addWord("ladybug", "божья коровка");
        addWord("crab", "краб");
        addWord("octopus", "осьминог");
        addWord("jellyfish", "медуза");
        addWord("starfish", "морская звезда");
    }
}