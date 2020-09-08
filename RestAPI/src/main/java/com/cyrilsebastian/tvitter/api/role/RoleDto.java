package com.cyrilsebastian.tvitter.api.role;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class RoleDto implements Serializable {
    private UUID id;
    private String name;
}
