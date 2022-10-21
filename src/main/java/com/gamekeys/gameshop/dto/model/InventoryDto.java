package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductKeyBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private Long id;

    private Integer totalSold;

    private Integer listed;

    private AppUserBasicDto user;

    private Set<ProductKeyBasicDto> productKeys;
}
