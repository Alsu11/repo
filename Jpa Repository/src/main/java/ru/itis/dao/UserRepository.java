package ru.itis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByFirstNameLike(String firstName);
}
