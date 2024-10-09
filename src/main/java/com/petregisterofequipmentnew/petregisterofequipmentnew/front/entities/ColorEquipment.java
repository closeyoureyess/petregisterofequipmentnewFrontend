package com.petregisterofequipmentnew.petregisterofequipmentnew.front.entities;

public enum ColorEquipment {

    BLACK("Черный"),
    WHITE("Белый"),
    RED("Красный"),
    GREEN("Зеленый"),
    BLUE("Синий"),
    YELLOW("Желтый"),
    CYAN("Голубой"),
    MAGENTA("Пурпурный"),
    GRAY("Серый"),
    DARK_GRAY("Темно-серый"),
    LIGHT_GRAY("Светло-серый"),
    SILVER("Серебристый"),
    MAROON("Бордовый"),
    OLIVE("Оливковый"),
    PURPLE("Фиолетовый"),
    TEAL("Бирюзовый"),
    NAVY("Темно-синий"),
    AQUA("Аква"),
    FUCHSIA("Фуксия"),
    LIME("Лайм"),
    INDIGO("Индиго"),
    ORANGE("Оранжевый"),
    PINK("Розовый"),
    BROWN("Коричневый"),
    BEIGE("Бежевый"),
    TAN("Загарный"),
    IVORY("Слоновая кость"),
    CORAL("Коралловый"),
    SALMON("Лососевый"),
    KHAKI("Хаки"),
    PEACH("Персиковый"),
    VIOLET("Лиловый"),
    TURQUOISE("Бирюзовый"),
    MINT("Мятный"),
    CHARTREUSE("Шартрез"),
    SLATE_BLUE("Серо-голубой"),
    HOT_PINK("Ярко-розовый"),
    SKY_BLUE("Небесно-голубой"),
    MEDIUM_SEA_GREEN("Средне-морской зеленый"),
    ORCHID("Орхидейный"),
    LIGHT_CYAN("Светло-голубой"),
    LIGHT_GREEN("Светло-зеленый"),
    LIGHT_YELLOW("Светло-желтый"),
    LIGHT_PINK("Светло-розовый"),
    LIGHT_SALMON("Светло-лососевый"),
    LIGHT_BLUE("Светло-синий"),
    LIGHT_GOLDENROD_YELLOW("Светло-золотисто-желтый"),
    LIGHT_CORAL("Светло-коралловый"),
    LIGHT_TURQUOISE("Светло-бирюзовый"),
    LIGHT_SLATE_GRAY("Светло-серо-голубой");

    private final String colorName;

    ColorEquipment(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }
}
