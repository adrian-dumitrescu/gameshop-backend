package com.gamekeys.gameshop.model.enums;

import lombok.Getter;

@Getter
public enum ProductPublisher {
    BLIZZARD_ENTERTAINMENT("Blizzard Entertainment"),
    FROM_SOFTWARE("From Software"),
    TECHLAND("Techland"),
    ACTIVISION("Activision"),
    RIOT_GAMES("Riot Games"),
    UBISOFT("Ubisoft"),
    CD_PROJEKT("CD Projekt"),
    BANDAI_NAMCO_ENTERTAINMENT("Bandai Namco Entertainment"),
    NETHERREALM_STUDIOS("NetherRealm Studios"),
    ELECTRONIC_ARTS("Electronic Arts"),
    HELLO_GAMES("Hello Games")
    ;

    private final String publisher;

    ProductPublisher(String publisher) {
        this.publisher = publisher;
    }


}
