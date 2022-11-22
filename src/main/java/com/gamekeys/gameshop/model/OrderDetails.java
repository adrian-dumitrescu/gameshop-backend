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
@Table(name = "ORDER_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id", nullable = false, updatable = false)
    private Long id;

    private BigDecimal total = BigDecimal.valueOf(0);

    private Boolean withGuard;

    private String paymentOption;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk", nullable = false)
    //@JsonBackReference
    private AppUser user;

    @OneToMany(mappedBy = "orderDetails", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    //@JsonIgnore
    //@JsonManagedReference
    private Set<OrderItem> orderItems = new HashSet<>();
}
