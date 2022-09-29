package com.gamekeys.gameshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.File;

import static com.gamekeys.gameshop.constant.FileConstant.USER_FOLDER;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GameShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameShopApplication.class, args);
        new File(USER_FOLDER).mkdirs(); // once the application starts it will create the folder
    }




        // The Spring Boot order of operations is: Front End -> Controller -> Service -> Repository -> DTO -> Entity
        // Transactional atunci cand se fac join-uri intre tabele


//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }



//            appUserService.saveRole(new AppUserRoleDto(null, Role.ROLE_USER));
//            appUserService.saveRole(new AppUserRoleDto(null, Role.ROLE_ADMIN));
//            AppUserRole ADMIN = new AppUserRole(null,Role.ROLE_ADMIN);
//            AppUserRole USER = new AppUserRole(null,Role.ROLE_USER);
//            AppUserRole USER2 = new AppUserRole(null,Role.ROLE_USER);
//            //appUserService.createUser(new AppUserDto(null, "Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!", new ArrayList<>(Arrays.asList(new AppUserRole(null,Role.ROLE_USER), new AppUserRole(null,Role.ROLE_ADMIN))), "", LocalDate.now(), false, false));
//            //appUserService.createUser(new AppUserDto(null, "Ioana", "Marin", "ioana.marin@gmail.com", "ioanaA94!", new ArrayList<>(Collections.singleton(new AppUserRole(null, Role.ROLE_USER))), "", LocalDate.now(), false, false));
//            appUserService.createUser(new AppUserDto(null, "Adrian", "Dumitrescu", "dumitrescu.adrian121@gmail.com", "adrianN94!", new ArrayList<>(Arrays.asList(ADMIN,USER)), "", LocalDate.now(), false, false));

}




//import lombok.Getter;
//        import lombok.NoArgsConstructor;
//        import lombok.Setter;
//
//        import javax.persistence.*;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "users")
//@NoArgsConstructor
//public class UserEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "designation")
//    private String designation;
//
//    @Column(name = "email")
//    private String email;
//
//
//
//}
//
//
//import com.example.code.entity.UserEntity;
//        import org.springframework.data.jpa.repository.JpaRepository;
//        import org.springframework.stereotype.Repository;
//
//        import java.util.List;
//        import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<UserEntity, Long> {
//
//    Optional<UserEntity> findByName(String name);
//
//    List<UserEntity> findByDesignation(String designation);
//
//    Optional<UserEntity> findByEmail(String email);
//}
//
//
//import java.util.Collection;
//
//public interface UserService {
//    void createUser(UserDTO userDTO);
//
//    Collection<UserDTO> getUsers(
//            String username,
//            String designation,
//            String email
//    );
//}
//
//
//import com.example.code.dto.UserDTO;
//        import com.example.code.entity.UserEntity;
//        import com.example.code.mapper.UserMapper;
//        import com.example.code.repository.UserRepository;
//        import lombok.RequiredArgsConstructor;
//        import org.apache.catalina.User;
//        import org.springframework.stereotype.Service;
//        import org.springframework.util.StringUtils;
//
//        import java.util.Collection;
//        import java.util.HashSet;
//        import java.util.Set;
//        import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public void createUser(UserDTO userDTO) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName(userDTO.getName());
//        userEntity.setDesignation(userDTO.getDesignation());
//        userEntity.setEmail(userDTO.getEmail());
//        userRepository.save(userEntity);
//    }
//
//    @Override
//    public Collection<UserDTO> getUsers(String username, String designation, String email) {
//        Set<UserDTO> userDTOS = new HashSet<>();
//        if( !username.isBlank() && !username.isEmpty() && userRepository.findByName(username).isPresent() ) {
//            userDTOS.add(UserMapper.toDto(
//                    userRepository.findByName(username).get()
//            ));
//        }
//        if(!designation.isBlank() && !designation.isEmpty()) {
//            userDTOS.addAll(
//                    userRepository.findByDesignation(designation)
//                            .stream()
//                            .map(UserMapper::toDto)
//                            .collect(Collectors.toSet())
//            );
//        }
//
//        if( !email.isBlank() &&
//                !email.isEmpty() &&
//                userRepository.findByEmail(email).isPresent() ) {
//            userDTOS.add(UserMapper.toDto(
//                    userRepository.findByEmail(email).get()
//            ));
//        }
//
//        return userDTOS;
//    }
//}
//
//
//import com.example.code.dto.UserDTO;
//        import com.example.code.entity.UserEntity;
//
//public class UserMapper {
//
//    public static UserDTO toDto(UserEntity entity) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setName(entity.getName());
//        userDTO.setDesignation(entity.getDesignation());
//        userDTO.setEmail(entity.getEmail());
//        return userDTO;
//    }
//}
//
//
//@RestController
//@RequestMapping("/test")
//@RequiredArgsConstructor
//public class TestController {
//
//    private final UserService userService;
//
//    @PostMapping
//    public ResponseEntity<String> createUser(@RequestBody final UserDTO userDTO) {
//        try {
//            userService.createUser(userDTO);
//        }catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Failure");
//        }
//        return ResponseEntity.ok("Success");
//    }
//
//    @GetMapping
//    public ResponseEntity<Collection<UserDTO>> getUsers(
//            @RequestParam(value = "name", required = false) String name,
//            @RequestParam(value = "designation", required = false) String designation,
//            @RequestParam(value = "email", required = false) String email
//    ) {
//        return ResponseEntity.ok(userService.getUsers(name, designation, email));
//    }
//}




