package com.example.demo.repo;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findByPhone(String phone);

}
