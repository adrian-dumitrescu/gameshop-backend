package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.ProductDto;
import com.gamekeys.gameshop.mapper.ProductMapper;
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
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts(){
        return productRepository.findAll().stream().map(c -> productMapper.convertToDto(c)).collect(Collectors.toList());
    }

}
