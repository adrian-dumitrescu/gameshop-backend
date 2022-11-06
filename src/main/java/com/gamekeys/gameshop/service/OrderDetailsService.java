package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.OrderDetailsDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.exception.domain.NotEnoughStockException;
import com.gamekeys.gameshop.mapper.OrderDetailsMapper;
import com.gamekeys.gameshop.model.*;
import com.gamekeys.gameshop.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsMapper orderDetailsMapper;

    private final AppUserRepository appUserRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final ProductKeyRepository productKeyRepository;


    public OrderDetailsDto getAllUserOrders(String userEmail) {
        OrderDetails orderDetails = orderDetailsRepository.findOrderDetailsByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No order-details was found for user with email " + userEmail)));
        return orderDetailsMapper.convertToDto(orderDetails);
    }

    @Transactional
    public OrderDetailsDto createPurchaseOrder(String clientEmail) throws NotEnoughStockException {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserEmail(clientEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No shopping-cart with clientEmail " + clientEmail + " was found")));
        OrderDetails newOrderDetails = shoppingCartToOrderDetails(shoppingCart);

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Set<ProductKey> productKeys = cartItem.getProduct().getProductKeys();
            if (cartItem.getQuantity() <= productKeys.size()) {
                for (int i = 0; i < cartItem.getQuantity(); i++) {
                    if (productKeys.iterator().hasNext()) {
                        ProductKey productKey = productKeys.iterator().next();
                        productKeyRepository.delete(productKey);
                    }
                }
            } else {
                throw new NotEnoughStockException(String.format("There is not enough stock available for product: " + cartItem.getProduct().getProductDetails().getTitle()));
            }
        }
        orderDetailsRepository.save(newOrderDetails);
        AppUser appUser = shoppingCart.getUser();
        appUser.setShoppingCart(null);
        shoppingCartRepository.delete(shoppingCart);
        return orderDetailsMapper.convertToDto(newOrderDetails);
    }

    private OrderDetails shoppingCartToOrderDetails(ShoppingCart shoppingCart) {
        OrderDetails newOrderDetails = new OrderDetails();
        newOrderDetails.setTotal(shoppingCart.getTotal());
        newOrderDetails.setCreatedAt(LocalDateTime.now());
        newOrderDetails.setModifiedAt(LocalDateTime.now());
        newOrderDetails.setUser(shoppingCart.getUser());
        orderDetailsRepository.save(newOrderDetails);
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream().map(cartItem -> cartItemToOrderItem(cartItem, newOrderDetails)).collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);
        newOrderDetails.setOrderItems(orderItems);
        return newOrderDetails;
    }

    private OrderItem cartItemToOrderItem(CartItem entity, OrderDetails newOrderDetails) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(entity.getId());
        orderItem.setQuantity(entity.getQuantity());
        orderItem.setCreatedAt(entity.getCreatedAt());
        orderItem.setModifiedAt(entity.getModifiedAt());
        orderItem.setOrderDetails(newOrderDetails);
        orderItem.setProduct(entity.getProduct());
        return orderItem;
    }
}
