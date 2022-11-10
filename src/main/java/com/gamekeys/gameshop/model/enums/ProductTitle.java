package com.gamekeys.gameshop.model.enums;

import lombok.Getter;

@Getter
public enum ProductTitle {
    OVERWATCH_2("Overwatch 2"),
    DIABLO_4("Diablo 4"),
    DARK_SOULS_3("Dark Souls 3"),
    DYING_LIGHT_2("Dying Light 2"),
    DESTINY_2("Destiny 2"),
    CYBERPUNK_2077("Cyberpunk 2077"),
    LITTLE_NIGHTMARES("Little Nightmares"),
    MORTAL_KOMBAT_11("Mortal Kombat 11"),
    NFS_HEAT("NFS Heat"),
    NO_MANS_SKY("No Mans Sky"),
    THE_WITCHER_3("The Witcher 3"),
    VALORANT("Valorant")
    ;
    private final String title;

    ProductTitle(String title) {
        this.title = title;
    }
}
