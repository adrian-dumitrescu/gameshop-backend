package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.CartItem;
import com.gamekeys.gameshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    Optional<CartItem> findCartItemByProductProductDetailsTitle(String productTitle);

    Optional<CartItem> findCartItemByShoppingCartUserEmailAndProductProductDetailsTitle(String userEmail, String productTitle);

    Optional<CartItem> findCartItemByShoppingCartUserEmailAndProduct(String userEmail, Product product);



}
