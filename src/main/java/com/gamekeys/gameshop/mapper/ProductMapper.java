package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.ProductDto;
import com.gamekeys.gameshop.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper implements Mapper<Product, ProductDto>{

    @Override
    public ProductDto convertToDto(Product entity) {
        ProductDto result = new ProductDto();
        result.setId(entity.getId());
        result.setProductName(entity.getProductName());
        result.setPublisher(entity.getPublisher());
        result.setActivationKeys(entity.getActivationKeys());
        return result;
    }

    @Override
    public Product convertToEntity(ProductDto dto) {
        Product result = new Product();
        result.setId(dto.getId());
        result.setProductName(dto.getProductName());
        result.setPublisher(dto.getPublisher());
        result.setActivationKeys(dto.getActivationKeys());
        return result;
    }

}
