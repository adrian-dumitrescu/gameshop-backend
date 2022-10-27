package com.gamekeys.gameshop.model;

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
    private String activationKey;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "product_fk", nullable = false)
    //@JsonBackReference
    private Product product;




}
