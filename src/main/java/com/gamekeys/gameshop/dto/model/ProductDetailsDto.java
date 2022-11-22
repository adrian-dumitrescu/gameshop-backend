package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDto {

    private Long id;

    private String title;

    private String summary;

    private String contentRating;

    private LocalDate initialRelease;

    private String genres;

    private String platforms;

    private String publisher;

    private Set<ProductBasicDto> products;

}
