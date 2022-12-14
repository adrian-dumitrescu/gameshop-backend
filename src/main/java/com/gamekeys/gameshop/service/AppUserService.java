package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.dto.model.AppUserDto;
import com.gamekeys.gameshop.exception.domain.*;
import com.gamekeys.gameshop.mapper.AppUserMapper;
import com.gamekeys.gameshop.model.AppRole;
import com.gamekeys.gameshop.model.AppUser;
import com.gamekeys.gameshop.model.AppUserDetails;
import com.gamekeys.gameshop.model.enums.Role;
import com.gamekeys.gameshop.repository.AppRoleRepository;
import com.gamekeys.gameshop.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gamekeys.gameshop.constant.FileConstant.*;
import static com.gamekeys.gameshop.constant.UserImplConstant.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Slf4j
@Service
@AllArgsConstructor
@Qualifier("appUserDetailsService")
//@Transactional // Manage propagation
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    private final AppRoleRepository appRoleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final LoginAttemptService loginAttemptService;

    private final AppUserMapper appUserMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Get user from database
        Optional<AppUser> appUserOptional = appUserRepository.findAppUserByEmail(email);
        if (appUserOptional.isEmpty()) {
            //log.error("The user is: " + appUserOptional.get().getEmail());
            log.error(NO_USER_FOUND_BY_USERNAME + email);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + email);
        } else {
            AppUser appUser = appUserOptional.get();
            bruteForceAttackCheck(appUser);
            appUserRepository.save(appUser);
            log.info(FOUND_USER_BY_EMAIL + email);
            return new AppUserDetails(appUser);
        }
    }

    /**
     * Registers a new user and sets the role to USER role
     * Validates that there is no other user with that email. See {@link #validateNewUserEmail}
     *
     * @param appUserDto to extract the form details
     * @return the saved appUserDto coming from the database (only when no exceptions occur)
     * @throws UserNotFoundException if no user with this email exists
     * @throws EmailExistException   if the email already exists in the database
     * @info
     */
    @Transactional
    public AppUserDto registerUser(AppUserDto appUserDto) throws UserNotFoundException, EmailExistException, MessagingException {
        AppUser appUserEntity = appUserMapper.convertToEntity(appUserDto);
        AppRole userRole = appRoleRepository.findByRole(Role.ROLE_USER);

        validateNewUserEmail(EMPTY, appUserEntity.getEmail());
        log.info("Saving new user {} to the database", appUserEntity.getFirstName() + " " + appUserEntity.getLastName());
        appUserEntity.setPassword(passwordEncoder.encode(appUserEntity.getPassword()));
        appUserEntity.setJoinDate(LocalDate.now());
        appUserEntity.setIsEnabled(true);
        appUserEntity.setIsNotLocked(true);
        appUserEntity.setProfileImageUrl(getTemporaryProfileImageUrl(appUserEntity.getEmail()));
        appUserEntity.setRoles(Set.of(userRole));
        appUserRepository.save(appUserEntity);

        loginAttemptService.evictUserFromLoginAttemptCache(appUserEntity.getEmail()); // clears any possible brute forced attacks
        log.info("New user password: " + appUserEntity.getPassword());
        //emailService.sendNewPasswordEmail(firstName, password, email);
        return appUserMapper.convertToDto(appUserEntity);
    }

    // This method is for creating new users (mostly with CommandLineRunner
    public AppUserDto createNewUser(String firstName, String lastName, String nickname, String email, String password, Role role, boolean isNotLocked, boolean isEnabled) throws UserNotFoundException, EmailExistException, IOException {
        validateNewUserEmail(EMPTY, email);
        AppUser appUser = new AppUser();
        AppRole userRole = appRoleRepository.findByRole(role);
        //userRole.setRole(role);

        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setNickname(nickname);
        appUser.setJoinDate(LocalDate.now());
        appUser.setEmail(email);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setIsEnabled(isEnabled);
        appUser.setIsNotLocked(isNotLocked);
        //appUser.setRoles((Set<AppRole>) appRoleRepository.findByRole(Role.ROLE_USER));
        appUser.setRoles(Set.of(userRole));
        appUser.setProfileImageUrl(getTemporaryProfileImageUrl(email));
        //appUser.setShoppingSession(shoppingSession);
        appUserRepository.save(appUser);

        //saveProfileImage(appUser, profileImage);

        return appUserMapper.convertToDto(appUser);
    }

    public AppUserDto updateUser(String currentEmail, String newFirstName, String newLastName, String newEmail, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, NotAnImageFileException {
        AppUser appUser = validateNewUserEmail(currentEmail, newEmail);
        appUser.setFirstName(newFirstName);
        appUser.setLastName(newLastName);
        appUser.setEmail(newEmail);
        appUserRepository.save(appUser);
        saveProfileImage(appUser, profileImage);
        return appUserMapper.convertToDto(appUser);
    }

    @Transactional
    public AppUserDto updateUserCard(String userEmail, String newFirstName, String newLastName, String newNickname, String gender, String country, Integer age) {
        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));
        appUser.setFirstName(newFirstName);
        appUser.setLastName(newLastName);
        appUser.setNickname(newNickname);
        appUser.setGender(gender);
        appUser.setCountry(country);
        appUser.setAge(age);
        appUserRepository.save(appUser);
        return appUserMapper.convertToDto(appUser);
    }

    public AppUserDto updateUserEmail(String currentEmail, String newEmail) throws UserNotFoundException, EmailExistException {
        AppUser appUser = validateNewUserEmail(currentEmail, newEmail);
        appUser.setEmail(newEmail);
        appUserRepository.save(appUser);
        return appUserMapper.convertToDto(appUser);
    }

    public AppUserDto updateUserPassword(String userEmail, String currentPassword, String newPassword) throws CurrentPasswordException {
        AppUser appUser = appUserRepository.findAppUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + userEmail + " was found")));
        if (passwordEncoder.matches(currentPassword, appUser.getPassword())) {
            appUser.setPassword(passwordEncoder.encode(newPassword));
            appUserRepository.save(appUser);
        } else {
            throw new CurrentPasswordException(CURRENT_PASSWORD_DOES_NOT_MATCH);
        }
        return appUserMapper.convertToDto(appUser);
    }

    private void saveProfileImage(AppUser user, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(USER_FOLDER + user.getEmail()).toAbsolutePath().normalize();
            if (!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                log.info(DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getEmail() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getEmail() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(buildProfileImageUrl(user.getEmail()));
            appUserRepository.save(user);
            log.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String buildProfileImageUrl(String email) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + email + FORWARD_SLASH
                + email + DOT + JPG_EXTENSION).toUriString();
    }

    public AppUserDto updateProfileImage(String email, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, NotAnImageFileException {
        AppUser appUser = validateNewUserEmail(email, EMPTY);
        saveProfileImage(appUser, profileImage);
        return appUserMapper.convertToDto(appUser);
    }

    private String getTemporaryProfileImageUrl(String email) {
        return DEFAULT_USER_IMAGE_PATH + email;
        //return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + email).toUriString(); // does not work when using CommandLineRunner as we don't have a context path
    }

    public AppUserDto findAppUserByEmail(String email) {
        //AppUser appUser = appUserRepository.findAppUserByEmailAndPassword(email, password).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + email + " and " + password + " was found")));
        AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + email + " was found")));
        return appUserMapper.convertToDto(appUser);
    }


    public List<AppUserDto> findAllUsers() {
        return appUserRepository.findAll().stream().map(c -> appUserMapper.convertToDto(c)).collect(Collectors.toList());
    }



    public AppUserDto findUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No user with id " + id + " was found")));
        return appUserMapper.convertToDto(appUser);
    }


    public void deleteAppUserById(Long id) {
        //AppUser appUser = appUserRepository.findAppUserById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Specified user does not exist", id)));
        //Set<AppRole> appRoles = appUser.getRoles();

        //appUserRepository.deleteAllById(Collections.singleton(id));
        //appRoleRepository.deleteById();
        appUserRepository.deleteAppUserById(id);

    }


    public boolean checkIfUserExists(String email) {
        return appUserRepository.existsAppUserByEmail(email);
    }

    private void bruteForceAttackCheck(AppUser appUser) {
        // If the account is not locked (false)
        if (appUser.getIsNotLocked()) {
            // If the user attempted login more than 5 times
            if (loginAttemptService.hasExceededMaxAttempts(appUser.getEmail())) {
                appUser.setIsNotLocked(false); // we lock the account
            } else {
                appUser.setIsNotLocked(true); // set to not locked
            }
            // if the account is locked
        } else {
            // If the user was ever in the cache, we remove it
            loginAttemptService.evictUserFromLoginAttemptCache(appUser.getEmail());
        }
    }

    private AppUser validateNewUserEmail(String currentEmail, String newEmail) throws UserNotFoundException, EmailExistException {

        Optional<AppUser> userWithNewEmail = appUserRepository.findAppUserByEmail(newEmail);

        // If we want to update the current Email/User
        if (StringUtils.isNotBlank(currentEmail)) {
            Optional<AppUser> currentUser = appUserRepository.findAppUserByEmail(currentEmail);

            if (currentUser.isEmpty()) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_EMAIL + currentEmail);
            }
            if (userWithNewEmail.isPresent() && !currentUser.get().getId().equals(userWithNewEmail.get().getId())) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser.get();
        } else {
            if (userWithNewEmail.isPresent()) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    public AppUserDetails getUserDetails(String email) {
        AppUser loginUser = appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + email + " was found")));
        return new AppUserDetails(loginUser);
    }


}
