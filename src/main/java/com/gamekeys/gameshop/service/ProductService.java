package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.mapper.ProductMapper;
import com.gamekeys.gameshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional // Manage propagation
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;


}
