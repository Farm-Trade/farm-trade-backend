package com.farmtrade.configurations;

import com.farmtrade.security.jwt.JwtConfiguration;
import com.farmtrade.security.jwt.JwtTokenFilter;
import com.farmtrade.security.jwt.JwtTokenProvider;
import com.twilio.jwt.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] URLS_FOR_SWAGGER = {
            "/index.jsp",
            "/swagger-ui.html",
            "/webjars/**",
            "/configuration/**",
            "/swagger-resources",
            "/swagger-resources/configuration/*",
            "/v2/api-docs",
            "/images/**",
            "/error"
    };
    private final JwtTokenProvider jwtTokenProvider;


    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/login", "/api/users/registration", "/api/users/activate").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(URLS_FOR_SWAGGER);
    }
}
