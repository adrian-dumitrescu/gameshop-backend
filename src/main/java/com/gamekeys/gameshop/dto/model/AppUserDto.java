package com.gamekeys.gameshop.dto.model;

import com.gamekeys.gameshop.dto.functional.basic.ProductBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.OrderDetailsBasicDto;
import com.gamekeys.gameshop.dto.functional.basic.ShoppingCartBasicDto;
import com.gamekeys.gameshop.model.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private Long id;
    //@NotEmpty
    private String firstName;
    //@NotEmpty
    private String lastName;
    //@Pattern
    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    //@NotEmpty(message = "Email cannot be empty")
    private String email;
    //@NotEmpty
    private String password;

    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate joinDate;

    private Boolean isNotLocked;

    private Boolean isEnabled;

    // USER CARD:
    private String profileImageUrl;

    private String nickname;

    private String country;

    private String gender;

    private Integer age;

    private Set<AppRole> roles;

    private ShoppingCartBasicDto shoppingCart;

    private Set<OrderDetailsBasicDto> orderDetails;

    private Set<ProductBasicDto> products;

}

// NotNull does not work on DTO's
// Use NotEmpty
