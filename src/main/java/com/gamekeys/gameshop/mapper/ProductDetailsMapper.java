package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.functional.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.dto.model.ProductDetailsDto;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.repository.ProductDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductDetailsMapper implements Mapper<ProductDetails, ProductDetailsDto>{

    private ProductDetailsRepository productDetailsRepository;

    @Override
    public ProductDetailsDto convertToDto(ProductDetails entity) {
        ProductDetailsDto result = new ProductDetailsDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setSummary(entity.getSummary());
        result.setContentRating(entity.getContentRating());
        result.setInitialRelease(entity.getInitialRelease());
        result.setGenres(entity.getGenres());
        result.setPlatforms(entity.getPlatforms());
        result.setPublisher(entity.getPublisher());
        if(entity.getProducts() != null) {
            result.setProducts(entity.getProducts().stream().map(activationKey -> productToBasicDto(activationKey)).collect(Collectors.toSet()));
        }
        return result;
    }

    @Override
    public ProductDetails convertToEntity(ProductDetailsDto dto) {
        ProductDetails result = new ProductDetails();
        result.setId(dto.getId());
        result.setTitle(dto.getTitle());
        result.setSummary(dto.getSummary());
        result.setContentRating(dto.getContentRating());
        result.setInitialRelease(dto.getInitialRelease());
        result.setGenres(dto.getGenres());
        result.setPlatforms(dto.getPlatforms());
        result.setPublisher(dto.getPublisher());
        if(dto.getProducts() != null) {
            result.setProducts(productDetailsRepository.getReferenceById(dto.getId()).getProducts());
        }
        return result;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto result = new ProductBasicDto();
        result.setId(entity.getId());
        result.setPricePerKey(entity.getPricePerKey());
        result.setDiscountPercent(entity.getDiscountPercent());
        result.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
        return result;
    }

    private ProductDetailsBasicDto productDetailsToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setSummary(entity.getSummary());
        result.setContentRating(entity.getContentRating());
        result.setInitialRelease(entity.getInitialRelease());
        result.setGenres(entity.getGenres());
        result.setPlatforms(entity.getPlatforms());
        result.setPublisher(entity.getPublisher());
        return result;
    }



}
