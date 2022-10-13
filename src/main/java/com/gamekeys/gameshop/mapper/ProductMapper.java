package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.ProductDto;
import com.gamekeys.gameshop.dto.basic.ActivationKeyBasicDto;
import com.gamekeys.gameshop.entity.ActivationKey;
import com.gamekeys.gameshop.entity.Product;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductMapper implements Mapper<Product, ProductDto>{

    private  ProductRepository productRepository;

    @Override
    public ProductDto convertToDto(Product entity) {
        ProductDto result = new ProductDto();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setPublisher(entity.getPublisher());
        result.setActivationKey(entity.getActivationKeys().stream().map(activationKey -> activationKeyToBasicDto(activationKey)).collect(Collectors.toSet()));
        return result;
    }

    @Override
    public Product convertToEntity(ProductDto dto) {
        Product result = new Product();
        result.setId(dto.getId());
        result.setName(dto.getName());
        result.setPublisher(dto.getPublisher());
        if(dto.getActivationKey() != null) {
            result.setActivationKeys(productRepository.getReferenceById(dto.getId()).getActivationKeys());
        }
        return result;
    }


    private ActivationKeyBasicDto activationKeyToBasicDto(ActivationKey entity) {
        ActivationKeyBasicDto result = new ActivationKeyBasicDto();
        result.setId(entity.getId());
        result.setKey(entity.getKeyValue());
        return result;
    }


}
