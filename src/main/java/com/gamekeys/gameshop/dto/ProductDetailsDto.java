package com.gamekeys.gameshop.dto;

import com.gamekeys.gameshop.dto.basic.ProductKeyBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDto {

    private Long id;

    private String productName;

    private String publisher;

    //private Set<ActivationKeyDto> activationKeyDto;
    private Set<ProductKeyBasicDto> activationKey;

}
