package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.OrderDetailsDto;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.exception.domain.NotEnoughStockException;
import com.gamekeys.gameshop.mapper.AppUserMapper;
import com.gamekeys.gameshop.mapper.OrderDetailsMapper;
import com.gamekeys.gameshop.model.*;
import com.gamekeys.gameshop.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gamekeys.gameshop.constant.FileConstant.GAMEKEY_GUARD_EUROS;

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

    private final CartItemRepository cartItemRepository;

    private final AppUserMapper appUserMapper;


    public OrderDetailsDto getAllUserOrders(String userEmail) {
        OrderDetails orderDetails = orderDetailsRepository.findOrderDetailsByUserEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No order-details was found for user with email " + userEmail)));
        return orderDetailsMapper.convertToDto(orderDetails);
    }

    @Transactional
    public OrderDetailsDto createPurchaseOrder(String clientEmail, Boolean guardProtection, String paymentOption) throws NotEnoughStockException {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserEmail(clientEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No shopping-cart with clientEmail " + clientEmail + " was found")));
        OrderDetails purchaseOrder = shoppingCartToOrderDetails(shoppingCart, guardProtection, paymentOption);

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Set<ProductKey> productKeys = cartItem.getProduct().getProductKeys();

            if (cartItem.getQuantity() <= productKeys.size()) {
                for (int i = 0; i < cartItem.getQuantity(); i++) {
                    if (productKeys.iterator().hasNext()) {
                        ProductKey productKey = productKeys.iterator().next();
                        productKeyRepository.delete(productKey);
                    }
                }
//                if(productKeys.size() - cartItem.getQuantity() == 0){
//                    productRepository.delete(cartItem.getProduct());
//                }
            } else {
                throw new NotEnoughStockException(String.format("There is not enough stock available for product: " + cartItem.getProduct().getProductDetails().getTitle()));
            }
        }
        //orderDetailsRepository.save(newOrderDetails);
        AppUser appUser = shoppingCart.getUser();
        appUser.setShoppingCart(null);
        shoppingCartRepository.delete(shoppingCart);

        //orderDetailsRepository.save(purchaseOrder);
        //return appUserMapper.convertToDto(appUserRepository.findAppUserByEmail(clientEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No User with clientEmail " + clientEmail + " was found"))));
        return orderDetailsMapper.convertToDto(purchaseOrder);
    }

    private OrderDetails shoppingCartToOrderDetails(ShoppingCart shoppingCart, Boolean guardProtection, String paymentOption) {
        OrderDetails newOrderDetails = new OrderDetails();
        newOrderDetails.setWithGuard(guardProtection);
        if(guardProtection) {
            newOrderDetails.setTotal(shoppingCart.getTotal().add(BigDecimal.valueOf(GAMEKEY_GUARD_EUROS)));
        }else {
            newOrderDetails.setTotal(shoppingCart.getTotal());
        }
        newOrderDetails.setPaymentOption(paymentOption);
        newOrderDetails.setCreatedAt(LocalDateTime.now());
        newOrderDetails.setModifiedAt(LocalDateTime.now());
        newOrderDetails.setUser(shoppingCart.getUser());
        orderDetailsRepository.save(newOrderDetails);
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream().map(cartItem -> cartItemToOrderItem(cartItem, newOrderDetails)).collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);
        newOrderDetails.setOrderItems(orderItems);
        orderDetailsRepository.save(newOrderDetails);
        return newOrderDetails;
    }

    private OrderItem cartItemToOrderItem(CartItem entity, OrderDetails newOrderDetails) {
        OrderItem orderItem = new OrderItem();
        //orderItem.setId(entity.getId()); // aici era problema
        orderItem.setQuantity(entity.getQuantity());
        orderItem.setCreatedAt(entity.getCreatedAt());
        orderItem.setModifiedAt(entity.getModifiedAt());
        orderItem.setOrderDetails(newOrderDetails);
        orderItem.setProduct(entity.getProduct());
        return orderItem;
    }
}
