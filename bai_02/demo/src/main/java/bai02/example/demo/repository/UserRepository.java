package bai02.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bai02.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // dùng để thao tác với bảng users trong database

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username); //Optional để tránh lỗi null pointer exception
    

}
