package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.entity.AppRole;
import com.gamekeys.gameshop.dto.AppRoleDto;

import org.springframework.stereotype.Service;

@Service
public class AppRoleMapper implements Mapper<AppRole, AppRoleDto>{
    @Override
    public AppRoleDto convertToDto(AppRole entity) {
        AppRoleDto result = new AppRoleDto();
        result.setId(entity.getId());
        result.setRole(entity.getRole());
        return result;
    }

    @Override
    public AppRole convertToEntity(AppRoleDto dto) {
        AppRole result = new AppRole();
        result.setId(dto.getId());
        result.setRole(dto.getRole());
        return result;
    }
}
