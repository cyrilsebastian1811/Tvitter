package com.cyrilsebastian.tvitter.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableWebSecurity(debug = false)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public BCryptPasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and()
                .logout().logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("SESSION");

        http.authorizeRequests()
//                .antMatchers("/**").permitAll();
                .antMatchers("/login").authenticated()
                .antMatchers(HttpMethod.POST, "/users", "/profiles").permitAll()
                .antMatchers("/users", "/profiles").hasAnyRole("USER", "ADMIN")
                .antMatchers("/users/{id}/**", "/profiles/{id}/**")
                .access("hasAnyRole('USER', 'ADMIN') and @userDetailsServiceImpl.hasUserId(authentication,#id)");
    }
}
