package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    //private final OrderItemMapper orderItemMapper;

}
