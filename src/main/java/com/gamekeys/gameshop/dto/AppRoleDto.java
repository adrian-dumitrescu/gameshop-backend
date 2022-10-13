package com.gamekeys.gameshop.dto;

import com.gamekeys.gameshop.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class AppRoleDto {

    private Long id;

    private Role role;

}
