package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    Optional<Set<Product>> findAllByUserEmail(String userEmail);

    //Optional<Product> findByUserEmailAndProductTitle(String userEmail, String productTitle);
    //Optional<Product> findInventoryByUserEmailAndProductTitle(String userEmail, String productTitle);

    Optional<Product> findProductByUserEmailAndProductDetailsTitle(String userEmail, String productTitle);
}
