package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/shopping-cart")
@CrossOrigin(origins = "http://localhost:4200/")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/all/{userEmail}")
    public ResponseEntity<ShoppingCartDto> getShoppingCart(@PathVariable("userEmail") String userEmail) {
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart(userEmail);
        return new ResponseEntity<>(shoppingCart, OK);
    }

    @PostMapping("/add/item")
    public ResponseEntity<ShoppingCartDto> addItemToShoppingCart(@RequestParam("clientEmail") String clientEmail,
                                                                 @RequestParam("sellerEmail") String sellerEmail,
                                                                 @RequestParam("productTitle") String productTitle) {
        ShoppingCartDto shoppingCart = shoppingCartService.addItemToShoppingCart(clientEmail, sellerEmail, productTitle);
        return new ResponseEntity<>(shoppingCart, CREATED);
    }

    @PutMapping("/increment")
    public ResponseEntity<ShoppingCartDto> incrementItemQuantity(@RequestParam("cartItemId") Long cartItemId) {
        ShoppingCartDto shoppingCart = shoppingCartService.incrementItemQuantity(cartItemId);
        return new ResponseEntity<>(shoppingCart, OK);
    }

    @PutMapping("/decrement")
    public ResponseEntity<ShoppingCartDto> decrementItemQuantity(@RequestParam("cartItemId") Long cartItemId) {
        ShoppingCartDto shoppingCart = shoppingCartService.decrementItemQuantity(cartItemId);
        return new ResponseEntity<>(shoppingCart, OK);
    }

    @PutMapping("/refresh")
    public ResponseEntity<ShoppingCartDto> refreshShoppingCart(@RequestParam("userEmail") String userEmail) {
        ShoppingCartDto shoppingCart = shoppingCartService.refreshShoppingCart(userEmail);
        return new ResponseEntity<>(shoppingCart, OK);
    }

    @DeleteMapping("/delete/cart")
    public ResponseEntity<?> deleteShoppingCart(@RequestParam("userEmail") String userEmail) {
        shoppingCartService.deleteShoppingCart(userEmail);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/delete/item/by-title")
    public ResponseEntity<?> deleteItemFromShoppingCartByTitle(@RequestParam("userEmail") String userEmail,
                                                               @RequestParam("productTitle") String productTitle) {
        shoppingCartService.deleteItemFromShoppingCartByTitle(userEmail, productTitle);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @DeleteMapping("/delete/item/{cartItemId}")
    public ResponseEntity<ShoppingCartDto> deleteItemFromShoppingCartById(@PathVariable("cartItemId") Long cartItemId) {
        ShoppingCartDto shoppingCart = shoppingCartService.deleteItemFromShoppingCartById(cartItemId);
        return new ResponseEntity<>(shoppingCart, OK);
    }

}
