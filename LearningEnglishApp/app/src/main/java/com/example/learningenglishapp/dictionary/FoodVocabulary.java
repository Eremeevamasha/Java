package com.example.learningenglishapp.dictionary;

public class FoodVocabulary extends BaseVocabulary {
    private static final FoodVocabulary instance = new FoodVocabulary();

    public static FoodVocabulary getInstance() {
        return instance;
    }

    private FoodVocabulary() {
        // Конструктор базового класса вызовет initializeVocabulary()
    }

    @Override
    protected void initializeVocabulary() {
        // Фрукты и овощи
        addWord("apple", "яблоко");
        addWord("banana", "банан");
        addWord("orange", "апельсин");
        addWord("grape", "виноград");
        addWord("strawberry", "клубника");
        addWord("watermelon", "арбуз");
        addWord("tomato", "помидор");
        addWord("potato", "картофель");
        addWord("carrot", "морковь");
        addWord("cucumber", "огурец");
        addWord("onion", "лук");
        addWord("garlic", "чеснок");

        // Мясо и рыба
        addWord("meat", "мясо");
        addWord("beef", "говядина");
        addWord("chicken", "курица");
        addWord("pork", "свинина");
        addWord("fish", "рыба");
        addWord("salmon", "лосось");
        addWord("shrimp", "креветка");

        // Молочные продукты
        addWord("milk", "молоко");
        addWord("cheese", "сыр");
        addWord("butter", "масло");
        addWord("yogurt", "йогурт");
        addWord("egg", "яйцо");
        addWord("cream", "сливки");

        // Напитки
        addWord("water", "вода");
        addWord("tea", "чай");
        addWord("coffee", "кофе");
        addWord("juice", "сок");
        addWord("wine", "вино");
        addWord("beer", "пиво");

        // Основные продукты
        addWord("bread", "хлеб");
        addWord("rice", "рис");
        addWord("pasta", "паста");
        addWord("soup", "суп");
        addWord("salad", "салат");
        addWord("sandwich", "бутерброд");

        // Сладости
        addWord("cake", "торт");
        addWord("chocolate", "шоколад");
        addWord("cookie", "печенье");
        addWord("ice cream", "мороженое");
        addWord("candy", "конфета");

        // Приправы
        addWord("sugar", "сахар");
        addWord("salt", "соль");
        addWord("pepper", "перец");
        addWord("oil", "масло");
        addWord("vinegar", "уксус");
        addWord("honey", "мед");
    }
}