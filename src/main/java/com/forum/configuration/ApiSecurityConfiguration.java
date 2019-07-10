package com.forum.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disable HTML validation
        http.csrf().disable()
        //disable spring security session management
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
        // Spring Security: ON
            .authorizeRequests()
                //Role      -> ADMIN (ROLE_ADMIN)
                //Authority -> ADMIN (ADMIN)

                //authority | role = authenticated + role | authority
                .antMatchers("/users/*/promote").hasAuthority("ADMIN")  //ADMIN
                .antMatchers("/users/*/demote").hasAuthority("ADMIN")   //ADMIN
                .antMatchers("/users/all").hasAnyAuthority("REGULAR", "ADMIN")              //any authenticated user
                .antMatchers("/users/**").permitAll();

        http.addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
