package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    Optional<CartItem> findCartItemByProductProductDetailsTitle(String productTitle);

    Optional<CartItem> findCartItemByProductUserEmailAndProductProductDetailsTitle(String userEmail, String productTitle);
}
