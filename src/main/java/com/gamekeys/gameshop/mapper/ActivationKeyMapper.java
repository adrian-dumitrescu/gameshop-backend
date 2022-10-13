package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.ActivationKeyDto;
import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import com.gamekeys.gameshop.entity.ActivationKey;
import com.gamekeys.gameshop.entity.AppUser;
import com.gamekeys.gameshop.entity.Product;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivationKeyMapper implements Mapper<ActivationKey, ActivationKeyDto> {

    private  AppUserRepository appUserRepository;
    private  ProductRepository productRepository;


    @Override
    public ActivationKeyDto convertToDto(ActivationKey entity) {
        ActivationKeyDto result = new ActivationKeyDto();
        result.setId(entity.getId());
        result.setKeyValue(entity.getKeyValue());
        result.setAppUser(appUserToBasicDto(entity.getUser()));
        result.setProduct(productToBasicDto(entity.getProduct()));
        return result;
    }

    @Override
    public ActivationKey convertToEntity(ActivationKeyDto dto) {
        ActivationKey result = new ActivationKey();
        result.setId(dto.getId());
        result.setKeyValue(dto.getKeyValue());
        if(dto.getAppUser() != null) {
            result.setUser(appUserRepository.getReferenceById(dto.getAppUser().getId()));
        }
        if(dto.getProduct() != null) {
            result.setProduct(productRepository.getReferenceById(dto.getAppUser().getId()));
        }
        return result;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto result = new ProductBasicDto();
        result.setId(entity.getId());
        result.setProductName(entity.getName());
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
