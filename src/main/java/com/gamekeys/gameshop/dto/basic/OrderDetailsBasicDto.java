package com.gamekeys.gameshop.dto.basic;

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
public class OrderDetailsBasicDto {

    private Long id;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private Set<OrderItemBasicDto> orderItem;

    // No reference to AppUserBasicDto user;

    // No reference to Set<OrderItemBasicDto> orderItems;

}
