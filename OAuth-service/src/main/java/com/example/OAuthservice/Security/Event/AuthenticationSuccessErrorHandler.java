package com.example.OAuthservice.Security.Event;

import brave.Tracer;
import com.example.OAuthservice.Service.IUserService;
import com.example.commonslibrary.Entity.User;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private IUserService serv;

    @Autowired
    private Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        if(authentication.getDetails() instanceof WebAuthenticationDetails){
            return;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String message = "Success Login: " + userDetails.getUsername();

        System.out.println(message);
        log.info(message);

        User user = serv.findByUsername(authentication.getName());

        if(user.getAttemps() != null && user.getAttemps() > 0) {
            user.setAttemps(0);
            serv.updateUser(user, user.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

        String message  = "Error Login: " + exception.getMessage();
        log.error(message);
        System.out.println(message);

        try {
            StringBuilder errors = new StringBuilder();
            errors.append(message);

            User user = serv.findByUsername(authentication.getName());
            if(user.getAttemps() == null){
                user.setAttemps(0);
            }

            log.info("Intento actual nro: " + user.getAttemps());

            user.setAttemps(user.getAttemps() + 1);

            log.info("Incremento de intento nro: " + user.getAttemps());
            errors.append(" - Incremento de intento nro: " + user.getAttemps());

            if (user.getAttemps() >= 3){
                String errorMaxAttemps = String.format("Usuario %s deshabilitado por máximo deintentos", user.getUserName());
                log.error(String.format(errorMaxAttemps));
                errors.append(" - " + errorMaxAttemps);

                user.setEnabled(false);
            }

            serv.updateUser(user, user.getId());

            tracer.currentSpan().tag("error message", errors.toString());
        }
        catch (FeignException e){
            log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
        }
    }
}
