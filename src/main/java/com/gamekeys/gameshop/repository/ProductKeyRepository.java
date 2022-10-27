package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.ProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProductKeyRepository extends JpaRepository<ProductKey, Long> {

    //Optional<List<ProductKey>> findAllByUserEmail(String email);

    //Optional<Set<ProductKey>> findAllByProductDetailsTitle(String productTitle);

//    void deleteAllInBatchByActivationKey(String activationKey);

    Optional<ProductKey> findProductKeyByActivationKey(String activationKey);

    void deleteProductKeyByActivationKey(String activationKey);

    void deleteByActivationKey(String activationKey);

    //Integer countByInventoryProductTitle(String productTitle); // maybe countByInventoryUserEmailAndProductTitle

}
