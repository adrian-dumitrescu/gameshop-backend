package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.OrderDetailsBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Long id;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private OrderDetailsBasicDto orderDetails;

    private ProductDetailsBasicDto product;

}
