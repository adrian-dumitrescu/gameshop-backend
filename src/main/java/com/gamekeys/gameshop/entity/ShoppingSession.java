package com.gamekeys.gameshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SHOPPING_SESSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_session_id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(mappedBy = "shoppingSession", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_fk")
    //@MapsId
    private AppUser user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "shoppingSession")
    private Set<CartItem> cartItems = new HashSet<>();
}
