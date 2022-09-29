package com.gamekeys.gameshop.listener;

import com.gamekeys.gameshop.domain.user.AppUserDetails;
import com.gamekeys.gameshop.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationSuccessListener {
    private LoginAttemptService loginAttemptService;


//    @Autowired
//    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
//        this.loginAttemptService = loginAttemptService;
//    }

    @Async
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof AppUserDetails) {
            AppUserDetails user = (AppUserDetails) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
