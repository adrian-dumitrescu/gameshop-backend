package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.ActivationKeyDto;
import com.gamekeys.gameshop.entity.ActivationKey;
import com.gamekeys.gameshop.entity.AppUser;
import com.gamekeys.gameshop.entity.Product;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.ActivationKeyMapper;
import com.gamekeys.gameshop.repository.ActivationKeyRepository;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional // Manage propagation
public class ActivationKeyService {

    private final ActivationKeyRepository activationKeyRepository;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final ActivationKeyMapper activationKeyMapper;

    public List<ActivationKeyDto> getAllKeysForUser(String userEmail) {
        //List<ActivationKey> activationKeys = activationKeyRepository.findAllByUserEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("There are no keys for this user")));
        return activationKeyRepository.findAllByUserEmail(userEmail).stream().map(c -> activationKeyMapper.convertToDto(c)).collect(Collectors.toList());
    }

    public ActivationKeyDto addKeyForUser(String activationKey, String userEmail, String productName) {
        ActivationKey newKey = new ActivationKey();
        Product product = productRepository.findProductByName(productName).orElseThrow(() -> new EntityNotFoundException(String.format("No product with name " + productName + " was found")));
        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));

        newKey.setKeyValue(activationKey);
        newKey.setUser(appUser);
        newKey.setProduct(product);
        activationKeyRepository.save(newKey);

        return activationKeyMapper.convertToDto(newKey);
    }

    public void deleteKeyById(Long id) {
        activationKeyRepository.deleteById(id);
    }

    public ActivationKeyDto findKeyById(Long id) {
        ActivationKey activationKey = activationKeyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No key with id " + id + " was found")));
        return activationKeyMapper.convertToDto(activationKey);
    }
}
