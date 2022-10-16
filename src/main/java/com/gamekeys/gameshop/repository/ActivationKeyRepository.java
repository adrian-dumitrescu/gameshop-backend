package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.entity.ProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ActivationKeyRepository extends JpaRepository<ProductKey, Long> {

    //Optional<List<ActivationKey>> findAllByUserEmail(String email);
    //List<ProductKey> findAllByUserEmail(String email);

}
