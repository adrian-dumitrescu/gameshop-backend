package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.InventoryDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.InventoryMapper;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.Inventory;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.model.ProductKey;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.InventoryRepository;
import com.gamekeys.gameshop.repository.ProductDetailsRepository;
import com.gamekeys.gameshop.repository.ProductKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final AppUserRepository appUserRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductKeyRepository productKeyRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryDto getUserInventory(String userEmail) {
        Inventory userInventory = inventoryRepository.findByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No inventory was found for user with email " + userEmail)));
        return inventoryMapper.convertToDto(userInventory);
    }

    @Transactional
    public InventoryDto addKeyToUserInventory(String activationKey, BigDecimal productKeyPrice, String userEmail, String productTitle) {

        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));
        ProductDetails productDetails = productDetailsRepository.findProductByTitle(productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No product with name " + productTitle + " was found")));

        Inventory userInventory = appUser.getInventory();

        ProductKey newKey = new ProductKey();
        newKey.setActivationKey(activationKey);
        newKey.setPrice(productKeyPrice);
        newKey.setProductDetails(productDetails);
        newKey.setInventory(userInventory);
        productKeyRepository.save(newKey);

        userInventory.setListed(userInventory.getListed()+1);
        Set<ProductKey> userProductKeys = userInventory.getProductKeys();
        userProductKeys.add(newKey);
        userInventory.setProductKeys(userProductKeys); // this needs to add to the existing set. Now it just overwrites the set

        return inventoryMapper.convertToDto(userInventory);
    }


    public InventoryDto deleteKeyFromInventory(String activationKey, String userEmail) {
        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

        Inventory userInventory = appUser.getInventory();

        productKeyRepository.deleteProductKeyByActivationKey(activationKey);

        userInventory.setListed(userInventory.getListed()-1);
        inventoryRepository.save(userInventory);

        return inventoryMapper.convertToDto(userInventory);
    }
}
