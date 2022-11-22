package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.ProductDetailsDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.mapper.ProductDetailsMapper;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.repository.ProductDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional // Manage propagation
public class ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductDetailsMapper productDetailsMapper;

    public List<ProductDetailsDto> getAllProductDetails(){
        return productDetailsRepository.findAll().stream().map(c -> productDetailsMapper.convertToDto(c)).collect(Collectors.toList());
    }

    public ProductDetailsDto getProductDetailsById(Long productId) {
        ProductDetails productDetails = productDetailsRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(String.format("No productDetails with id " + productId + " was found")));
        return productDetailsMapper.convertToDto(productDetails);
    }
}
