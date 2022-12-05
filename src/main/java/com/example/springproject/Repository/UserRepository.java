package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import com.example.springproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT s FROM User s where s.login = ?1")
    Optional<User> findByLogin(String login);

    @Query("SELECT s from User s where s.email = ?1")
    Optional<User>findByEmail(String email);
    @Query("SELECT u from User u  where u.login=?1 and u.password=?2")
    Optional<User>LoginAndPassword(String login, String password);
}
