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
public class OrderItemBasicDto {

    private Long id;

    private Integer quantity;

    private String sellerEmail;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // No reference to OrderDetailsBasicDto orderDetails;

    // No reference to ProductDetailsBasicDto product;

}
