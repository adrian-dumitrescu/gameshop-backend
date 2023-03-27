package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.functional.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.CartItemBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDto {

    private Long id;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private AppUserBasicDto user;

    private Set<CartItemBasicDto> cartItems;

}
