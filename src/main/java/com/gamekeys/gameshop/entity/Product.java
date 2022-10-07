package com.gamekeys.gameshop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
//@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@EqualsAndHashCode
//@Table(name = "products")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, updatable = false)
    private Long id;

    private String productName;

    private String publisher;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "product")
    private Set<ActivationKey> activationKeys = new HashSet<>();


}
