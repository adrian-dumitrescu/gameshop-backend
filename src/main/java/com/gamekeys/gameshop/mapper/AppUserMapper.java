package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.model.AppUserDto;
import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.basic.OrderDetailsBasicDto;
import com.gamekeys.gameshop.dto.basic.ShoppingCartBasicDto;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.OrderDetails;
import com.gamekeys.gameshop.model.ShoppingCart;
import com.gamekeys.gameshop.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserMapper implements Mapper<AppUser, AppUserDto> {

    private AppUserRepository appUserRepository;

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
        result.setNickname(entity.getNickname());
        result.setCountry(entity.getCountry());
        result.setGender(entity.getGender());
        result.setAge(entity.getAge());
        if(entity.getShoppingCart() != null) {
            result.setShoppingCart(shoppingCartToBasicDto(entity.getShoppingCart()));
        }
        if(entity.getOrderDetails() != null) {
            result.setOrderDetails(entity.getOrderDetails().stream().map(orderDetails -> orderDetailsToBasicDto(orderDetails)).collect(Collectors.toSet()));
        }
        if(entity.getProducts() != null) {
            result.setProducts(entity.getProducts().stream().map(product -> productToBasicDto(product)).collect(Collectors.toSet()));
        }
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
        result.setNickname(dto.getNickname());
        result.setCountry(dto.getCountry());
        result.setGender(dto.getGender());
        result.setAge(dto.getAge());
        if(dto.getShoppingCart() != null) {
            result.setShoppingCart(appUserRepository.getReferenceById(dto.getId()).getShoppingCart());
        }
        if(dto.getOrderDetails() != null){
            result.setOrderDetails(appUserRepository.getReferenceById(dto.getId()).getOrderDetails());
        }
        if(dto.getProducts() != null){
            result.setProducts(appUserRepository.getReferenceById(dto.getId()).getProducts());
        }
        return result;
    }

    private ShoppingCartBasicDto shoppingCartToBasicDto(ShoppingCart entity) {
        ShoppingCartBasicDto result = new ShoppingCartBasicDto();
        result.setId(entity.getId());
        result.setTotal(entity.getTotal());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        return result;
    }

    private OrderDetailsBasicDto orderDetailsToBasicDto(OrderDetails entity) {
        OrderDetailsBasicDto result = new OrderDetailsBasicDto();
        result.setId(entity.getId());
        result.setTotal(entity.getTotal());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        return result;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto result = new ProductBasicDto();
        result.setId(entity.getId());
        result.setPricePerKey(entity.getPricePerKey());
        return result;
    }

}
