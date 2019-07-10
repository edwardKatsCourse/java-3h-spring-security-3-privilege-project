package com.forum.configuration;

import com.forum.entity.UserEntity;
import com.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String authorizationHeader = request.getHeader("Authorization");

        //Authorization - username

        if (authorizationHeader != null) {
            System.out.println("Security filter - auth header: " + authorizationHeader);

            //UserEntity
            UserEntity userEntity = userRepository.findByUsername(authorizationHeader);
            System.out.println("Security filter - User entity exists: " + (userEntity != null));

            if (userEntity != null) {

                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(
                        new SimpleGrantedAuthority(userEntity.getUserPrivilege().toString()) //REGULAR | ADMIN
                );


                Authentication key = new UsernamePasswordAuthenticationToken(
                        userEntity,
                        null,
                        grantedAuthorities //privileges
                );

                SecurityContextHolder.getContext().setAuthentication(key);

                //John Smith -> asjlkdn+ui_ui31buif3$#287f2b8f32b
                //asjlkdn+ui_ui31buif3$#287f2b8f32b -> asjlkdnasdasd232#4$187f2b8f32b
                //????????
                //asjlkdn+ui_ui31buif3$#287f2b8f32b -> John Smith

                //MD5
                //John Smith - asjlkdnasdasd232#4$187f2b8f32b
                //John Smith - asjlkdn+ui_ui31buif3$#287f2b8f32b
            }
        }

        filterChain.doFilter(request, response);
    }
}
