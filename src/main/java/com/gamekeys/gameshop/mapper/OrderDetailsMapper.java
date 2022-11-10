package com.gamekeys.gameshop.mapper;

import com.gamekeys.gameshop.dto.basic.AppUserBasicDto;
import com.gamekeys.gameshop.dto.basic.OrderItemBasicDto;
import com.gamekeys.gameshop.dto.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.model.OrderDetailsDto;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.OrderDetails;
import com.gamekeys.gameshop.model.OrderItem;
import com.gamekeys.gameshop.model.Product;
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

    private OrderItemBasicDto orderItemToBasicDto(OrderItem entity) {
        OrderItemBasicDto orderItemBasicDto = new OrderItemBasicDto();
        orderItemBasicDto.setId(entity.getId());
        orderItemBasicDto.setQuantity(entity.getQuantity());
        orderItemBasicDto.setCreatedAt(entity.getCreatedAt());
        orderItemBasicDto.setModifiedAt(entity.getModifiedAt());
        orderItemBasicDto.setProduct(productToBasicDto(entity.getProduct())); // demo
        return orderItemBasicDto;
    }

    private ProductBasicDto productToBasicDto(Product entity) {
        ProductBasicDto productBasicDto = new ProductBasicDto();
        productBasicDto.setId(entity.getId());
        productBasicDto.setPricePerKey(entity.getPricePerKey());
        productBasicDto.setDiscountPercent(entity.getDiscountPercent());
        return productBasicDto;
    }

}

