package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductKeyDto {

    private Long id;

    private String activationKey;

    private ProductDetailsBasicDto productDetails;

    private ProductBasicDto product;

}
