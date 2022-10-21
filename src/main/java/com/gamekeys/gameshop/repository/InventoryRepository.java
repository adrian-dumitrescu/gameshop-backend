package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findById(Long id);

    Optional<Inventory> findByUserEmail(String userEmail);


}
