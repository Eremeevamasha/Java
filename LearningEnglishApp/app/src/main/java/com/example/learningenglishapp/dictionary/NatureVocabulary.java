package com.example.learningenglishapp.dictionary;

public class NatureVocabulary extends BaseVocabulary {
    private static final NatureVocabulary instance = new NatureVocabulary();

    public static NatureVocabulary getInstance() {
        return instance;
    }

    private NatureVocabulary() {
        // Конструктор базового класса вызовет initializeVocabulary()
    }

    @Override
    protected void initializeVocabulary() {
        // Погода
        addWord("weather", "погода");
        addWord("sun", "солнце");
        addWord("moon", "луна");
        addWord("star", "звезда");
        addWord("cloud", "облако");
        addWord("rain", "дождь");
        addWord("snow", "снег");
        addWord("wind", "ветер");
        addWord("storm", "шторм");
        addWord("fog", "туман");
        addWord("rainbow", "радуга");

        // Ландшафт
        addWord("mountain", "гора");
        addWord("hill", "холм");
        addWord("valley", "долина");
        addWord("forest", "лес");
        addWord("field", "поле");
        addWord("meadow", "луг");
        addWord("river", "река");
        addWord("lake", "озеро");
        addWord("sea", "море");
        addWord("ocean", "океан");
        addWord("waterfall", "водопад");
        addWord("beach", "пляж");
        addWord("island", "остров");
        addWord("desert", "пустыня");
        addWord("cave", "пещера");
        addWord("cliff", "скала");

        // Растения
        addWord("tree", "дерево");
        addWord("flower", "цветок");
        addWord("grass", "трава");
        addWord("bush", "куст");
        addWord("leaf", "лист");
        addWord("root", "корень");
        addWord("branch", "ветка");
        addWord("fruit", "фрукт");
        addWord("vegetable", "овощ");
        addWord("seed", "семя");
        addWord("pinecone", "шишка");

        // Деревья
        addWord("oak", "дуб");
        addWord("pine", "сосна");
        addWord("birch", "береза");
        addWord("maple", "клен");
        addWord("willow", "ива");
        addWord("spruce", "ель");
        addWord("fir", "пихта");

        // Цветы
        addWord("rose", "роза");
        addWord("tulip", "тюльпан");
        addWord("daisy", "ромашка");
        addWord("sunflower", "подсолнух");
        addWord("lily", "лилия");
        addWord("orchid", "орхидея");
        addWord("violet", "фиалка");

        // Природные явления
        addWord("earthquake", "землетрясение");
        addWord("volcano", "вулкан");
        addWord("tsunami", "цунами");
        addWord("hurricane", "ураган");
        addWord("tornado", "торнадо");
        addWord("flood", "наводнение");
        addWord("avalanche", "лавина");

        // Времена года
        addWord("spring", "весна");
        addWord("summer", "лето");
        addWord("autumn", "осень");
        addWord("fall", "осень");
        addWord("winter", "зима");
        addWord("season", "время года");

        // Небо и космос
        addWord("sky", "небо");
        addWord("lightning", "молния");
        addWord("thunder", "гром");
        addWord("horizon", "горизонт");
        addWord("sunrise", "восход");
        addWord("sunset", "закат");
        addWord("planet", "планета");
        addWord("galaxy", "галактика");

        // Почва и камни
        addWord("earth", "земля");
        addWord("soil", "почва");
        addWord("sand", "песок");
        addWord("stone", "камень");
        addWord("rock", "скала");
        addWord("clay", "глина");
        addWord("mud", "грязь");
        addWord("gravel", "гравий");

        // Вода
        addWord("water", "вода");
        addWord("ice", "лед");
        addWord("steam", "пар");
        addWord("stream", "ручей");
        addWord("pond", "пруд");
        addWord("swamp", "болото");
        addWord("wave", "волна");

        // Экология
        addWord("nature", "природа");
        addWord("environment", "окружающая среда");
        addWord("ecology", "экология");
        addWord("pollution", "загрязнение");
        addWord("protection", "защита");
        addWord("conservation", "сохранение");
        addWord("recycling", "переработка");

        // Природные ресурсы
        addWord("air", "воздух");
        addWord("fire", "огонь");
        addWord("wood", "дерево");
        addWord("coal", "уголь");
        addWord("oil", "нефть");
        addWord("gas", "газ");
        addWord("mineral", "минерал");

        // Погодные условия
        addWord("temperature", "температура");
        addWord("hot", "жарко");
        addWord("cold", "холодно");
        addWord("warm", "тепло");
        addWord("cool", "прохладно");
        addWord("humid", "влажно");
        addWord("dry", "сухо");
        addWord("windy", "ветрено");
        addWord("sunny", "солнечно");
        addWord("cloudy", "облачно");
    }
}