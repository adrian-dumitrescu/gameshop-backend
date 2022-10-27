package com.gamekeys.gameshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT") // former inventory
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private BigDecimal pricePerKey;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_fk", nullable = false)
    //@JsonBackReference
    private AppUser user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product", orphanRemoval = true)
    //@JsonIgnore
    //@JsonManagedReference
    private Set<ProductKey> productKeys = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_details_fk", nullable = false)
    //@JsonBackReference
    private ProductDetails productDetails;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_fk")
    //@MapsId
    private CartItem cartItem;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_fk")
    //@MapsId
    private OrderItem orderItem;

}

