package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    //private final OrderDetailsMapper orderDetailsMapper;
}
