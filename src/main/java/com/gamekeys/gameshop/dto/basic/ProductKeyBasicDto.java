package com.gamekeys.gameshop.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ProductKeyBasicDto {

    private Long id;

    private String key;

    // No reference to AppUser user;

    // No reference to Product product;


}

