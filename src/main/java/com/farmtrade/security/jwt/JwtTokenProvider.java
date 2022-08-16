package com.farmtrade.security.jwt;

import com.farmtrade.entities.enums.Role;
import com.farmtrade.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@PropertySource("application.yaml")
public class JwtTokenProvider {

    @Value("${spring.security.jwt.token.secret}")
    private String secret;

    @Value("${spring.security.jwt.token.expired}")
    private long validateInMilliseconds;

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtTokenProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    protected void init() {secret = Base64.getEncoder().encodeToString(secret.getBytes());}

    public String createToken(String phone, List<Role> role){
        Claims claims = Jwts.claims().setSubject(phone);
        claims.put("roles", getRoleNames(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validateInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getPhone(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }

    public String getPhone(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null){ //&& bearerToken.startsWith("Bearer_")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getAuthority());
        });

        return result;
    }
}
