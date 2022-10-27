package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.ShoppingCartMapper;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.CartItem;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.ShoppingCart;
import com.gamekeys.gameshop.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartMapper shoppingCartMapper;

    private final AppUserRepository appUserRepository;

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    public ShoppingCartDto getShoppingCart(String userEmail) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No shopping-cart was found for user with email " + userEmail)));
        return shoppingCartMapper.convertToDto(shoppingCart);
    }

    @Transactional
    public ShoppingCartDto addItemToShoppingCart(String clientEmail, String sellerEmail, String productTitle) {
//        @RequestParam("clientEmail") String clientEmail,
//        @RequestParam("vendorEmail") String vendorEmail,
//        @RequestParam("productTitle") String productTitle
        AppUser clientUser = appUserRepository.findAppUserByEmail(clientEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + clientEmail + " was found")));
        Product product = productRepository.findProductByUserEmailAndProductDetailsTitle(sellerEmail, productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No product with user email " + clientEmail + " and product title " + productTitle + " was found")));

        ShoppingCart userShoppingCart = getUserShoppingCart(clientEmail, clientUser);

        Set<CartItem> cartItems;
        if(userShoppingCart.getCartItems() != null){
            cartItems = userShoppingCart.getCartItems();
        }else {
            cartItems = new HashSet<>();
        }

        CartItem userCartItem = updateCartItem(userShoppingCart, product, sellerEmail, productTitle);
        cartItems.add(userCartItem);


        userShoppingCart.setCartItems(cartItems);
        userShoppingCart.setTotal(product.getPricePerKey().multiply(new BigDecimal(userCartItem.getQuantity())));
        userShoppingCart.setModifiedAt(LocalDateTime.now());
        shoppingCartRepository.save(userShoppingCart);

        //Set<CartItem> userCartItems = getUserCartItems(userShoppingCart);

//        CartItem userCartItem = (CartItem) userShoppingCart.getCartItems().stream().map(cartItem -> {
//            if(cartItem.getProduct().getProductDetails().getTitle().equals(productTitle)){
//                return cartItem;
//            }
//            return new CartItem();
//        });

//        Set<CartItem> cartItems = new HashSet<>();
//        if(userShoppingCart.getCartItems() != null) {
//            cartItems = userShoppingCart.getCartItems();
//        }
        //ProductDetails productDetails = productDetailsRepository.findProductByTitle(productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No productDetails with title " + productTitle + " was found")));
        //CartItem cartItem = new CartItem();


        //cartItems.add(cartItem);
        return shoppingCartMapper.convertToDto(userShoppingCart);
    }

//    private boolean cartItemExists(ShoppingCart userShoppingCart, String productTitle) {
////        Set<CartItem> cartItems = userShoppingCart.getCartItems().stream().filter(cartItem -> {
////            return cartItem.getProduct().getProductDetails().getTitle().equals(productTitle);
////        }).collect(Collectors.toSet());
//    }

    private CartItem updateCartItem(ShoppingCart userShoppingCart, Product product, String sellerEmail, String productTitle){
        Optional<CartItem> optionalCartItem = cartItemRepository.findCartItemByProductUserEmailAndProductProductDetailsTitle(sellerEmail,productTitle);
        if(optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setModifiedAt(LocalDateTime.now());
            cartItemRepository.save(cartItem);
            return cartItem;
        }else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(newCartItem.getQuantity() + 1);
            newCartItem.setSellerEmail(sellerEmail);
            newCartItem.setCreatedAt(LocalDateTime.now());
            newCartItem.setModifiedAt(LocalDateTime.now());
            newCartItem.setShoppingCart(userShoppingCart);
            cartItemRepository.save(newCartItem);
            return newCartItem;
        }
    }
    private ShoppingCart getUserShoppingCart(String clientEmail, AppUser clientUser){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findShoppingCartByUserEmail(clientEmail);

        if(shoppingCart.isPresent()){
            return shoppingCart.get();
        }
        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(clientUser);
        newShoppingCart.setCreatedAt(LocalDateTime.now());
        newShoppingCart.setModifiedAt(LocalDateTime.now());
        shoppingCartRepository.save(newShoppingCart);

        clientUser.setShoppingCart(newShoppingCart);
        appUserRepository.save(clientUser);
        return newShoppingCart;
    }


    public ShoppingCartDto deleteItemFromShoppingCart(String activationKey, String userEmail) {
        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

        ShoppingCart shoppingCart = appUser.getShoppingCart();

//        productKeyRepository.deleteProductKeyByActivationKey(activationKey);
//        // delete Cart Item By activation Key??!??!?!
//
//        userProduct.setListed(userProduct.getListed()-1);
//        productRepository.save(userProduct);

        return shoppingCartMapper.convertToDto(shoppingCart);
    }

    public ShoppingCartDto deleteShoppingCart(String activationKey, String userEmail) {
        return null;
    }
}
