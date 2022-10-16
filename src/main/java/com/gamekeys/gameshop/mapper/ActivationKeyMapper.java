package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.ProductKeyDto;
import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.entity.ProductKey;
import com.gamekeys.gameshop.entity.AppUser;
import com.gamekeys.gameshop.entity.ProductDetails;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivationKeyMapper implements Mapper<ProductKey, ProductKeyDto> {

    private  AppUserRepository appUserRepository;
    private  ProductRepository productRepository;


    @Override
    public ProductKeyDto convertToDto(ProductKey entity) {
        ProductKeyDto result = new ProductKeyDto();
        result.setId(entity.getId());
        result.setKeyValue(entity.getKeyValue());
        //result.setAppUser(appUserToBasicDto(entity.getUser()));
        result.setProduct(productToBasicDto(entity.getProductDetails()));
        return result;
    }

    @Override
    public ProductKey convertToEntity(ProductKeyDto dto) {
        ProductKey result = new ProductKey();
        result.setId(dto.getId());
        result.setKeyValue(dto.getKeyValue());
        if(dto.getAppUser() != null) {
        //    result.setUser(appUserRepository.getReferenceById(dto.getAppUser().getId()));
        }
        if(dto.getProduct() != null) {
            result.setProductDetails(productRepository.getReferenceById(dto.getAppUser().getId()));
        }
        return result;
    }

    private ProductDetailsBasicDto productToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setProductName(entity.getProductName());
        result.setPublisher(entity.getPublisher());
        return result;
    }

    private AppUserBasicDto appUserToBasicDto(AppUser entity) {
        AppUserBasicDto appUserDto = new AppUserBasicDto();
        appUserDto.setId(entity.getId());
        appUserDto.setFirstName(entity.getFirstName());
        appUserDto.setLastName(entity.getLastName());
        appUserDto.setEmail(entity.getEmail());
        appUserDto.setPassword(entity.getPassword());
        appUserDto.setRoles(entity.getRoles());
        appUserDto.setProfileImageUrl(entity.getProfileImageUrl());
        appUserDto.setJoinDate(entity.getJoinDate());
        appUserDto.setIsNotLocked(entity.getIsNotLocked());
        appUserDto.setIsEnabled(entity.getIsEnabled());
        return appUserDto;
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
