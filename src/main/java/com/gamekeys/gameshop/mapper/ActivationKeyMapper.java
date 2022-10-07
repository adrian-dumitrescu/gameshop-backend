package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.ActivationKeyDto;
import com.gamekeys.gameshop.entity.ActivationKey;
import org.springframework.stereotype.Service;

@Service
public class ActivationKeyMapper implements Mapper<ActivationKey, ActivationKeyDto>{
    @Override
    public ActivationKeyDto convertToDto(ActivationKey entity) {
        ActivationKeyDto result = new ActivationKeyDto();
        result.setId(entity.getId());
        result.setProductKey(entity.getProductKey());
        result.setUser(entity.getUser());
        result.setProduct(entity.getProduct());
        return result;
    }

    @Override
    public ActivationKey convertToEntity(ActivationKeyDto dto) {
        ActivationKey result = new ActivationKey();
        result.setId(dto.getId());
        result.setProductKey(dto.getProductKey());
        result.setUser(dto.getUser());
        result.setProduct(dto.getProduct());
        return result;
    }

}
