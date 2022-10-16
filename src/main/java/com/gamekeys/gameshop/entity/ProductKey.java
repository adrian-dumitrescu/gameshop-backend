package com.gamekeys.gameshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

//@Data
//@EqualsAndHashCode
@Entity
@Table(name = "PRODUCT_KEY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductKey implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_key_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyValue;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "inventory_fk", nullable = false)
    //@JsonBackReference
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_details_fk", nullable = false)
    //@JsonBackReference
    private ProductDetails productDetails;

    @OneToOne(mappedBy = "productKey", cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_fk")
    //@MapsId
    private CartItem cartItem;

    @OneToOne(mappedBy = "productKey", cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_fk")
    //@MapsId
    private OrderItem orderItem;
}
