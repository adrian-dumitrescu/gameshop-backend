package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.entity.AppUser;
import com.gamekeys.gameshop.dto.AppUserDto;
import org.springframework.stereotype.Service;

@Service
public class AppUserMapper implements Mapper<AppUser, AppUserDto> {

    @Override
    public AppUserDto convertToDto(AppUser entity) {
        AppUserDto result = new AppUserDto();
        result.setId(entity.getId());
        result.setFirstName(entity.getFirstName());
        result.setLastName(entity.getLastName());
        result.setEmail(entity.getEmail());
        result.setPassword(entity.getPassword());
        result.setRoles(entity.getRoles());
        result.setProfileImageUrl(entity.getProfileImageUrl());
        result.setJoinDate(entity.getJoinDate());
        result.setIsNotLocked(entity.getIsNotLocked());
        result.setIsEnabled(entity.getIsEnabled());
        return result;
    }

    @Override
    public AppUser convertToEntity(AppUserDto dto) {
        AppUser result = new AppUser();
        result.setId(dto.getId());
        result.setFirstName(dto.getFirstName());
        result.setLastName(dto.getLastName());
        result.setEmail(dto.getEmail());
        result.setPassword(dto.getPassword());
        result.setRoles(dto.getRoles());
        result.setProfileImageUrl(dto.getProfileImageUrl());
        result.setJoinDate(dto.getJoinDate());
        result.setIsEnabled(dto.getIsNotLocked());
        result.setIsEnabled(dto.getIsEnabled());
        return result;
    }
}
