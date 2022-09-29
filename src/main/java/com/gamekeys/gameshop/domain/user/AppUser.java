package com.gamekeys.gameshop.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gamekeys.gameshop.domain.role.AppRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Validated
//@Data // This replaces the @Getter @Setter @NoArgsConstructor
//@Getter
//@Setter
@Data
@Entity
//@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@EqualsAndHashCode
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements Serializable {
    //    @SequenceGenerator(
//            name = "user_sequence",
//            sequenceName = "user_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "user_sequence"
//    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @Enumerated(EnumType.STRING)
//    private Role appUserRole = ROLE_USER;

    private String profileImageUrl;

    private Date joinDate;

    private Boolean isNotLocked;

    private Boolean isEnabled;

//    public boolean isNotLocked() {
//        return isLocked;
//    }

//    public boolean isEnabled() {
//        return isEnabled;
//    }

    // ONE USER CAN HAVE MULTIPLE ROLES -> ONE TO MANY
    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<AppRole> roles = new HashSet<>();


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
