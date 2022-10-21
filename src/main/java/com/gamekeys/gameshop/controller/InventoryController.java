package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.InventoryDto;
import com.gamekeys.gameshop.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:4200/")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/all/{email}")
    public ResponseEntity<InventoryDto> getUserInventory(@PathVariable("email") String userEmail) {
        InventoryDto userInventory = inventoryService.getUserInventory(userEmail);
        return new ResponseEntity<>(userInventory, OK);
    }

    @PostMapping("/add/key")
    public ResponseEntity<InventoryDto> addKeyToUserInventory(@RequestParam("activationKey") String activationKey,
                                                        @RequestParam("userEmail") String userEmail,
                                                        @RequestParam("productTitle") String productTitle,
                                                        @RequestParam("productKeyPrice") BigDecimal productKeyPrice){
        InventoryDto userInventory = inventoryService.addKeyToUserInventory(activationKey, productKeyPrice, userEmail, productTitle);
        return new ResponseEntity<>(userInventory, OK);
    }



    @DeleteMapping("/delete/key")
    public ResponseEntity<InventoryDto> removeProductKeyFromInventory(@RequestParam("activationKey") String activationKey,
                                                                      @RequestParam("userEmail") String userEmail) {
        InventoryDto userInventory = inventoryService.deleteKeyFromInventory(activationKey, userEmail);
        return new ResponseEntity<>(userInventory, OK);
    }

}
