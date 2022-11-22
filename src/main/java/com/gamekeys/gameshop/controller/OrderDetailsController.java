package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.model.OrderDetailsDto;
import com.gamekeys.gameshop.exception.domain.NotEnoughStockException;
import com.gamekeys.gameshop.service.OrderDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/order-details")
@CrossOrigin(origins = "http://localhost:4200/")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @GetMapping("/all/{userEmail}")
    public ResponseEntity<OrderDetailsDto> getAllUserOrders(@PathVariable("userEmail") String userEmail) {
        OrderDetailsDto orderDetails = orderDetailsService.getAllUserOrders(userEmail);
        return new ResponseEntity<>(orderDetails, OK);
    }

//    @PostMapping("/purchase")
//    public ResponseEntity<OrderDetailsDto> createPurchaseOrder(@RequestParam("clientEmail") String clientEmail) throws NotEnoughStockException {
//        OrderDetailsDto orderDetails = orderDetailsService.createPurchaseOrder(clientEmail);
//        return new ResponseEntity<>(orderDetails, CREATED);
//    }

    @PostMapping("/purchase")
    public ResponseEntity<OrderDetailsDto> createPurchaseOrder(@RequestParam("clientEmail") String clientEmail,
                                                               @RequestParam("guardProtection") Boolean guardProtection,
                                                               @RequestParam("paymentOption") String paymentOption) throws NotEnoughStockException {
        OrderDetailsDto orderDetails = orderDetailsService.createPurchaseOrder(clientEmail, guardProtection, paymentOption);
        return new ResponseEntity<>(orderDetails, CREATED);
    }


}
