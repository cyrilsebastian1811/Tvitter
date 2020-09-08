package com.cyrilsebastian.tvitter.config.security;

import com.cyrilsebastian.tvitter.api.user.User;
import com.cyrilsebastian.tvitter.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if(user==null) new UsernameNotFoundException("User with email: "+email+" does not exist");
        return new UserDetailsImpl(user);
    }

    public boolean hasUserId(Authentication authentication, UUID id) {
        UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
        return user.getId().equals(id);
    }
}
