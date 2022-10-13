package com.gamekeys.gameshop.configuration;

import com.gamekeys.gameshop.entity.ActivationKey;
import com.gamekeys.gameshop.entity.AppRole;
import com.gamekeys.gameshop.entity.Product;
import com.gamekeys.gameshop.entity.enums.Role;
import com.gamekeys.gameshop.repository.ActivationKeyRepository;
import com.gamekeys.gameshop.repository.AppRoleRepository;
import com.gamekeys.gameshop.repository.AppUserRepository;
import com.gamekeys.gameshop.repository.ProductRepository;
import com.gamekeys.gameshop.service.ActivationKeyService;
import com.gamekeys.gameshop.service.AppUserService;
import com.gamekeys.gameshop.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class BeanConfiguration {

    @Bean
    CommandLineRunner run(AppUserService appUserService,
                          AppUserRepository appUserRepository,
                          AppRoleRepository appRoleRepository,
                          ActivationKeyService activationKeyService,
                          ActivationKeyRepository activationKeyRepository,
                          ProductRepository productRepository,
                          ProductService productService) {

        return args -> {

            AppRole USER = new AppRole(null, Role.ROLE_USER);
            AppRole ADMIN = new AppRole(null, Role.ROLE_ADMIN);
            AppRole SUPER_ADMIN = new AppRole(null, Role.ROLE_SUPER_ADMIN);
            appRoleRepository.saveAll(List.of(SUPER_ADMIN, ADMIN, USER));

            //appUserService.createNewUser("Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!", Role.ROLE_SUPER_ADMIN,true,true);
            appUserService.createNewUser("Ioana", "Marin", "ioana.marin@gmail.com", "ioanaA94!", Role.ROLE_SUPER_ADMIN, true, true);
            //appUserService.registerUser(new AppUserDto("Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!"));
            //appUserService.registerUser(new AppUserDto("Ioana", "Marin", "ioana.marin@gmail.com", "ioanaA94!"));
            Product overwatch = new Product(null, "Overwatch", "Blizzard Entertainment");
            productRepository.save(overwatch);

            ActivationKey activationKey = new ActivationKey();
            activationKey.setKeyValue("231412412");
            activationKey.setUser(appUserRepository.getReferenceById(1L));
            activationKey.setProduct(productRepository.getReferenceById(1L));
            activationKeyRepository.save(activationKey);
        };
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
//                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
//        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
