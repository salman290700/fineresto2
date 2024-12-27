package org.babagroup.repository;

import org.babagroup.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Override
    Optional<User> findById(String s);
    Optional<User> findByEmail(String e);
    Optional<User> findByEmailAndPassword(String e, String p);
    boolean existsByEmail(String e);

}
