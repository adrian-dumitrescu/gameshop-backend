package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.ProductKeyDto;
import com.gamekeys.gameshop.service.ProductKeyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/key")
//@RequestMapping({"/api","/api/user"})
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductKeyController {

    private final ProductKeyService productKeyService;

    @GetMapping("/all/{productTitle}")
    public ResponseEntity<List<ProductKeyDto>> getAllKeysForUser(@PathVariable("productTitle") String productTitle) {
        List<ProductKeyDto> activationKeys = productKeyService.findAllProductKeysByTitle(productTitle);
        return new ResponseEntity<>(activationKeys, OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductKeyDto> getProductKeyById(@PathVariable("id") Long id) {
        ProductKeyDto productKey = productKeyService.findProductKeyById(id);
        return new ResponseEntity<>(productKey, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductKeyById(@PathVariable("id") Long id) {
        productKeyService.deleteProductKeyById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{activationKey}")
    public ResponseEntity<?> deleteProductKeyByActivationKey(@PathVariable("activationKey") String activationKey) {
        productKeyService.deleteProductKeyByActivationKey(activationKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }



//
////    @PostMapping("/add")
////    public ResponseEntity<ActivationKeyDto> addKeyForUser(@RequestParam(name = "activationKey") String activationKey, @RequestParam(name = "userEmail") String userEmail, @RequestParam(name = "productName") String productName) {
////        ActivationKeyDto activationKeyDto = activationKeyService.addKeyForUser(activationKey, userEmail, productName);
////        return new ResponseEntity<>(activationKeyDto, OK);
////    }
//
//    @PostMapping("/add/{activationKey}/{userEmail}/{productName}")
//    public ResponseEntity<ProductKeyDto> addKeyForUser(@PathVariable("activationKey") String activationKey, @PathVariable("userEmail") String userEmail, @PathVariable("productName") String productName) {
//        ProductKeyDto productKeyDto = activationKeyService.addKeyForUser(activationKey, userEmail, productName);
//        return new ResponseEntity<>(productKeyDto, OK);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        activationKeyService.deleteKeyById(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/find/{id}")
//    public ResponseEntity<ProductKeyDto> getUserById(@PathVariable("id") Long id) {
//        ProductKeyDto productKeyDto = activationKeyService.findKeyById(id);
//        return new ResponseEntity<>(productKeyDto, HttpStatus.OK);
//    }

}
