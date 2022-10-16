package com.gamekeys.gameshop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


//@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
//@EqualsAndHashCode
@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @Enumerated(EnumType.STRING)
//    private Role appUserRole = ROLE_USER;


    //    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "dd-MM-yyyy")
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate joinDate;

    private Boolean isNotLocked;

    private Boolean isEnabled;

    // USER CARD:

    private String profileImageUrl;

    private String nickname;

    private String country;

    private String gender;

    private Integer age;

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_fk"),
            inverseJoinColumns = @JoinColumn(name = "role_fk")
    )
    private Set<AppRole> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_session_fk", referencedColumnName = "shopping_session_id")
    //@PrimaryKeyJoinColumn
    private ShoppingSession shoppingSession;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_fk", referencedColumnName = "inventory_id")
    //@PrimaryKeyJoinColumn
    private Inventory inventory;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    //@JsonIgnore
    //@JsonManagedReference
    private Set<OrderDetails> orderDetails = new HashSet<>();




//  @Enumerated(EnumType.STRING)

//  private String appUserRole = String.valueOf(Role.ROLE_USER);


//    @ManyToMany(fetch = EAGER)
//    private Collection<Role> roles = new ArrayList<>();

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
////        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
////        return Collections.singletonList(authority);
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        stream(roles).forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
//        });
//        return authorities;
    //return roles.stream().map(roles -> new SimpleGrantedAuthority(roles.getRole().toString())).collect(Collectors.toList());

//        return authorities;
    //SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.appUserRole.toString());
    //return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    //return Collections.singleton(new SimpleGrantedAuthority(this.appUserRole.toString()));
//    }


}


// If you let Hibernate generate the database schema, which you should not do for a production system,
// there is no difference between @@Column(nullable = false) and @NotNull. You should always use the @NotNull annotation,
// which is defined by the BeanValidation specification. It configures a validation step that gets performed before Hibernate
// executes the SQL statement. It might look like JPA’s @Column annotation achieves the same result, but that’s only
// the case if Hibernate generates the table definition and adds the not null constraint. Database constraints are an
// important tool to ensure data consistency, but you shouldn’t rely on them in your Java application.
// @Column(nullable = false) is for Hibernate
// @NotNull is for Spring Boot
