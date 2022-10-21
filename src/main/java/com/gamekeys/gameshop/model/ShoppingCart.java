package com.gamekeys.gameshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SHOPPING_CART")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id", nullable = false, updatable = false)
    private Long id;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @OneToOne(mappedBy = "shoppingCart", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_fk")
    //@MapsId
    private AppUser user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoppingCart")
    private Set<CartItem> cartItems = new HashSet<>();
}
