package com.gamekeys.gameshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDER_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false, updatable = false)
    private Long id;

    private Integer quantity = 0;

    private String sellerEmail;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_details_fk", nullable = false)
    //@JsonBackReference
    private OrderDetails orderDetails;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_fk", referencedColumnName = "product_id")
    //@PrimaryKeyJoinColumn
    private Product product;
}
