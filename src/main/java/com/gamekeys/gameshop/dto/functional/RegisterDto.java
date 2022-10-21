package com.gamekeys.gameshop.dto.functional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This replaces the @Getter @Setter @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String firstName;

    private String lastName;

}
