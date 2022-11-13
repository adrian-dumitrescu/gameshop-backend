package com.gamekeys.gameshop.token.filter;

import com.gamekeys.gameshop.constant.SecurityConstant;
import com.gamekeys.gameshop.token.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.gamekeys.gameshop.constant.SecurityConstant.TOKEN_PREFIX;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private JWTTokenProvider jwtTokenProvider;

    public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    // Check to make sure that the token is valid, the user is valid
    // then set that user as the authenticated user.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // When the request comes in, we make sure that it's not OPTIONS, if it is,
        // we set the response to OK because we are not doing anything
        if (request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)){
            // If it's option we are not doing anything, we just set the status to ok.
            // We are doing this because the option is seen before every request and is sent to gather information about the server
            // So when one of the clients sent a request to this server, is going to check the information that the server accepts
            // like things about the headers and what is acceptable.
            // "Hey server do you accept this specific thing?"
            response.setStatus(HttpStatus.OK.value());
            // Otherwise we try to get the authorization header.
        }else{
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            // If the header is null or if the header does not start with "Bearer "
            if(authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)){
                // We pass in the request and the response because we are not doing anything with the
                // request anymore.
                filterChain.doFilter(request,response);
                return;
            }
            // Remove the "Bearer " in front of the token
            String token = authorizationHeader.substring(TOKEN_PREFIX.length());
            // Once we have the token, we can attempt to get the username
            String username = jwtTokenProvider.getSubject(token);
            // The check in the security context holder is not needed since we are not using session. This part can be removed in the if condition
            //if(jwtTokenProvider.isTokenValid(username,token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(jwtTokenProvider.isTokenValid(username,token)) {
                List<GrantedAuthority> authorities = jwtTokenProvider.getTokenAuthorities(token);
                // Once we have the authorities, we can get an authentication
                Authentication authentication = jwtTokenProvider.getAuthentication(username,authorities,request);
                // We can set the authentication in the security context holder because we have checked and we did not find any for the specific user
                // Once we get the authentication, we set it in the security context holder as this user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                // If anything fails, we clear the context, we don't want anything to be lingering
                SecurityContextHolder.clearContext();
            }
        }
        // We let the request continue it's course
        filterChain.doFilter(request,response);
    }
}
