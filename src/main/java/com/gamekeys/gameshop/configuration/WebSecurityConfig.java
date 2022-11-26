package com.gamekeys.gameshop.configuration;

import com.gamekeys.gameshop.token.filter.JWTAccessDeniedHandler;
import com.gamekeys.gameshop.token.filter.JWTAuthenticationEntryPoint;
import com.gamekeys.gameshop.token.filter.JWTAuthorizationFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.gamekeys.gameshop.constant.SecurityConstant.PUBLIC_URLS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Data
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // enables to have security at a method level
public class WebSecurityConfig {
    private JWTAuthorizationFilter jwtAuthorizationFilter;
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter,
                                 JWTAccessDeniedHandler jwtAccessDeniedHandler,
                                 JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("appUserDetailsService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);

        // Here we pass in everything that we want Spring Security to manage in terms of endpoint or URL.
        // Which one to secure/leave open
        // First we tell to not use Cross Site Request Forgery because we are not using it
        // We add Cross Origin resource Sharing because we don't want anyone from any URL to be able to connect to our API
        // We want ony a specific list to be able to do that. Disabling it will allow anyone from any domain to send a request to our application
        // and the application will try to process it.
        http.csrf().disable().cors()
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS) // With JWT, we don't need to keep track of who is logged in, so we can set STATELESS instead of STATEFUL
                .and()
                //.authorizeRequests().antMatchers("/api/**").permitAll().and() // just let everything pass through
                .authorizeRequests().antMatchers("/api/user/create", "/api/user/update", "/api/user/delete/**").hasRole("SUPER_ADMIN").and()
                .authorizeRequests().antMatchers(PUBLIC_URLS).permitAll() // We are telling the app the list of URLs that we are allowing the user to access without being authenticated
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
