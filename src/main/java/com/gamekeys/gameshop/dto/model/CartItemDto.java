package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.functional.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.ShoppingCartBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long id;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private ShoppingCartBasicDto shoppingCart;

    private ProductDetailsBasicDto product;

}
