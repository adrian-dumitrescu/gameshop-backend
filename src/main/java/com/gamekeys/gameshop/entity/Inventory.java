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
@Table(name = "INVENTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "inventory")
    //@JsonIgnore
    //@JsonManagedReference
    private Set<ProductKey> productKeys = new HashSet<>();
}

