package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.service.ActivationKeyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/key")
//@RequestMapping({"/api","/api/user"})
@CrossOrigin(origins = "http://localhost:4200/")
public class ActivationKeyController {

    private final ActivationKeyService activationKeyService;

//    @GetMapping("/all/{email}")
//    public ResponseEntity<List<ProductKeyDto>> getAllKeysForUser(@PathVariable("email") String email) {
//        List<ProductKeyDto> activationKeys = activationKeyService.getAllKeysForUser(email);
//        return new ResponseEntity<>(activationKeys, OK);
//    }
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
