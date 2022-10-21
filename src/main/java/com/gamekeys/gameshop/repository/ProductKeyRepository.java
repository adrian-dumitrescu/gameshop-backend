package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.ProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface ProductKeyRepository extends JpaRepository<ProductKey, Long> {

    //Optional<List<ProductKey>> findAllByUserEmail(String email);

    Optional<Set<ProductKey>> findAllByProductDetailsTitle(String productTitle);

    void deleteProductKeyByActivationKey(String activationKey);

}
