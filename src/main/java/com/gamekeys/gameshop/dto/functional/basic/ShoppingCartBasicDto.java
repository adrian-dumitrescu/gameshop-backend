package com.gamekeys.gameshop.dto.functional.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartBasicDto {

    private Long id;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Set<CartItemBasicDto> cartItems;

    // No reference to AppUserBasicDto user;

    // No reference to Set<CartItemBasicDto> cartItems;

}
