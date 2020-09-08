package com.cyrilsebastian.tvitter.api.user;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Mapper(componentModel = "spring")
@Named("AddOnMapper")
public class PasswordMapper {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Named("HashPassword")
    public String hashPassword(String password) {return passwordEncoder.encode(password);}
}