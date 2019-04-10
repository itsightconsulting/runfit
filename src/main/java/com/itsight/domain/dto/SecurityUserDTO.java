package com.itsight.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityUserDTO {

    private Integer id;

    private String username;

    private String password;

    private boolean enabled;

    private String roles;

    private String privileges;
}
