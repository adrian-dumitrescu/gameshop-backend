package com.gamekeys.gameshop.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasicDto {

    private Long id;

    private BigDecimal pricePerKey;

    // No reference to AppUser user

    // No reference to  Set<ProductKey> productKeys

    // No reference to ProductDetails productDetails;

    // No reference to CartItem cartItem;

    // No reference to OrderItem orderItem;

}
