package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.model.ProductKeyDto;
import com.gamekeys.gameshop.dto.basic.InventoryBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.model.Inventory;
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
        result.setPrice(entity.getPrice());
        if(entity.getInventory() != null) {
            result.setInventory(inventoryToBasicDto(entity.getInventory()));
        }
        if(entity.getProductDetails() != null) {
            result.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
        }
        return result;
    }

    @Override
    public ProductKey convertToEntity(ProductKeyDto dto) {
        ProductKey result = new ProductKey();
        result.setId(dto.getId());
        result.setActivationKey(dto.getActivationKey());
        result.setPrice(dto.getPrice());
        if (dto.getInventory() != null) {
            result.setInventory(productKeyRepository.getReferenceById(dto.getId()).getInventory());
        }
        if (dto.getProductDetails() != null) {
            result.setProductDetails(productKeyRepository.getReferenceById(dto.getId()).getProductDetails());
        }
        return result;
    }

    private ProductDetailsBasicDto productDetailsToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setPublisher(entity.getPublisher());
        return result;
    }

    private InventoryBasicDto inventoryToBasicDto(Inventory entity) {
        InventoryBasicDto result = new InventoryBasicDto();
        result.setId(entity.getId());
        result.setTotalSold(entity.getTotalSold());
        result.setListed(entity.getListed());
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
