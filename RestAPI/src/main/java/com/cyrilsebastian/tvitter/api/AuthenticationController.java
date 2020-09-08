package com.cyrilsebastian.tvitter.api;

import com.cyrilsebastian.tvitter.api.profile.ProfileService;
import com.cyrilsebastian.tvitter.config.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping(consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthenticationController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/login")
    public String login(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return "{ \"id\": \""+userDetails.getId()+"\", \"email\": \""+userDetails.getEmail()+"\", \"login_success\" : true }";
    }

    @PostMapping("/logout")
    public String logout(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        profileService.logout(userDetails.getId());
        return "{ \"id\": \""+userDetails.getId()+"\", \"email\": \""+userDetails.getEmail()+"\", \"logout_success\" : true }";
    }
}
