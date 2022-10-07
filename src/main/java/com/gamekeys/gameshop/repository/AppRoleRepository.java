package com.gamekeys.gameshop.repository;

import com.gamekeys.gameshop.entity.enums.Role;
import com.gamekeys.gameshop.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    //@Query("SELECT r FROM AppRole r WHERE r.role = ?1")
    public AppRole findByRole(Role role);


}
