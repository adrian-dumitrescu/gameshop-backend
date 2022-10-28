package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    //Optional<ShoppingCart> findByUserEmail(String userEmail);
    Optional<ShoppingCart> findShoppingCartByUserEmail(String userEmail);

    void deleteShoppingCartByUserEmail(String userEmail);

}
