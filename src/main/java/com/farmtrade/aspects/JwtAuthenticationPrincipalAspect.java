package com.farmtrade.aspects;

import com.farmtrade.entities.User;
import com.farmtrade.exceptions.UnauthorizedException;
import com.farmtrade.repositories.UserRepository;
import com.farmtrade.services.interfaces.AuthService;
import com.farmtrade.services.security.AuthServiceImp;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JwtAuthenticationPrincipalAspect {
    private final AuthService authService;
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationPrincipalAspect.class);

    public JwtAuthenticationPrincipalAspect(AuthService authService) {
        this.authService = authService;
    }

    @Pointcut("@annotation(com.farmtrade.aspects.anotations.JwtAuthenticationPrincipal)")
    public User getUserFromContext() {
        try {
            return authService.getUserFromContext();
        } catch (UsernameNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new UnauthorizedException();
        }
    }
}
