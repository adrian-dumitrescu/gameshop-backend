package com.gamekeys.gameshop.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBasicDto {

    private Long id;

    private Integer totalSold;

    private Integer listed;

    // No reference to AppUser user

    // No reference to  Set<ProductKey> productKeys

}
