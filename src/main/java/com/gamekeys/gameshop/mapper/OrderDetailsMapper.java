package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.functional.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.OrderItemBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.ProductDetailsBasicDto;
import com.gamekeys.gameshop.dto.model.OrderDetailsDto;
import com.gamekeys.gameshop.model.*;
import com.gamekeys.gameshop.repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderDetailsMapper implements Mapper<OrderDetails, OrderDetailsDto> {

    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDetailsDto convertToDto(OrderDetails entity) {
        OrderDetailsDto result = new OrderDetailsDto();
        result.setId(entity.getId());
        result.setTotal(entity.getTotal());
        result.setWithGuard(entity.getWithGuard());
        result.setPaymentOption(entity.getPaymentOption());
        result.setCreatedAt(entity.getCreatedAt());
        result.setModifiedAt(entity.getModifiedAt());
        if(entity.getUser() != null) {
            result.setUser(appUserToBasicDto(entity.getUser()));
        }
        if(entity.getOrderItems() != null){
            result.setOrderItems(entity.getOrderItems().stream().map(orderItem -> orderItemToBasicDto(orderItem)).collect(Collectors.toSet()));
        }
        return result;
    }

    @Override
    public OrderDetails convertToEntity(OrderDetailsDto dto) {
        OrderDetails result = new OrderDetails();
        result.setId(dto.getId());
        result.setTotal(dto.getTotal());
        result.setWithGuard(dto.getWithGuard());
        result.setPaymentOption(dto.getPaymentOption());
        result.setCreatedAt(dto.getCreatedAt());
        result.setModifiedAt(dto.getModifiedAt());
        if(dto.getUser() != null) {
            result.setUser(orderDetailsRepository.getReferenceById(dto.getId()).getUser());
        }
        if(dto.getOrderItems() != null){
            result.setOrderItems(orderDetailsRepository.getReferenceById(dto.getId()).getOrderItems());
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

    private OrderItemBasicDto orderItemToBasicDto(OrderItem entity) {
        OrderItemBasicDto result = new OrderItemBasicDto();
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
        result.setProductDetails(productDetailsToBasicDto(entity.getProductDetails()));
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

