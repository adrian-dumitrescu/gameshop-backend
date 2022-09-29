package com.gamekeys.gameshop.service;

import com.gamekeys.gameshop.domain.enums.Role;
import com.gamekeys.gameshop.domain.role.AppRole;
import com.gamekeys.gameshop.domain.user.AppUser;
import com.gamekeys.gameshop.domain.user.AppUserDetails;
import com.gamekeys.gameshop.domain.user.AppUserDto;
import com.gamekeys.gameshop.exception.domain.EmailExistException;
import com.gamekeys.gameshop.exception.domain.EntityNotFoundException;
import com.gamekeys.gameshop.exception.domain.NotAnImageFileException;
import com.gamekeys.gameshop.exception.domain.UserNotFoundException;
import com.gamekeys.gameshop.mapper.AppUserMapper;
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

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gamekeys.gameshop.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static com.gamekeys.gameshop.constant.UserImplConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@Service
//@Transactional // Manage propagation
@AllArgsConstructor
@Qualifier("appUserDetailsService")
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User with email %s not found.";
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final AppRoleRepository appRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;


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
            validateLoginAttempt(appUser);
            appUserRepository.save(appUser);
            log.info(FOUND_USER_BY_EMAIL + email);
            return new AppUserDetails(appUser);
        }
    }

    private void validateLoginAttempt(AppUser appUser) {
        if(appUser.getIsNotLocked()){ // If the account is not locked (false)
            if(loginAttemptService.hasExceededMaxAttempts(appUser.getEmail())){ // If the user attempted login more than 5 times
                appUser.setIsNotLocked(false); // we lock the account
            }else {
                appUser.setIsNotLocked(true); // keep it unlocked. Redundant?
            }
        }else { // if the account is locked
            // If the user was ever in the cache, we remove it
            loginAttemptService.evictUserFromLoginAttemptCache(appUser.getEmail());
        }
    }

    /**
     * @param appUserDto to extract the form details
     * @return the saved appUserDto coming from the database (only when no exceptions occur)
     * @throws UserNotFoundException if no user with this email exists
     * @throws EmailExistException   if the email already exists in the database
     * @info registers a new user and sets the role to USER role
     */
    @Transactional
    public AppUserDto registerUser(AppUserDto appUserDto) throws UserNotFoundException, EmailExistException, MessagingException {
        AppUser appUserEntity = appUserMapper.convertToEntity(appUserDto);
        AppRole userRole = appRoleRepository.findByRole(Role.ROLE_USER);

        validateNewUserEmail(EMPTY, appUserEntity.getEmail());
        log.info("Saving new user {} to the database", appUserEntity.getFirstName() + " " + appUserEntity.getLastName());
        appUserEntity.setPassword(passwordEncoder.encode(appUserEntity.getPassword()));
        appUserEntity.setJoinDate(new Date());
        appUserEntity.setIsEnabled(true);
        appUserEntity.setIsNotLocked(true);
        appUserEntity.setProfileImageUrl(getTemporaryProfileImageUrl(appUserEntity.getEmail()));
        appUserEntity.setRoles(Set.of(userRole));
        appUserRepository.save(appUserEntity);

        loginAttemptService.evictUserFromLoginAttemptCache(appUserEntity.getEmail()); // clears any possible brute forced attacks
        log.info("New user password: " + appUserEntity.getPassword());
        //emailService.sendNewPasswordEmail(firstName, password, email);
        return appUserMapper.convertToDto(appUserEntity);

//        AppRole role = new AppRole();
//        role.setRole(ROLE_USER);
        //appUserEntity.addRole(new AppRole(ROLE_USER));
//        AppRole user = new AppRole(ROLE_USER);
//        appUserRoleRepository.saveAll(List.of(user));
        //AppRole userRole = appUserRoleRepository.findByRole(Role.ROLE_USER);
        //AppRole userRole = new AppRole(Role.ROLE_USER);
    }

    public AppUserDto findAppUserByEmail(String email) {
        //AppUser appUser = appUserRepository.findAppUserByEmailAndPassword(email, password).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + email + " and " + password + " was found")));
        AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + email + " was found")));
        return appUserMapper.convertToDto(appUser);
    }


    public List<AppUserDto> findAllUsers() {
        return appUserRepository.findAll().stream().map(c -> appUserMapper.convertToDto(c)).collect(Collectors.toList());
    }

    public AppUserDto updateUser(AppUserDto appUserDto) {
        // Update user by id:
        AppUser appUserEntity = appUserRepository.findAppUserByEmail(appUserDto.getEmail()).orElseThrow(() -> new EntityNotFoundException(String.format("User with email {} does not exist.", appUserDto.getEmail())));
        appUserRepository.save(appUserEntity);
        return appUserMapper.convertToDto(appUserEntity);
    }

    public AppUserDto findUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No user with id " + id + " was found")));
        return appUserMapper.convertToDto(appUser);
    }

    @Transactional
    public void deleteAppUserById(Long id) {
        //AppUser appUser = appUserRepository.findAppUserById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Specified user does not exist", id)));
        //Set<AppRole> appRoles = appUser.getRoles();

        //appUserRepository.deleteAllById(Collections.singleton(id));
        //appRoleRepository.deleteById();
        appUserRepository.deleteAppUserById(id);

    }



//    public AppUserDto getUserByEmail(String email) {
//        log.info("Fetching user {}", email);
//        AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("No user with email " + email + " was found")));
//        return appUserMapper.convertToDto(appUser);
//    }

    public boolean checkIfUserExists(String email) {
        return appUserRepository.existsAppUserByEmail(email);
    }

    private String getTemporaryProfileImageUrl(String email) {
        return DEFAULT_USER_IMAGE_PATH + email;
        //return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + email).toUriString(); // does not work when using CommandLineRunner
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

    public AppUser createNewUser(String firstName, String lastName, String email, String password,String role, boolean isNotLocked, boolean isEnabled, MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException, NotAnImageFileException {
        validateNewUserEmail(EMPTY, email);
        AppUser appUser = new AppUser();

        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);
        appUser.setJoinDate(new Date());
        appUser.setEmail(email);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setIsEnabled(isEnabled);
        appUser.setIsNotLocked(isNotLocked);
        appUser.setRoles((Set<AppRole>) appRoleRepository.findByRole(Role.ROLE_USER));
        appUser.setProfileImageUrl(getTemporaryProfileImageUrl(email));
        appUserRepository.save(appUser);
        saveProfileImage(appUser, profileImage);

        return appUser;
    }

    private void saveProfileImage(AppUser appUser, MultipartFile profileImage) {

    }
}
