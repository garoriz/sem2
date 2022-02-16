package ru.kpfu.stud.garipov.repository;

import ru.kpfu.stud.garipov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
