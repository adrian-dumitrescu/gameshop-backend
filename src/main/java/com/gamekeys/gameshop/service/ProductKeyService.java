package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.ProductKeyDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.ProductKeyMapper;
import com.gamekeys.gameshop.model.ProductKey;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductDetailsRepository;
import com.gamekeys.gameshop.repository.ProductKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional // Manage propagation
public class ProductKeyService {

    private final ProductKeyRepository productKeyRepository;
    private final AppUserRepository appUserRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductKeyMapper productKeyMapper;

    public ProductKeyDto findProductKeyById(Long id) {
        ProductKey productKey = productKeyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No key with id " + id + " was found")));
        return productKeyMapper.convertToDto(productKey);
    }

//    public List<ProductKeyDto> findAllProductKeysByTitle(String productTitle) {
//        Set<ProductKey> productKeys = productKeyRepository.findAllByProductDetailsTitle(productTitle).orElseThrow(() -> new EntityNotFoundException(String.format("There are no keys for title: " + productTitle)));//return productKeyRepository.findAllByInventory_UserEmail(userEmail).stream().map(c -> productKeyMapper.convertToDto(c)).collect(Collectors.toList());
//        return productKeys.stream().map(productKey -> productKeyMapper.convertToDto(productKey)).collect(Collectors.toList());
//    }

    public void deleteProductKeyById(Long id) {
        productKeyRepository.deleteById(id);
    }

    public void deleteProductKeyByActivationKey(String activationKey) {
        ProductKey productKey = productKeyRepository.findProductKeyByActivationKey(activationKey).orElseThrow(() -> new EntityNotFoundException(String.format("No key with activation key " + activationKey + " was found")));
        productKeyRepository.delete(productKey);
    }


}
