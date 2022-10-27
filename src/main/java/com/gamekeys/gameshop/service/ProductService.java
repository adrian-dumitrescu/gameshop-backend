package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.ProductDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.ProductMapper;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.model.ProductKey;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductDetailsRepository;
import com.gamekeys.gameshop.repository.ProductKeyRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AppUserRepository appUserRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductKeyRepository productKeyRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getUserProducts(String userEmail) {
        Set<Product> userProducts = productRepository.findAllByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No product was found for user with email " + userEmail)));
        return userProducts.stream().map(productKey -> productMapper.convertToDto(productKey)).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto addKeyToUserProduct(String activationKey, BigDecimal pricePerKey, String userEmail, String productTitle) {

        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

        Product userProduct = getProductByTitle(userEmail, productTitle, pricePerKey, appUser);

        ProductKey newKey = new ProductKey();
        newKey.setActivationKey(activationKey);
        newKey.setProduct(userProduct);
        productKeyRepository.save(newKey);

        Set<ProductKey> userProductKeys = userProduct.getProductKeys();
        userProductKeys.add(newKey);

//        userProduct.setProductKeys(userProductKeys); // this needs to add to the existing set. Now it just overwrites the set
//        inventoryRepository.save(userProduct);
//
//        userInventories.add(userProduct);
//        appUser.setProducts(userInventories);
//        appUserRepository.save(appUser);


        return productMapper.convertToDto(userProduct);
    }

    private Product getProductByTitle(String userEmail, String productTitle, BigDecimal pricePerKey, AppUser appUser) {
        Optional<Product> product = productRepository.findProductByUserEmailAndProductDetailsTitle(userEmail, productTitle);

        if (product.isPresent()) {
            return product.get();
        }
        ProductDetails productDetails = productDetailsRepository.findProductByTitle(productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No productDetails with title " + productTitle + " was found")));
        Product newProduct = new Product();
        newProduct.setProductDetails(productDetails);
        newProduct.setUser(appUser);
        newProduct.setPricePerKey(pricePerKey);
        productRepository.save(newProduct);
        return newProduct;
    }


    public ProductDto deleteKeyFromProduct(String activationKey, String userEmail, String productTitle) {

        ProductKey productKey = productKeyRepository.findProductKeyByActivationKey(activationKey).orElseThrow(() -> new EntityNotFoundException(String.format("No key with activation key " + activationKey + " was found")));
        productKeyRepository.delete(productKey);

        Product userProduct = productRepository.findProductByUserEmailAndProductDetailsTitle(userEmail, productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No inventory with user email " + userEmail + " and product title " + productTitle + " was found")));

        return productMapper.convertToDto(userProduct);
    }
}
