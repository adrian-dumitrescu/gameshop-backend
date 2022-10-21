package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.model.InventoryDto;
import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductKeyBasicDto;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.Inventory;
import com.gamekeys.gameshop.model.ProductKey;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.InventoryRepository;
import com.gamekeys.gameshop.repository.ProductKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryMapper implements Mapper<Inventory, InventoryDto> {

    private AppUserRepository appUserRepository;
    private ProductKeyRepository productKeyRepository;
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryDto convertToDto(Inventory entity) {
        InventoryDto result = new InventoryDto();
        result.setId(entity.getId());
        result.setTotalSold(entity.getTotalSold());
        result.setListed(entity.getListed());
        if(entity.getUser() != null) {
            result.setUser(appUserToBasicDto(entity.getUser()));
        }
        if(entity.getProductKeys() != null) {
            result.setProductKeys(entity.getProductKeys().stream().map(productKey -> productKeyToBasicDto(productKey)).collect(Collectors.toSet()));
        }
        return result;
    }

    @Override
    public Inventory convertToEntity(InventoryDto dto) {
        Inventory result = new Inventory();
        result.setId(dto.getId());
        result.setTotalSold(dto.getTotalSold());
        result.setListed(dto.getListed());
        if(dto.getUser() != null) {
            result.setUser(inventoryRepository.getReferenceById(dto.getId()).getUser());
        }
        if(dto.getProductKeys() != null){
            result.setProductKeys(inventoryRepository.getReferenceById(dto.getId()).getProductKeys());
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
        return appUserBasicDto;
    }

    private ProductKeyBasicDto productKeyToBasicDto(ProductKey entity) {
        ProductKeyBasicDto productKeyBasicDto = new ProductKeyBasicDto();
        productKeyBasicDto.setId(entity.getId());
        productKeyBasicDto.setActivationKey(entity.getActivationKey());
        productKeyBasicDto.setPrice(entity.getPrice());
        return productKeyBasicDto;
    }


}
