package com.sofian.codingtest.securities.repositories;

import com.sofian.codingtest.securities.entity.Role;
import com.sofian.codingtest.securities.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
