package com.example.learningenglishapp.dictionary;

public class ClothingVocabulary extends BaseVocabulary {
    private static final ClothingVocabulary instance = new ClothingVocabulary();

    public static ClothingVocabulary getInstance() {
        return instance;
    }

    private ClothingVocabulary() {
        // Конструктор базового класса вызовет initializeVocabulary()
    }

    @Override
    protected void initializeVocabulary() {
        // Верхняя одежда
        addWord("jacket", "куртка");
        addWord("coat", "пальто");
        addWord("raincoat", "плащ");
        addWord("fur coat", "шуба");
        addWord("parka", "парка");
        addWord("blazer", "блейзер");

        // Верх
        addWord("t-shirt", "футболка");
        addWord("shirt", "рубашка");
        addWord("blouse", "блузка");
        addWord("sweater", "свитер");
        addWord("hoodie", "толстовка");
        addWord("dress", "платье");
        addWord("suit", "костюм");
        addWord("vest", "жилет");

        // Низ
        addWord("pants", "брюки");
        addWord("jeans", "джинсы");
        addWord("trousers", "брюки");
        addWord("shorts", "шорты");
        addWord("skirt", "юбка");
        addWord("leggings", "леггинсы");
        addWord("overalls", "комбинезон");

        // Нижнее белье
        addWord("underwear", "нижнее белье");
        addWord("bra", "бюстгальтер");
        addWord("panties", "трусики");
        addWord("boxers", "боксеры");
        addWord("socks", "носки");
        addWord("stockings", "чулки");
        addWord("tights", "колготки");

        // Обувь
        addWord("shoes", "туфли");
        addWord("boots", "ботинки");
        addWord("sneakers", "кроссовки");
        addWord("sandals", "сандали");
        addWord("slippers", "тапочки");
        addWord("high heels", "туфли на каблуке");
        addWord("flip flops", "шлепанцы");

        // Головные уборы
        addWord("hat", "шляпа");
        addWord("cap", "кепка");
        addWord("beanie", "шапка");
        addWord("scarf", "шарф");
        addWord("helmet", "шлем");
        addWord("baseball cap", "бейсболка");

        // Аксессуары
        addWord("gloves", "перчатки");
        addWord("belt", "ремень");
        addWord("tie", "галстук");
        addWord("bow tie", "бабочка");
        addWord("bag", "сумка");
        addWord("backpack", "рюкзак");
        addWord("wallet", "кошелек");
        addWord("umbrella", "зонт");

        // Специальная одежда
        addWord("uniform", "униформа");
        addWord("pajamas", "пижама");
        addWord("swimsuit", "купальник");
        addWord("bikini", "бикини");
        addWord("tracksuit", "спортивный костюм");

        // Аксессуары доп.
        addWord("glasses", "очки");
        addWord("sunglasses", "солнцезащитные очки");
        addWord("watch", "часы");
        addWord("jewelry", "украшения");
        addWord("ring", "кольцо");
        addWord("necklace", "ожерелье");
        addWord("earrings", "серьги");
        addWord("bracelet", "браслет");

        // Ткани и материалы
        addWord("cotton", "хлопок");
        addWord("wool", "шерсть");
        addWord("silk", "шелк");
        addWord("leather", "кожа");
        addWord("denim", "джинсовая ткань");
        addWord("linen", "лен");
        addWord("polyester", "полиэстер");

        // Размеры
        addWord("size", "размер");
        addWord("small", "маленький");
        addWord("medium", "средний");
        addWord("large", "большой");
        addWord("extra large", "очень большой");
    }
}