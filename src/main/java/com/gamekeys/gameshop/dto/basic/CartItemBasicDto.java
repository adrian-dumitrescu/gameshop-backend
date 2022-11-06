package com.gamekeys.gameshop.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CartItemBasicDto {

    private Long id;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private ProductBasicDto product;

    // No reference to ShoppingCartBasicDto shoppingCart;

    // No reference to ProductBasicDto product;

}
