package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.functional.basic.*;
import com.gamekeys.gameshop.dto.model.AppUserDto;
import com.gamekeys.gameshop.model.*;
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
        result.setCartItems(entity.getCartItems().stream().map(cartItem -> cartItemToBasicDto(cartItem)).collect(Collectors.toSet()));
        return result;
    }


    private OrderDetailsBasicDto orderDetailsToBasicDto(OrderDetails entity) {
        OrderDetailsBasicDto result = new OrderDetailsBasicDto();
        result.setId(entity.getId());
        result.setTotal(entity.getTotal());
        result.setWithGuard(entity.getWithGuard());
        result.setPaymentOption(entity.getPaymentOption());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        result.setOrderItem(entity.getOrderItems().stream().map(orderItem -> orderItemToBasicDto(orderItem)).collect(Collectors.toSet()));
        return result;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto result = new ProductBasicDto();
        result.setId(entity.getId());
        result.setPricePerKey(entity.getPricePerKey());
        result.setDiscountPercent(entity.getDiscountPercent());
        result.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
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

    private OrderItemBasicDto orderItemToBasicDto(OrderItem entity) {
        OrderItemBasicDto result = new OrderItemBasicDto();
        result.setId(entity.getId());
        result.setQuantity(entity.getQuantity());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        result.setProduct(productToBasicDto(entity.getProduct())); // demo
        return result;
    }

    private ProductDetailsBasicDto productDetailsToBasicDto(ProductDetails entity) {
        ProductDetailsBasicDto result = new ProductDetailsBasicDto();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setSummary(entity.getSummary());
        result.setContentRating(entity.getContentRating());
        result.setInitialRelease(entity.getInitialRelease());
        result.setGenres(entity.getGenres());
        result.setPlatforms(entity.getPlatforms());
        result.setPublisher(entity.getPublisher());
        return result;
    }

}
