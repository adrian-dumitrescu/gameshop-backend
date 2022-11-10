package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.dto.model.ProductDto;
import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductKeyBasicDto;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.model.ProductKey;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import com.gamekeys.gameshop.repository.ProductKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductMapper implements Mapper<Product, ProductDto> {

    private AppUserRepository appUserRepository;
    private ProductKeyRepository productKeyRepository;
    private ProductRepository productRepository;

    @Override
    public ProductDto convertToDto(Product entity) {
        ProductDto result = new ProductDto();
        result.setId(entity.getId());
        result.setPricePerKey(entity.getPricePerKey());
        result.setDiscountPercent(entity.getDiscountPercent());
        if(entity.getUser() != null) {
            result.setUser(appUserToBasicDto(entity.getUser()));
        }
        if(entity.getProductKeys() != null) {
            result.setProductKeys(entity.getProductKeys().stream().map(productKey -> productKeyToBasicDto(productKey)).collect(Collectors.toSet()));
        }
        if(entity.getProductDetails() != null){
            result.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
        }
        return result;
    }

    @Override
    public Product convertToEntity(ProductDto dto) {
        Product result = new Product();
        result.setId(dto.getId());
        result.setPricePerKey(dto.getPricePerKey());
        result.setDiscountPercent(dto.getDiscountPercent());
        if(dto.getUser() != null) {
            result.setUser(productRepository.getReferenceById(dto.getId()).getUser());
        }
        if(dto.getProductKeys() != null){
            result.setProductKeys(productRepository.getReferenceById(dto.getId()).getProductKeys());
        }
        if(dto.getProductDetails() != null){
            result.setProductDetails(productRepository.getReferenceById(dto.getId()).getProductDetails());
        }
        return result;
    }

    private AppUserBasicDto appUserToBasicDto(AppUser entity) {
        AppUserBasicDto appUserBasicDto = new AppUserBasicDto();
        appUserBasicDto.setId(entity.getId());
        appUserBasicDto.setFirstName(entity.getFirstName());
        appUserBasicDto.setLastName(entity.getLastName());
        appUserBasicDto.setEmail(entity.getEmail());
        appUserBasicDto.setPassword(entity.getPassword());
        appUserBasicDto.setRoles(entity.getRoles());
        appUserBasicDto.setProfileImageUrl(entity.getProfileImageUrl());
        appUserBasicDto.setJoinDate(entity.getJoinDate());
        appUserBasicDto.setIsNotLocked(entity.getIsNotLocked());
        appUserBasicDto.setIsEnabled(entity.getIsEnabled());
        appUserBasicDto.setNickname(entity.getNickname());
        appUserBasicDto.setCountry(entity.getCountry());
        appUserBasicDto.setGender(entity.getGender());
        appUserBasicDto.setAge(entity.getAge());
        return appUserBasicDto;
    }

    private ProductKeyBasicDto productKeyToBasicDto(ProductKey entity) {
        ProductKeyBasicDto productKeyBasicDto = new ProductKeyBasicDto();
        productKeyBasicDto.setId(entity.getId());
        productKeyBasicDto.setActivationKey(entity.getActivationKey());
        return productKeyBasicDto;
    }


    private ProductDetailsBasicDto productDetailsToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setPublisher(entity.getPublisher());
        return result;
    }


}
