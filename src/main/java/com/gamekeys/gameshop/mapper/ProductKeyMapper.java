package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.functional.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.dto.model.ProductKeyDto;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.model.ProductKey;
import com.gamekeys.gameshop.repository.ProductKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductKeyMapper implements Mapper<ProductKey, ProductKeyDto> {

    private ProductKeyRepository productKeyRepository;


    @Override
    public ProductKeyDto convertToDto(ProductKey entity) {
        ProductKeyDto result = new ProductKeyDto();
        result.setId(entity.getId());
        result.setActivationKey(entity.getActivationKey());
        if(entity.getProduct() != null) {
            result.setProduct(productToBasicDto(entity.getProduct()));
        }
        return result;
    }

    @Override
    public ProductKey convertToEntity(ProductKeyDto dto) {
        ProductKey result = new ProductKey();
        result.setId(dto.getId());
        result.setActivationKey(dto.getActivationKey());
        if (dto.getProduct() != null) {
            result.setProduct(productKeyRepository.getReferenceById(dto.getId()).getProduct());
        }
        return result;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto productBasicDto = new ProductBasicDto();
        productBasicDto.setId(entity.getId());
        productBasicDto.setPricePerKey(entity.getPricePerKey());
        productBasicDto.setDiscountPercent(entity.getDiscountPercent());
        productBasicDto.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
        return productBasicDto;
    }

    private ProductDetailsBasicDto productDetailsToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setPublisher(entity.getPublisher());
        return result;
    }

}

//        if (entity.getProducer() != null) {
//        productDto.setProducerDto(producerMapper.convertToDto(entity.getProducer()));
//        }
//        if (entity.getCategory() != null) {
//        productDto.setCategoryDto(categoryMapper.convertToDto(entity.getCategory()));
//        }


//        if (dto.getProducerDto() != null) {
//        product.setProducer(producerRepository.getReferenceById(dto.getProducerDto().getId()));
//        }
//        if (dto.getProductType() != null) {
//        product.setCategory(categoryRepository.getReferenceById(dto.getCategoryDto().getId()));
//
//        }
