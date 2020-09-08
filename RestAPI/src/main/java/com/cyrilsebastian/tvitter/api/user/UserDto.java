package com.cyrilsebastian.tvitter.api.user;

import com.cyrilsebastian.tvitter.api.role.RoleDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDto implements Serializable {
    private UUID id;
    private String email;
    private String password;
    @ToString.Exclude @EqualsAndHashCode.Exclude private Set<RoleDto> roles;
}
