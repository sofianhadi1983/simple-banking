package com.sofian.codingtest.securities.repositories;

import com.sofian.codingtest.securities.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // SELECT * FROM user WHERE email = ''

    Optional<User> findByUsernameOrEmail(String username, String email); // SELECT * FROM user WHERE username = '' OR email = ''

    List<User> findByIdIn(List<Long> userIds); // SELECT * FROM user WHERE ID IN(1,2,3,3)

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
