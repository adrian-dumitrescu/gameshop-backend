package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<ShoppingCartDto> addItemToShoppingCart(@RequestParam("userEmail") String userEmail,
                                                                 @RequestParam("sellerEmail") String sellerEmail,
                                                                 @RequestParam("productTitle") String productTitle) {
        ShoppingCartDto shoppingCart = shoppingCartService.addItemToShoppingCart(userEmail, sellerEmail, productTitle);
        return new ResponseEntity<>(shoppingCart, CREATED);
    }

    @DeleteMapping("/delete/cart")
    public ResponseEntity<ShoppingCartDto> removeShoppingCart(@RequestParam("activationKey") String activationKey,
                                                              @RequestParam("userEmail") String userEmail) {
        ShoppingCartDto shoppingCart = shoppingCartService.deleteShoppingCart(activationKey, userEmail);
        return new ResponseEntity<>(shoppingCart, OK);
    }

    @DeleteMapping("/delete/item")
    public ResponseEntity<ShoppingCartDto> removeItemFromShoppingCart(@RequestParam("activationKey") String activationKey,
                                                                      @RequestParam("userEmail") String userEmail) {
        ShoppingCartDto shoppingCart = shoppingCartService.deleteItemFromShoppingCart(activationKey, userEmail);
        return new ResponseEntity<>(shoppingCart, OK);
    }

}
