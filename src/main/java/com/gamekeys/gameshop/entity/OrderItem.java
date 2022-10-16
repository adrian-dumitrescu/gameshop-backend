package com.gamekeys.gameshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OREDER_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_details_fk", nullable = false)
    //@JsonBackReference
    private OrderDetails orderDetails;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_key_fk", referencedColumnName = "product_key_id")
    //@PrimaryKeyJoinColumn
    private ProductKey productKey;
}
