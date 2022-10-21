package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.CartItemBasicDto;
import com.gamekeys.gameshop.dto.basic.InventoryBasicDto;
import com.gamekeys.gameshop.dto.basic.OrderItemBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductKeyDto {

    private Long id;

    private String activationKey;

    private BigDecimal price;

    private ProductDetailsBasicDto productDetails;

    private InventoryBasicDto inventory;

    private CartItemBasicDto cartItem;

    private OrderItemBasicDto orderItem;

}
