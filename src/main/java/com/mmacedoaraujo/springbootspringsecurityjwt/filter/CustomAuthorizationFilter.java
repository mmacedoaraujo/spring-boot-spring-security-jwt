package com.mmacedoaraujo.springbootspringsecurityjwt.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    //Excluding the string bearer from the beginning of the token
                    String token = authorizationHeader.substring("Bearer ".length());
                    //Instantiating the algorithm used to generate the token, but now we will use it to decode
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    //Here we assign it
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    //Now we are decoding it and assigning to a variable
                    DecodedJWT decodedJWT = verifier.verify(token);
                    //Here we extract the username for later use
                    String username = decodedJWT.getSubject();
                    //Getting the roles and storing them into a array
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    //Here we convert the roles to SimpleGrantedAuthority for Spring Security be able to recognize it
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    /*This is the line where we tell spring security all the roles from a user,
                     and determine what they can access*/
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {

                }

            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
