package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.CartItemBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.model.*;
import com.gamekeys.gameshop.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShoppingCartMapper implements Mapper<ShoppingCart, ShoppingCartDto> {

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartDto convertToDto(ShoppingCart entity) {
        ShoppingCartDto result = new ShoppingCartDto();
        result.setId(entity.getId());
        result.setTotal(entity.getTotal());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        if(entity.getUser() != null) {
            result.setUser(appUserToBasicDto(entity.getUser()));
        }
        if(entity.getCartItems() != null){
            result.setCartItems(entity.getCartItems().stream().map(cartItem -> cartItemToBasicDto(cartItem)).collect(Collectors.toSet()));
        }
        return result;
    }

    @Override
    public ShoppingCart convertToEntity(ShoppingCartDto dto) {
        ShoppingCart result = new ShoppingCart();
        result.setId(dto.getId());
        result.setTotal(dto.getTotal());
        result.setCreatedAt(dto.getCreatedAt());
        result.setModifiedAt(dto.getModifiedAt());
        if(dto.getUser() != null) {
            result.setUser(shoppingCartRepository.getReferenceById(dto.getId()).getUser());
        }
        if(dto.getCartItems() != null){
            result.setCartItems(shoppingCartRepository.getReferenceById(dto.getId()).getCartItems());
        }
        return result;
    }

    private AppUserBasicDto appUserToBasicDto(AppUser entity) {
        AppUserBasicDto result = new AppUserBasicDto();
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
        return result;
    }

    private AppUserBasicDto sellerUserToBasicDto(AppUser entity) {
        AppUserBasicDto result = new AppUserBasicDto();
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
        return result;
    }

    private CartItemBasicDto cartItemToBasicDto(CartItem entity) {
        CartItemBasicDto result = new CartItemBasicDto();
        result.setId(entity.getId());
        result.setQuantity(entity.getQuantity());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        result.setProduct(productToBasicDto(entity.getProduct())); // demo
        return result;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto result = new ProductBasicDto();
        result.setId(entity.getId());
        result.setPricePerKey(entity.getPricePerKey());
        result.setDiscountPercent(entity.getDiscountPercent());
        result.setUser(sellerUserToBasicDto(entity.getUser()));
        result.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
        return result;
    }

    private ProductDetailsBasicDto productDetailsToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setPublisher(entity.getPublisher());
        return result;
    }

}
