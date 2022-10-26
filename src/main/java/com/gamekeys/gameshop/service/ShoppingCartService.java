package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.ShoppingCartMapper;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.ShoppingCart;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartMapper shoppingCartMapper;

    private final AppUserRepository appUserRepository;

    public ShoppingCartDto getShoppingCart(String userEmail) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No inventory was found for user with email " + userEmail)));
        return shoppingCartMapper.convertToDto(shoppingCart);
    }

    @Transactional
    public ShoppingCartDto addItemToShoppingCart() {

        //AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

//        Inventory userInventory = appUser.getInventory();
//
//        ShoppingCart shoppingCart = appUser.getShoppingCart();
//        Set<CartItem> cartItems = shoppingCart.getCartItems();
//
//        ProductKey newKey = new ProductKey();
//        newKey.setActivationKey(activationKey);
//        newKey.setPrice(productKeyPrice);
//        newKey.setProductDetails(productDetails);
//        newKey.setInventory(userInventory);
//        productKeyRepository.save(newKey);
//
//        userInventory.setListed(userInventory.getListed()+1);
//        Set<ProductKey> userProductKeys = userInventory.getProductKeys();
//        userProductKeys.add(newKey);
//        userInventory.setProductKeys(userProductKeys); // this needs to add to the existing set. Now it just overwrites the set

        return shoppingCartMapper.convertToDto(new ShoppingCart());
    }


    public ShoppingCartDto deleteItemFromShoppingCart(String activationKey, String userEmail) {
        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

        ShoppingCart shoppingCart = appUser.getShoppingCart();

//        productKeyRepository.deleteProductKeyByActivationKey(activationKey);
//        // delete Cart Item By activation Key??!??!?!
//
//        userInventory.setListed(userInventory.getListed()-1);
//        inventoryRepository.save(userInventory);

        return shoppingCartMapper.convertToDto(shoppingCart);
    }

    public ShoppingCartDto deleteShoppingCart(String activationKey, String userEmail) {
        return null;
    }
}
