package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.mapper.ActivationKeyMapper;
import com.gamekeys.gameshop.repository.ActivationKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
//@Transactional // Manage propagation
public class ActivationKeyService {

    private final ActivationKeyRepository activationKeyRepository;

    private final ActivationKeyMapper activationKeyMapper;

}
