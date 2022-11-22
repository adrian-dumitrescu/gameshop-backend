package com.gamekeys.gameshop.configuration;

import com.gamekeys.gameshop.model.AppRole;
import com.gamekeys.gameshop.model.ProductDetails;
import com.gamekeys.gameshop.model.enums.ProductPublisher;
import com.gamekeys.gameshop.model.enums.ProductTitle;
import com.gamekeys.gameshop.model.enums.Role;
import com.gamekeys.gameshop.repository.*;
import com.gamekeys.gameshop.service.AppUserService;
import com.gamekeys.gameshop.service.ProductDetailsService;
import com.gamekeys.gameshop.service.ProductKeyService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
                          ShoppingCartRepository shoppingCartRepository,
                          ProductService productService) {

        return args -> {

            AppRole USER = new AppRole(null, Role.ROLE_USER);
            AppRole ADMIN = new AppRole(null, Role.ROLE_ADMIN);
            AppRole SUPER_ADMIN = new AppRole(null, Role.ROLE_SUPER_ADMIN);
            appRoleRepository.saveAll(List.of(SUPER_ADMIN, ADMIN, USER));


            //appUserService.createNewUser("Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!", Role.ROLE_SUPER_ADMIN,true,true);
            appUserService.createNewUser("Ioana", "Marin", "Kitzi", "ioana.marin@gmail.com", "ioanaA94!", Role.ROLE_SUPER_ADMIN, true, true);
            appUserService.createNewUser("Teodora", "Bucur", "corgY", "bucur.teodora@yahoo.com", "portocale98!", Role.ROLE_USER, true, true);
            appUserService.createNewUser("Angel", "Angel", "Loredon", "oprea.angel@outlook.com", "angel94!", Role.ROLE_USER, true, true);
            //appUserService.registerUser(new AppUserDto("Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!"));
            //appUserService.registerUser(new AppUserDto("Ioana", "Marin", "ioana.marin@gmail.com", "ioanaA94!"));

            ProductDetails overwatch_2 = new
                    ProductDetails(null,
                    ProductTitle.OVERWATCH_2.getTitle(),
                    "Overwatch 2 is the globe-spanning sequel to Blizzard Entertainment’s acclaimed team-based game, building upon the original’s battle-honed foundation and carrying forward everything players have earned into a new era of epic competition and team play.",
                    "Blood, Use of Tobacco, Mild Language, Violence",
                    LocalDate.of(2022, 10, 4),
                    "Shooter",
                    "PC, Xbox Series, Nintendo Switch, Xbox One, Playstation 4",
                    ProductPublisher.BLIZZARD_ENTERTAINMENT.getPublisher());

            ProductDetails diablo_4 = new
                    ProductDetails(null,
                    ProductTitle.DIABLO_4.getTitle(),
                    "The endless battle between the High Heavens and the Burning Hells rages on as chaos threatens to consume Sanctuary in Diablo IV, the fourth chapter of Blizzard's hellacious action-RPG saga.",
                    "Blood, Use of Tobacco, Mild Language, Violence",
                    LocalDate.of(2023, 3, 22),
                    "Action, RPG",
                    "PC, Xbox Series, Nintendo Switch, Xbox One, Playstation 4",
                    ProductPublisher.BLIZZARD_ENTERTAINMENT.getPublisher());

            ProductDetails dark_souls_3 = new
                    ProductDetails(null,
                    ProductTitle.DARK_SOULS_3.getTitle(),
                    "A dark and brooding fantasy adventure awaits players in a vast twisted world full of fearsome beasts, devious traps and hidden secrets. Dark Souls III once again gives gamers the trademark sword and sorcery combat and rewarding action RPG gameplay.",
                    "Blood, Violence, Online Interactions Not Rated by the ESRB",
                    LocalDate.of(2016, 4, 12),
                    "Action, RPG",
                    "PC, Xbox One, Playstation 4",
                    ProductPublisher.FROM_SOFTWARE.getPublisher());

            //wait(100);
            ProductDetails dying_light_2 = new
                    ProductDetails(null,
                    ProductTitle.DYING_LIGHT_2.getTitle(),
                    "The fate of a decaying City is in your hands. Every choice matters in Dying Light 2, the bold sequel to a best-selling open-world phenomenon.",
                    "Strong Language, Suggestive Themes, Blood and Gore, Intense Violence, Users Interact, In-Game Purchases",
                    LocalDate.of(2022, 2, 4),
                    "Action",
                    "PC, Xbox Series, Nintendo Switch, Xbox One, Playstation 4, Playstation 5",
                    ProductPublisher.TECHLAND.getPublisher());

            ProductDetails destiny_2 = new
                    ProductDetails(null,
                    ProductTitle.DESTINY_2.getTitle(),
                    "From the makers of the acclaimed hit game Destiny, comes the much-anticipated FPS sequel that takes you on an epic journey across the solar system.",
                    "Blood, Language, Violence, Users Interact, In-Game Purchases",
                    LocalDate.of(2017, 9, 6),
                    "Shooter",
                    "PC, Xbox Series, Stadia, Xbox One, Playstation 4, Playstation 5",
                    ProductPublisher.ACTIVISION.getPublisher());

            ProductDetails cyberpunk_2077 = new ProductDetails(null,
                    ProductTitle.CYBERPUNK_2077.getTitle(),
                    "Cyberpunk 2077 is a non-linear sci-fi RPG based on renowned pen-and-paper-RPG designer Mike Pondsmith's Cyberpunk system and created by CD Projekt, the acclaimed development group behind The Witcher.",
                    "Strong Language, Nudity, Blood and Gore, Strong Sexual Content, Use of Drugs and Alcohol, Intense Violence",
                    LocalDate.of(2022, 12, 10),
                    "RPG, Sci-Fi",
                    "PC, Xbox Series, Stadia, Xbox One, Playstation 4, Playstation 5",
                    ProductPublisher.CD_PROJEKT.getPublisher());

            ProductDetails little_nightmares = new
                    ProductDetails(null,
                    ProductTitle.LITTLE_NIGHTMARES.getTitle(),
                    "Little Nightmares is a third-person 3D action adventure game with stealth and exploration elements. A little girl named Six is kidnapped from her home and taken to work in The Maw -- a surreal underwater resort. When she is able to escape, Six takes a journey through the bizarre and unpredictable world.",
                    "Blood, Violence",
                    LocalDate.of(2022, 4, 27),
                    "Adventure",
                    "iPad, iPhone, Android, Stadia, Nintendo Switch, Playstation 4, PC, Xbox One",
                    ProductPublisher.BANDAI_NAMCO_ENTERTAINMENT.getPublisher());

            ProductDetails mortal_kombat_11 = new
                    ProductDetails(null,
                    ProductTitle.MORTAL_KOMBAT_11.getTitle(),
                    "Mortal Kombat is back and better than ever in the eleventh evolution of the iconic franchise. MK11 features a roster of new and returning Klassic Fighters engaged in deadly brawls and a cinematic story mode, which continues the epic Mortal Kombat saga over 25 years in the making.",
                    "Blood and Gore, Intense Violence, Strong Language, Users Interact, In-Game Purchases",
                    LocalDate.of(2019, 4, 23),
                    "Fighting",
                    "PC, Nintendo Switch, Xbox Series, Stadia, Xbox One, Playstation 4, Playstation 5",
                    ProductPublisher.NETHERREALM_STUDIOS.getPublisher());

            ProductDetails nfs_heat = new
                    ProductDetails(null,
                    ProductTitle.NFS_HEAT.getTitle(),
                    "Hustle by day and risk it all at night in Need for Speed Heat, a thrilling race experience that pits you against a city’s rogue police force as you battle your way into street racing’s elite.",
                    "Language, Mild Violence, Users Interact, In-Game Purchases",
                    LocalDate.of(2019, 11, 8),
                    "Racing",
                    "PC, Playstation 4, Xbox One",
                    ProductPublisher.ELECTRONIC_ARTS.getPublisher());

            ProductDetails no_mans_sky = new
                    ProductDetails(null,
                    ProductTitle.NO_MANS_SKY.getTitle(),
                    "No Man’s Sky is a game about exploration and survival, set in an infinite universe. You can fly seamlessly from the surface of a planet to another, and every star in the sky is a sun that you can visit. Where you’ll go and how fast you’ll make your way through this universe is up to you. It’s yours for the taking.",
                    "Animated Blood, Fantasy Violence, Online Interactions Not Rated by the ESRB",
                    LocalDate.of(2016, 8, 9),
                    "Action, Adventure, Shooter, Flight",
                    "PC, Nintendo Switch, Xbox Series, Xbox One, Playstation 4, Playstation 5",
                    ProductPublisher.HELLO_GAMES.getPublisher());

            ProductDetails the_witcher_3 = new
                    ProductDetails(null,
                    ProductTitle.THE_WITCHER_3.getTitle(),
                    "The climactic third game in the fantasy RPG series, The Witcher 3: Wild Hunt is a unique combination of a non-linear story and an open world -- a character-driven, non-linear story experience focused on player choice, tactical combat and a rich, living environment.",
                    "Intense Violence, Blood and Gore, Sexual Content, Use of Alcohol, Strong Language, Nudity",
                    LocalDate.of(2015, 5, 19),
                    "RPG",
                    "PC, Nintendo Switch, Xbox Series, Xbox One, Playstation 4, Playstation 5",
                    ProductPublisher.CD_PROJEKT.getPublisher());

            ProductDetails valorant = new
                    ProductDetails(null,
                    ProductTitle.VALORANT.getTitle(),
                    "Valorant is a tactical first-person shooter game in the works at Riot Games, the creative studio behind League of Legends.",
                    "Blood, Language, Violence, In-Game Purchases, Users Interact",
                    LocalDate.of(2022, 6, 2),
                    "RPG",
                    "PC",
                    ProductPublisher.RIOT_GAMES.getPublisher());

//            ProductDetails overwatch_2 = new ProductDetails(null, ProductTitle.OVERWATCH_2.getTitle(), ProductPublisher.BLIZZARD_ENTERTAINMENT.getPublisher());
//            ProductDetails diablo_4 = new ProductDetails(null, ProductTitle.DIABLO_4.getTitle(), ProductPublisher.BLIZZARD_ENTERTAINMENT.getPublisher());
//            ProductDetails dark_souls_3 = new ProductDetails(null, ProductTitle.DARK_SOULS_3.getTitle(), ProductPublisher.FROM_SOFTWARE.getPublisher());
//            ProductDetails dying_light_2 = new ProductDetails(null, ProductTitle.DYING_LIGHT_2.getTitle(), ProductPublisher.TECHLAND.getPublisher());
//            ProductDetails destiny_2 = new ProductDetails(null, ProductTitle.DESTINY_2.getTitle(), ProductPublisher.ACTIVISION.getPublisher());
//            ProductDetails cyberpunk_2077 = new ProductDetails(null, ProductTitle.CYBERPUNK_2077.getTitle(), ProductPublisher.CD_PROJEKT.getPublisher());
//            ProductDetails little_nightmares = new ProductDetails(null, ProductTitle.LITTLE_NIGHTMARES.getTitle(), ProductPublisher.BANDAI_NAMCO_ENTERTAINMENT.getPublisher());
//            ProductDetails mortal_kombat_11 = new ProductDetails(null, ProductTitle.MORTAL_KOMBAT_11.getTitle(), ProductPublisher.NETHERREALM_STUDIOS.getPublisher());
//            ProductDetails nfs_heat = new ProductDetails(null, ProductTitle.NFS_HEAT.getTitle(), ProductPublisher.ELECTRONIC_ARTS.getPublisher());
//            ProductDetails no_mans_sky = new ProductDetails(null, ProductTitle.NO_MANS_SKY.getTitle(), ProductPublisher.HELLO_GAMES.getPublisher());
//            ProductDetails the_witcher_3 = new ProductDetails(null, ProductTitle.THE_WITCHER_3.getTitle(), ProductPublisher.CD_PROJEKT.getPublisher());
//            ProductDetails valorant = new ProductDetails(null, ProductTitle.VALORANT.getTitle(), ProductPublisher.RIOT_GAMES.getPublisher());

            productDetailsRepository.saveAll(List.of(overwatch_2, diablo_4, dark_souls_3, dying_light_2, destiny_2,
                    cyberpunk_2077, little_nightmares, mortal_kombat_11, nfs_heat, no_mans_sky, the_witcher_3, valorant));

            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.OVERWATCH_2.getTitle(), 10);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.OVERWATCH_2.getTitle(), 10);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.OVERWATCH_2.getTitle(), 20);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.OVERWATCH_2.getTitle(), 20);

            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.DARK_SOULS_3.getTitle(), 15);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.DARK_SOULS_3.getTitle(), 15);

            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.CYBERPUNK_2077.getTitle(), 5);

            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.NFS_HEAT.getTitle(), 10);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.NFS_HEAT.getTitle(), 20);


            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.DIABLO_4.getTitle(), 20);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.DIABLO_4.getTitle(), 10);


            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.CYBERPUNK_2077.getTitle(), 5);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "oprea.angel@outlook.com", ProductTitle.CYBERPUNK_2077.getTitle(), 15);

            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.THE_WITCHER_3.getTitle(), 20);
            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.THE_WITCHER_3.getTitle(), 20);

            productService.addKeyToUserProduct(String.valueOf(UUID.randomUUID()), BigDecimal.valueOf(25), "bucur.teodora@yahoo.com", ProductTitle.VALORANT.getTitle(), 30);


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
