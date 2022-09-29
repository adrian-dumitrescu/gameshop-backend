package com.gamekeys.gameshop.listener;

import com.gamekeys.gameshop.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationFailureListener {
    private LoginAttemptService loginAttemptService;


//    @Autowired
//    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
//        this.loginAttemptService = loginAttemptService;
//    }

    // When BadCredentialEvent happens, this triggers
    @Async
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(username); // increment
        }

    }
}
