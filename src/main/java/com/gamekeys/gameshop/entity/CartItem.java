package com.gamekeys.gameshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CART_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "shopping_session_fk", nullable = false)
    private ShoppingSession shoppingSession;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_key_fk", referencedColumnName = "product_key_id")
    //@PrimaryKeyJoinColumn
    private ProductKey productKey;


}
