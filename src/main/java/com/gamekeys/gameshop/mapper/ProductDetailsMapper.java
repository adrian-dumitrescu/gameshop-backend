package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.model.ProductDetailsDto;
import com.gamekeys.gameshop.dto.basic.ProductKeyBasicDto;
import com.gamekeys.gameshop.model.ProductKey;
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
        result.setPublisher(entity.getPublisher());
        if(entity.getProductKeys() != null) {
            result.setProductKeys(entity.getProductKeys().stream().map(activationKey -> productKeyToBasicDto(activationKey)).collect(Collectors.toSet()));
        }
        return result;
    }

    @Override
    public ProductDetails convertToEntity(ProductDetailsDto dto) {
        ProductDetails result = new ProductDetails();
        result.setId(dto.getId());
        result.setTitle(dto.getTitle());
        result.setPublisher(dto.getPublisher());
        if(dto.getProductKeys() != null) {
            result.setProductKeys(productDetailsRepository.getReferenceById(dto.getId()).getProductKeys());
        }
        return result;
    }


    private ProductKeyBasicDto productKeyToBasicDto(ProductKey entity) {
        ProductKeyBasicDto productKeyBasicDto = new ProductKeyBasicDto();
        productKeyBasicDto.setId(entity.getId());
        productKeyBasicDto.setActivationKey(entity.getActivationKey());
        productKeyBasicDto.setPrice(entity.getPrice());
        return productKeyBasicDto;
    }


}
