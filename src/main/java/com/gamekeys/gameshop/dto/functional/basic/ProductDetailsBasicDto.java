package com.gamekeys.gameshop.dto.functional.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsBasicDto {

    private Long id;

    private String title;

    private String summary;

    private String contentRating;

    private LocalDate initialRelease;

    private String genres;

    private String platforms;

    private String publisher;

    // No reference to Set<Product> products;
}
