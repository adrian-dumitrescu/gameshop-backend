package com.gamekeys.gameshop.model.enums;

import lombok.Getter;

@Getter
public enum ProductTitle {
    OVERWATCH_2("Overwatch 2"),
    DIABLO_4("Diablo 4"),
    DARK_SOULS_3("Dark Souls 3"),
    DYING_LIGHT_2("Dying Light 2"),
    DESTINY_2("Destiny 2");

    private final String title;

    ProductTitle(String title) {
        this.title = title;
    }
}
