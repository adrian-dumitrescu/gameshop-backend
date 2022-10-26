package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/shopping-cart")
@CrossOrigin(origins = "http://localhost:4200/")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;


    @GetMapping("/all/{email}")
    public ResponseEntity<ShoppingCartDto> getShoppingCart(@PathVariable("email") String userEmail) {
        ShoppingCartDto shoppingCart = shoppingCartService.getShoppingCart(userEmail);
        return new ResponseEntity<>(shoppingCart, OK);
    }

    @PostMapping("/add/item")
    public ResponseEntity<ShoppingCartDto> addItemToShoppingCart(@RequestParam("userEmail") String userEmail,
                                                              @RequestParam("seller") String seller,
                                                              @RequestParam("productTitle") String productTitle,
                                                              @RequestParam("productKeyPrice") BigDecimal productKeyPrice){
        ShoppingCartDto shoppingCart = shoppingCartService.addItemToShoppingCart();
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
