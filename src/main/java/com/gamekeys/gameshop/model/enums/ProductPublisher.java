package com.gamekeys.gameshop.model.enums;

import lombok.Getter;

@Getter
public enum ProductPublisher {
    BLIZZARD_ENTERTAINMENT("Blizzard Entertainment"),
    FROM_SOFTWARE("From Software"),
    TECHLAND("Techland"),
    ACTIVISION("Activision")
    ;

    private final String publisher;

    ProductPublisher(String publisher) {
        this.publisher = publisher;
    }


}
