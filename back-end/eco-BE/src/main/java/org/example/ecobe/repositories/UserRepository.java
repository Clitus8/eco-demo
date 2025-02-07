package org.example.ecobe.repositories;

import org.example.ecobe.enums.UserRole;
import org.example.ecobe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);


    Optional<User> findByRole(UserRole userRole);
}
