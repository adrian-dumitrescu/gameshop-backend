package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository <AppUser, Long> {
    // add custom queries here
    void deleteAppUserById(Long id);

    Optional<AppUser> findById(Long id);

    boolean existsAppUserByEmail(String email);

    Optional<AppUser> findAppUserByEmail(String email);

    Optional<AppUser> findAppUserByEmailAndPassword(String email, String password);

    Optional<AppUser> findAppUserById(Long id);
}
