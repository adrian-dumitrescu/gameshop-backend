package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.ProductDetailsDto;
import com.gamekeys.gameshop.service.ProductDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/product-details")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductDetailsController {

    private final ProductDetailsService productDetailsService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDetailsDto>> getAllProductDetails() {
        List<ProductDetailsDto> productDetails = productDetailsService.getAllProductDetails();
        return new ResponseEntity<>(productDetails, OK);
    }

    @GetMapping("/title/{productId}")
    public ResponseEntity<ProductDetailsDto> getProductDetailsById(@PathVariable("productId") Long productId) {
        ProductDetailsDto productDetails = productDetailsService.getProductDetailsById(productId);
        return new ResponseEntity<>(productDetails, OK);
    }
}
