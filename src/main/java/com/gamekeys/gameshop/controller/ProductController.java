package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.ProductDto;
import com.gamekeys.gameshop.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getUserProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, OK);
    }

    @GetMapping("/all/{userEmail}")
    public ResponseEntity<List<ProductDto>> getUserProducts(@PathVariable("userEmail") String userEmail) {
        List<ProductDto> userProducts = productService.getUserProducts(userEmail);
        return new ResponseEntity<>(userProducts, OK);
    }

    @PostMapping("/add/key")
    public ResponseEntity<ProductDto> addKeyToUserProducts(@RequestParam("activationKey") String activationKey,
                                                           @RequestParam("userEmail") String userEmail,
                                                           @RequestParam("productTitle") String productTitle,
                                                           @RequestParam("productKeyPrice") BigDecimal productKeyPrice,
                                                           @RequestParam("productKeyDiscount") Integer productKeyDiscount) {
        ProductDto userProduct = productService.addKeyToUserProduct(activationKey, productKeyPrice, userEmail, productTitle, productKeyDiscount);
        return new ResponseEntity<>(userProduct, CREATED);
    }

    @PutMapping("/update/price")
    public ResponseEntity<ProductDto> updateProductKeyPrice(@RequestParam("productId") Long productId,
                                                            @RequestParam("productKeyPrice") BigDecimal productKeyPrice,
                                                            @RequestParam("productKeyDiscount") Integer productKeyDiscount) {
        ProductDto userProduct = productService.updateProductKeyPrice(productId, productKeyPrice, productKeyDiscount);
        return new ResponseEntity<>(userProduct, OK);
    }

    @DeleteMapping("/delete/key")
    public ResponseEntity<ProductDto> deleteKeyFromProduct(@RequestParam("activationKey") String activationKey,
                                                           @RequestParam("userEmail") String userEmail,
                                                           @RequestParam("productTitle") String productTitle) {
        ProductDto userProduct = productService.deleteKeyFromProduct(activationKey, userEmail, productTitle);
        return new ResponseEntity<>(userProduct, OK);
    }

    @DeleteMapping("/delete/product-inventory")
    public ResponseEntity<?> deleteProduct(@RequestParam("userEmail") String userEmail,
                                           @RequestParam("productTitle") String productTitle) {
        productService.deleteProductFromUser(userEmail, productTitle);
        return new ResponseEntity<>(OK);
    }

}
