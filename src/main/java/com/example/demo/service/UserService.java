package com.example.demo.service;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;


    public List<User> getAllUser() {
        log.info("Service:getAllUser - Fetching all users");
        List<User> users = userRepo.findAll();
        log.info("Service:getAllUser - Found {} users", users.size());
        return users;
    }


    public User create(UserCreateRequest req) {

        // Validate business logic
        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new BadRequestException("Name must not be empty");
        }

        if (req.getPhone() == null || req.getPhone().trim().isEmpty()) {
            throw new BadRequestException("Phone must not be empty");
        }

        // Create user
        User user = new User();
        user.setName(req.getName());
        user.setPhone(req.getPhone());

        return userRepo.save(user);
    }

}
