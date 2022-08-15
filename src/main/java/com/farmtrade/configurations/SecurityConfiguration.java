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
            "/swagger-ui/**",
            "/javainuse-openapi/**",
            "/webjars/**",
            "/configuration/**",
            "/swagger-resources",
            "/swagger-resources/configuration/*",
            "/v3/api-docs/**",
            "/images/**",
            "/error",
    };
    private static final String[] UNSECURE_URLS = {
            "/api/auth/login",
            "/api/auth/forgot-password",
            "/api/auth/reset-password/*",
            "/api/users/from-code/*",
            "/api/users/registration",
            "/api/users/activate",
            "/api/status"
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
                .antMatchers(UNSECURE_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(URLS_FOR_SWAGGER);
    }
}
