package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.OrderItemBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {

    private Long id;

    private BigDecimal total;

    private Boolean withGuard;

    private String paymentOption;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private AppUserBasicDto user;

    private Set<OrderItemBasicDto> orderItems;

}
