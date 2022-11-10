package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.CartItemBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.model.ShoppingCartDto;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.CartItem;
import com.gamekeys.gameshop.model.Product;
import com.gamekeys.gameshop.model.ShoppingCart;
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
        AppUserBasicDto appUserBasicDto = new AppUserBasicDto();
        appUserBasicDto.setId(entity.getId());
        appUserBasicDto.setFirstName(entity.getFirstName());
        appUserBasicDto.setLastName(entity.getLastName());
        appUserBasicDto.setEmail(entity.getEmail());
        appUserBasicDto.setPassword(entity.getPassword());
        appUserBasicDto.setRoles(entity.getRoles());
        appUserBasicDto.setProfileImageUrl(entity.getProfileImageUrl());
        appUserBasicDto.setJoinDate(entity.getJoinDate());
        appUserBasicDto.setIsNotLocked(entity.getIsNotLocked());
        appUserBasicDto.setIsEnabled(entity.getIsEnabled());
        appUserBasicDto.setNickname(entity.getNickname());
        appUserBasicDto.setCountry(entity.getCountry());
        appUserBasicDto.setGender(entity.getGender());
        appUserBasicDto.setAge(entity.getAge());
        return appUserBasicDto;
    }

    private CartItemBasicDto cartItemToBasicDto(CartItem entity) {
        CartItemBasicDto cartItemBasicDto = new CartItemBasicDto();
        cartItemBasicDto.setId(entity.getId());
        cartItemBasicDto.setQuantity(entity.getQuantity());
        cartItemBasicDto.setCreatedAt(entity.getCreatedAt());
        cartItemBasicDto.setModifiedAt(entity.getModifiedAt());
        cartItemBasicDto.setProduct(productToBasicDto(entity.getProduct())); // demo
        return cartItemBasicDto;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto productBasicDto = new ProductBasicDto();
        productBasicDto.setId(entity.getId());
        productBasicDto.setPricePerKey(entity.getPricePerKey());
        productBasicDto.setDiscountPercent(entity.getDiscountPercent());
        return productBasicDto;
    }

}
