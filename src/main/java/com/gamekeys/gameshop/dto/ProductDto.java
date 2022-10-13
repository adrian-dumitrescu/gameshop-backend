package com.gamekeys.gameshop.dto;

import com.gamekeys.gameshop.dto.basic.ActivationKeyBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private String publisher;

    //private Set<ActivationKeyDto> activationKeyDto;
    private Set<ActivationKeyBasicDto> activationKey;

}
