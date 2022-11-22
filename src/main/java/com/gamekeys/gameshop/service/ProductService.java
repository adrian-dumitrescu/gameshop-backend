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

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            if(product.getProductKeys().size() > 0) {
                return productMapper.convertToDto(product);
            }
            return null;
        }).collect(Collectors.toList());
    }

    public List<ProductDto> getUserProducts(String userEmail) {
        Set<Product> userProducts = productRepository.findAllByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No product was found for user with email " + userEmail)));
        return userProducts.stream().map(product -> productMapper.convertToDto(product)).collect(Collectors.toList());
    }

    @Transactional
    public ProductDto addKeyToUserProduct(String activationKey, BigDecimal pricePerKey, String userEmail, String productTitle, Integer productKeyDiscount) {

        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

        Product userProduct = getProductByTitle(userEmail, productTitle, pricePerKey, productKeyDiscount, appUser);

        ProductKey newKey = new ProductKey();
        newKey.setActivationKey(activationKey);
        newKey.setProduct(userProduct);
        productKeyRepository.save(newKey);

        Set<ProductKey> userProductKeys = userProduct.getProductKeys();
        userProductKeys.add(newKey);

        return productMapper.convertToDto(userProduct);
    }

    private Product getProductByTitle(String userEmail, String productTitle, BigDecimal pricePerKey, Integer productKeyDiscount, AppUser appUser) {
        Optional<Product> product = productRepository.findProductByUserEmailAndProductDetailsTitle(userEmail, productTitle);

        if (product.isPresent()) {
            product.get().setDiscountPercent(productKeyDiscount);
            product.get().setPricePerKey(pricePerKey);
            return product.get();
        }
        ProductDetails productDetails = productDetailsRepository.findProductByTitle(productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No productDetails with title " + productTitle + " was found")));
        Product newProduct = new Product();
        newProduct.setProductDetails(productDetails);
        newProduct.setUser(appUser);
        newProduct.setPricePerKey(pricePerKey);
        newProduct.setDiscountPercent(productKeyDiscount);
        newProduct.setPricePerKey(pricePerKey);

        productRepository.save(newProduct);
        return newProduct;
    }

    private void applyDiscountToProduct(Product product, BigDecimal pricePerKey, Integer productKeyDiscount) {
        BigDecimal discountedPrice = pricePerKey.subtract(pricePerKey.multiply(BigDecimal.valueOf(productKeyDiscount)).divide(BigDecimal.valueOf(100)));
        product.setDiscountPercent(productKeyDiscount);
        product.setPricePerKey(discountedPrice);
    }


    public ProductDto deleteKeyFromProduct(String activationKey, String userEmail, String productTitle) {

        ProductKey productKey = productKeyRepository.findProductKeyByActivationKey(activationKey).orElseThrow(() -> new EntityNotFoundException(String.format("No key with activation key " + activationKey + " was found")));
        productKeyRepository.delete(productKey);

        Product userProduct = productRepository.findProductByUserEmailAndProductDetailsTitle(userEmail, productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No inventory with user email " + userEmail + " and product title " + productTitle + " was found")));
        return productMapper.convertToDto(userProduct);
        //        if(userProduct.getProductKeys().size() > 0){
//            return productMapper.convertToDto(userProduct);
//        }else{
//            productRepository.delete(userProduct);
//            return productMapper.convertToDto(userProduct);
//        }
    }

    public void deleteProductFromUser(String userEmail, String productTitle) {
        Product product = productRepository.findProductByUserEmailAndProductDetailsTitle(userEmail, productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("No inventory with user email " + userEmail + " and product title " + productTitle + " was found")));
        productRepository.delete(product);
    }

    public ProductDto updateProductKeyPrice(Long productId, BigDecimal productKeyPrice, Integer productKeyDiscount) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            product.get().setDiscountPercent(productKeyDiscount);
            product.get().setPricePerKey(productKeyPrice);
            productRepository.save(product.get());
            return productMapper.convertToDto(product.get());
        }else {
            throw new EntityNotFoundException(String.format("No product was found for user with id " + productId));
        }

    }


}
