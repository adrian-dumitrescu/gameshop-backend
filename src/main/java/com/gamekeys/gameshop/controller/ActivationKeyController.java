package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.ActivationKeyDto;
import com.gamekeys.gameshop.service.ActivationKeyService;
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
public class ActivationKeyController {

    private final ActivationKeyService activationKeyService;

    @GetMapping("/all/{email}")
    public ResponseEntity<List<ActivationKeyDto>> getAllKeysForUser(@PathVariable("email") String email) {
        List<ActivationKeyDto> activationKeys = activationKeyService.getAllKeysForUser(email);
        return new ResponseEntity<>(activationKeys, OK);
    }

//    @PostMapping("/add")
//    public ResponseEntity<ActivationKeyDto> addKeyForUser(@RequestParam(name = "activationKey") String activationKey, @RequestParam(name = "userEmail") String userEmail, @RequestParam(name = "productName") String productName) {
//        ActivationKeyDto activationKeyDto = activationKeyService.addKeyForUser(activationKey, userEmail, productName);
//        return new ResponseEntity<>(activationKeyDto, OK);
//    }

    @PostMapping("/add/{activationKey}/{userEmail}/{productName}")
    public ResponseEntity<ActivationKeyDto> addKeyForUser(@PathVariable("activationKey") String activationKey, @PathVariable("userEmail") String userEmail, @PathVariable("productName") String productName) {
        ActivationKeyDto activationKeyDto = activationKeyService.addKeyForUser(activationKey, userEmail, productName);
        return new ResponseEntity<>(activationKeyDto, OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        activationKeyService.deleteKeyById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ActivationKeyDto> getUserById(@PathVariable("id") Long id) {
        ActivationKeyDto activationKeyDto = activationKeyService.findKeyById(id);
        return new ResponseEntity<>(activationKeyDto, HttpStatus.OK);
    }

}
