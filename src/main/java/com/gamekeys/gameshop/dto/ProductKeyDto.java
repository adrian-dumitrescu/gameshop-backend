package com.gamekeys.gameshop.dto;

import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductKeyDto {

    private Long id;

    private String keyValue;

    private AppUserBasicDto appUser;

    private ProductDetailsBasicDto product;

}
