package com.cyrilsebastian.tvitter.api.profile;

import com.cyrilsebastian.tvitter.api.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ProfileDto implements Serializable {
    private UUID id;
    private String about;
    private char gender;
    private String interests;
    private String languages;
    private float latitude;
    private float longitude;
    @ToString.Exclude @EqualsAndHashCode.Exclude private UserDto user;
    private int postCount=0;
}
