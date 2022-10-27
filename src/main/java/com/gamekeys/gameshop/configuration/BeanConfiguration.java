package com.gamekeys.gameshop.configuration;

import com.gamekeys.gameshop.model.AppRole;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.model.enums.ProductPublisher;
import com.gamekeys.gameshop.model.enums.ProductTitle;
import com.gamekeys.gameshop.model.enums.Role;
import com.gamekeys.gameshop.repository.*;
import com.gamekeys.gameshop.service.AppUserService;
import com.gamekeys.gameshop.service.ProductKeyService;
import com.gamekeys.gameshop.service.ProductDetailsService;
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
                          ProductKeyService productKeyService,
                          ProductKeyRepository productKeyRepository,
                          ProductDetailsRepository productDetailsRepository,
                          ProductDetailsService productDetailsService,
                          ShoppingCartRepository shoppingCartRepository) {

        return args -> {

            AppRole USER = new AppRole(null, Role.ROLE_USER);
            AppRole ADMIN = new AppRole(null, Role.ROLE_ADMIN);
            AppRole SUPER_ADMIN = new AppRole(null, Role.ROLE_SUPER_ADMIN);
            appRoleRepository.saveAll(List.of(SUPER_ADMIN, ADMIN, USER));


            //appUserService.createNewUser("Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!", Role.ROLE_SUPER_ADMIN,true,true);
            appUserService.createNewUser("Ioana", "Marin", "ioana.marin@gmail.com", "ioanaA94!", Role.ROLE_SUPER_ADMIN, true, true);
            //appUserService.registerUser(new AppUserDto("Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!"));
            //appUserService.registerUser(new AppUserDto("Ioana", "Marin", "ioana.marin@gmail.com", "ioanaA94!"));
            ProductDetails overwatch2 = new ProductDetails(null, ProductTitle.OVERWATCH_2.getTitle(), ProductPublisher.BLIZZARD_ENTERTAINMENT.getPublisher());
            ProductDetails diablo4 = new ProductDetails(null, ProductTitle.DIABLO_4.getTitle(), ProductPublisher.BLIZZARD_ENTERTAINMENT.getPublisher());
            ProductDetails ds3 = new ProductDetails(null, ProductTitle.DARK_SOULS_3.getTitle(), ProductPublisher.FROM_SOFTWARE.getPublisher());
            ProductDetails dyingLight2 = new ProductDetails(null, ProductTitle.DYING_LIGHT_2.getTitle(), ProductPublisher.TECHLAND.getPublisher());
            ProductDetails destiny2 = new ProductDetails(null, ProductTitle.DESTINY_2.getTitle(), ProductPublisher.ACTIVISION.getPublisher());

            productDetailsRepository.saveAll(List.of(overwatch2, diablo4, ds3, dyingLight2, destiny2));

//            ShoppingCart ioanaShoppingCart = new ShoppingCart();
//            ioanaShoppingCart.setUser(appUserRepository.getReferenceById(1L));
//            shoppingCartRepository.save(ioanaShoppingCart);

//            ProductKey productKey = new ProductKey();
//            productKey.setKeyValue("231412412");
//            productKey.setUser(appUserRepository.getReferenceById(1L));
//            productKey.setProductDetails(productRepository.getReferenceById(1L));
//            activationKeyRepository.save(productKey);
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
