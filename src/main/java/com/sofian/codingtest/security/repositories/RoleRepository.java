package com.sofian.codingtest.security.repositories;

import com.sofian.codingtest.security.entities.Role;
import com.sofian.codingtest.security.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
