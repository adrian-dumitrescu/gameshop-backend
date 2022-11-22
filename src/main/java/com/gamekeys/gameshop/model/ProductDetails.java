package com.gamekeys.gameshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@Data
//@EqualsAndHashCode
@Entity
@Table(name = "PRODUCT_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_details_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    //@Enumerated(EnumType.STRING)
    private String title;

    // Product Description Fields:
    @Column(length = 350)
    private String summary;

    private String contentRating;

    private LocalDate initialRelease;

    private String genres;

    private String platforms;

    @Column(nullable = false)
    //@Enumerated(EnumType.STRING)
    private String publisher;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "productDetails")
    //@JsonManagedReference
    //@JsonIgnore
    private Set<Product> products = new HashSet<>();



    public ProductDetails(Long id, String title, String publisher) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
    }

    public ProductDetails(Long id, String title, String summary, String contentRating, LocalDate initialRelease, String genres, String platforms, String publisher) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.contentRating = contentRating;
        this.initialRelease = initialRelease;
        this.genres = genres;
        this.platforms = platforms;
        this.publisher = publisher;
    }
}
