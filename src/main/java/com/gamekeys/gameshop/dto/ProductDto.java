package com.gamekeys.gameshop.dto;

import com.gamekeys.gameshop.entity.ActivationKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String productName;

    private String publisher;

    private Set<ActivationKey> activationKeys = new HashSet<>();

}
