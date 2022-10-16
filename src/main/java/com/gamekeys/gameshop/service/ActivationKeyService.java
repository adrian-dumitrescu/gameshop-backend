package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.mapper.ActivationKeyMapper;
import com.gamekeys.gameshop.repository.ActivationKeyRepository;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional // Manage propagation
public class ActivationKeyService {

    private final ActivationKeyRepository activationKeyRepository;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final ActivationKeyMapper activationKeyMapper;

//    public List<ProductKeyDto> getAllKeysForUser(String userEmail) {
//        //List<ActivationKey> activationKeys = activationKeyRepository.findAllByUserEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("There are no keys for this user")));
//        return activationKeyRepository.findAllByUserEmail(userEmail).stream().map(c -> activationKeyMapper.convertToDto(c)).collect(Collectors.toList());
//    }
//
//    public ProductKeyDto addKeyForUser(String activationKey, String userEmail, String productName) {
//        ProductKey newKey = new ProductKey();
//        ProductDetails productDetails = productRepository.findProductByProductName(productName).orElseThrow(() -> new EntityNotFoundException(String.format("No product with name " + productName + " was found")));
//        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));
//
//        newKey.setKeyValue(activationKey);
//        newKey.setUser(appUser);
//        newKey.setProductDetails(productDetails);
//        activationKeyRepository.save(newKey);
//
//        return activationKeyMapper.convertToDto(newKey);
//    }
//
//    public void deleteKeyById(Long id) {
//        activationKeyRepository.deleteById(id);
//    }
//
//    public ProductKeyDto findKeyById(Long id) {
//        ProductKey productKey = activationKeyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No key with id " + id + " was found")));
//        return activationKeyMapper.convertToDto(productKey);
//    }
}
