package ru.kpfu.stud.garipov.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.kpfu.stud.garipov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByEmail(String email);

    Page<User> findAll(Pageable pageable);

    User findByVerificationCode(String code);
}
