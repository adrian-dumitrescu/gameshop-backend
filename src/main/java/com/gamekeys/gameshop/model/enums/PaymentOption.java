package com.gamekeys.gameshop.model.enums;

import lombok.Getter;

@Getter
public enum PaymentOption {

    BANK_TRANSFER("Bank Transfer"),
    CARD("Credit or Debit Card"),
    PAY_PAL("Pay Pal")
    ;

    private final String paymentOption;

    PaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

}
