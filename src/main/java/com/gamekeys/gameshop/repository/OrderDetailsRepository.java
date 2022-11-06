package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    Optional<OrderDetails> findOrderDetailsByUserEmail(String userEmail);

}
