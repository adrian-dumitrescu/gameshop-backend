package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.ProductDetailsDto;
import com.gamekeys.gameshop.dto.basic.ProductKeyBasicDto;
import com.gamekeys.gameshop.entity.ProductKey;
import com.gamekeys.gameshop.entity.ProductDetails;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductMapper implements Mapper<ProductDetails, ProductDetailsDto>{

    private  ProductRepository productRepository;

    @Override
    public ProductDetailsDto convertToDto(ProductDetails entity) {
        ProductDetailsDto result = new ProductDetailsDto();
        result.setId(entity.getId());
        result.setProductName(entity.getProductName());
        result.setPublisher(entity.getPublisher());
        result.setActivationKey(entity.getProductKeys().stream().map(activationKey -> activationKeyToBasicDto(activationKey)).collect(Collectors.toSet()));
        return result;
    }

    @Override
    public ProductDetails convertToEntity(ProductDetailsDto dto) {
        ProductDetails result = new ProductDetails();
        result.setId(dto.getId());
        result.setProductName(dto.getProductName());
        result.setPublisher(dto.getPublisher());
        if(dto.getActivationKey() != null) {
            result.setProductKeys(productRepository.getReferenceById(dto.getId()).getProductKeys());
        }
        return result;
    }


    private ProductKeyBasicDto activationKeyToBasicDto(ProductKey entity) {
        ProductKeyBasicDto result = new ProductKeyBasicDto();
        result.setId(entity.getId());
        result.setKey(entity.getKeyValue());
        return result;
    }


}
