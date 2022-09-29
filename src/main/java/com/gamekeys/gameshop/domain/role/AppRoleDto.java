package com.gamekeys.gameshop.domain.role;

import com.gamekeys.gameshop.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AppRoleDto {

    private Long id;

    private Role role;

}
