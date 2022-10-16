package com.gamekeys.gameshop.controller;

import com.gamekeys.gameshop.dto.AppUserDto;
import com.gamekeys.gameshop.dto.LoginDto;
import com.gamekeys.gameshop.entity.AppUserDetails;
import com.gamekeys.gameshop.exception.ExceptionHandling;
import com.gamekeys.gameshop.exception.domain.CurrentPasswordException;
import com.gamekeys.gameshop.exception.domain.EmailExistException;
import com.gamekeys.gameshop.exception.domain.NotAnImageFileException;
import com.gamekeys.gameshop.exception.domain.UserNotFoundException;
import com.gamekeys.gameshop.misc.HttpResponse;
import com.gamekeys.gameshop.service.AppUserService;
import com.gamekeys.gameshop.token.JWTTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.gamekeys.gameshop.constant.FileConstant.*;
import static com.gamekeys.gameshop.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Slf4j
@RestController
//@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/api/user")
//@RequestMapping({"/api","/api/user"})
@CrossOrigin(origins = "http://localhost:4200/")
public class AppUserController extends ExceptionHandling {
    // HTTP REST API: GET, POST, PUT, DELETE

    private final AppUserService appUserService;
    private JWTTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @PostMapping("/register") //former create
    public ResponseEntity<AppUserDto> registerUser(@RequestBody @Valid AppUserDto appUserDto) throws UserNotFoundException, EmailExistException, MessagingException {
        AppUserDto newUser = appUserService.registerUser(appUserDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

//    @GetMapping("/login/{email}/{password}")
//    public ResponseEntity<AppUserDto> loginUser(@PathVariable("email") String email, @PathVariable("password") String password) {
//        authenticate(email, password);
//        // Get the userDetails in order to generate the JWT header
//        AppUserDetails appUserDetails = appUserService.getUserDetails(email);
//        HttpHeaders jwtHeader = getJwtHeader(appUserDetails);
////        AppUser appUser = appUserDetails.getAppUser();
////        Set<AppRole> userRole = appUser.getRoles();
//        AppUserDto loginUser = appUserService.findAppUserByEmail(email);
//        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
//    }

    @PostMapping("/login")
    public ResponseEntity<AppUserDto> loginUser(@RequestBody LoginDto loginDto) {
        authenticate(loginDto.getEmail(), loginDto.getPassword());
        // Get the userDetails in order to generate the JWT header
        AppUserDetails appUserDetails = appUserService.getUserDetails(loginDto.getEmail());
        HttpHeaders jwtHeader = getJwtHeader(appUserDetails);
//        AppUser appUser = appUserDetails.getAppUser();
//        Set<AppRole> userRole = appUser.getRoles();
        AppUserDto loginUser = appUserService.findAppUserByEmail(loginDto.getEmail());
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

//    @PostMapping("/create")
//    public ResponseEntity<AppUserDto> createNewUser(@RequestParam("firstName") String firstName,
//                                                 @RequestParam("lastName") String lastName,
//                                                 @RequestParam("username") String username,
//                                                 @RequestParam("email") String email,
//                                                 @RequestParam("role") String role,
//                                                 @RequestParam("isActive") String isActive,
//                                                 @RequestParam("isNonLocked") String isNonLocked) throws UserNotFoundException, EmailExistException, IOException {
//        AppUserDto newUser = appUserService.createNewUser(firstName, lastName, username, email, Role.valueOf(role), Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive));
//        return new ResponseEntity<>(newUser, OK);
//    }

    @PutMapping("/update")
    public ResponseEntity<AppUserDto> updateUser(@RequestParam("currentEmail") String currentEmail,
                                                 @RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName,
                                                 @RequestParam("email") String newEmail,
                                                 @RequestParam(value = "profileImage", required = false)
                                                 MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, NotAnImageFileException {
        AppUserDto updateUser = appUserService.updateUser(currentEmail, firstName, lastName, newEmail, profileImage);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @PutMapping("/update/card")
    public ResponseEntity<AppUserDto> updateUserCard(@RequestParam("email") String email,
                                                     @RequestParam("firstName") String firstName,
                                                     @RequestParam("lastName") String lastName,
                                                     @RequestParam("nickname") String nickname,
                                                     @RequestParam("gender") String gender,
                                                     @RequestParam("country") String country,
                                                     @RequestParam("age") Integer age) {
        AppUserDto updatedUser = appUserService.updateUserCard(email, firstName, lastName, nickname, gender, country, age);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/update/email")
    public ResponseEntity<AppUserDto> updateUserEmail(@RequestParam("currentEmail") String currentEmail,
                                                      @RequestParam("newEmail") String newEmail) throws UserNotFoundException, EmailExistException{
        AppUserDto updatedUser = appUserService.updateUserEmail(currentEmail, newEmail);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/update/password")
    public ResponseEntity<AppUserDto> updateUserPassword(@RequestParam("email") String email,
                                                         @RequestParam("currentPassword") String currentPassword,
                                                         @RequestParam("newPassword") String newPassword) throws CurrentPasswordException {
        AppUserDto updatedUser = appUserService.updateUserPassword(email, currentPassword, newPassword);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<AppUserDto> updateProfileImage(@RequestParam("email") String email, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, NotAnImageFileException {
        AppUserDto appUserDto = appUserService.updateProfileImage(email, profileImage);
        return new ResponseEntity<>(appUserDto, OK);
    }

    // This is when there gas been a selected profile picture added to the user
    // It's going into the path and gets the image
    @GetMapping(path = "/image/{email}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("email") String username, @PathVariable("fileName") String fileName) throws IOException {
        //"user.home" + "/gameshop/user/{email}/{email.jpg}
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    // When called, this method goes to https://robohash.org/{email} and takes the input steam
    // (jpg image) and then returns it as a response to us
    @GetMapping(path = "/image/profile/{email}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("email") String email) throws IOException {
        // https://robohash.org/{email}
        URL url = new URL(ROBOHASH_IMAGE_BASE_URL + email + SET_3_URL);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // From this stream that I opened, read that many bytes at a time
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            // While there are bytes to read
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        List<AppUserDto> users = appUserService.findAllUsers();
        log.info("Retrieving Users '{}' ", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable("id") Long id) {
        AppUserDto user = appUserService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        appUserService.deleteAppUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add/{activationKey}/{userEmail}/{productName}")
    public ResponseEntity<AppUserDto> addKeyForUser(@PathVariable("activationKey") String activationKey, @PathVariable("userEmail") String userEmail, @PathVariable("productName") String productName) {
        AppUserDto appUserDto = appUserService.addKeyForUser(activationKey, userEmail, productName);
        return new ResponseEntity<>(appUserDto, OK);
    }


    private void authenticate(String username, String password) {
        // We told the authentication manager how to fetch the user (by implementing the UserDetails interface
        // and told it to look in the database by the username)
        // This will trigger the loadUserByUsername method in userService
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authToken);
    }

    private HttpHeaders getJwtHeader(AppUserDetails userDetails) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userDetails));
        return headers;
    }

    private ResponseEntity<HttpResponse> response(String message, HttpStatus httpStatus) {
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message);
        return new ResponseEntity<>(body, httpStatus);
    }


}
