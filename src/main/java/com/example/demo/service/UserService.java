package com.example.demo.service;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;


    // ============================================================
    // GET ALL USERS
    // ============================================================
    public List<User> getAllUser() {
        log.info("Service:getAllUser - Fetching all users");
        return userRepo.findAll();
    }


    // ============================================================
    // GET USER BY ID
    // ============================================================
    public User getById(Long id) {
        log.info("Service:getById - Fetching user id={}", id);

        return userRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Service:getById - User {} not found", id);
                    return new NotFoundException("User not found with id = " + id);
                });
    }


    // ============================================================
    // CREATE USER
    // ============================================================
    public User create(UserCreateRequest req) {

        log.info("Service:create - Creating user {}", req);

        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new BadRequestException("Name must not be empty");
        }

        if (req.getPhone() == null || req.getPhone().trim().isEmpty()) {
            throw new BadRequestException("Phone must not be empty");
        }

        // Tạo mới
        User user = new User();
        user.setName(req.getName().trim());
        user.setPhone(req.getPhone().trim());

        User saved = userRepo.save(user);

        log.info("Service:create - User created id={}", saved.getId());
        return saved;
    }


    // ============================================================
    // UPDATE USER
    // ============================================================
    public User update(Long id, UserUpdateRequest req) {

        log.info("Service:update - Updating user id={} data={}", id, req);

        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + id));

        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            user.setName(req.getName().trim());
        }

        if (req.getPhone() != null && !req.getPhone().trim().isEmpty()) {
            user.setPhone(req.getPhone().trim());
        }

        return userRepo.save(user);
    }


    // ============================================================
    // DELETE USER
    // ============================================================
    public void delete(Long id) {

        log.info("Service:delete - Deleting user id={}", id);

        if (!userRepo.existsById(id)) {
            throw new NotFoundException("User not found with id = " + id);
        }

        userRepo.deleteById(id);
    }



    // ============================================================
    // FIND USER BY NAME
    // ============================================================
    public List<User> findByName(String name) {
        log.info("Service:findByName - Searching name={}", name);

        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Name query must not be empty");
        }

        return userRepo.findByNameContainingIgnoreCase(name.trim());
    }


    // ============================================================
    // FIND USER BY PHONE
    // ============================================================
    public List<User> findByPhone(String phone) {
        log.info("Service:findByPhone - Searching phone={}", phone);

        if (phone == null || phone.trim().isEmpty()) {
            throw new BadRequestException("Phone query must not be empty");
        }

        return userRepo.findByPhone(phone.trim());
    }

}










package com.example.demo.service;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    // Get all (no paging)
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    // ============================================================
    // PAGING - get users by page
    // ============================================================
    public Page<User> getUsersByPage(int page, int size) {

        log.info("Service:getUsersByPage - page={}, size={}", page, size);

        if (page < 0) throw new BadRequestException("Page must be >= 0");
        if (size <= 0) throw new BadRequestException("Size must be > 0");

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return userRepo.findAll(pageable);
    }

    // ============================================================
    // PAGING + FILTER BY NAME
    // ============================================================
    public Page<User> searchByName(String name, int page, int size) {

        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("Name must not be empty");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return userRepo.findByNameContainingIgnoreCase(name.trim(), pageable);
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + id));
    }

    // CREATE
    public User create(UserCreateRequest req) {

        if (req.getName() == null || req.getName().trim().isEmpty()) {
            throw new BadRequestException("Name must not be empty");
        }

        if (req.getPhone() == null || req.getPhone().trim().isEmpty()) {
            throw new BadRequestException("Phone must not be empty");
        }

        User user = new User();
        user.setName(req.getName().trim());
        user.setPhone(req.getPhone().trim());

        return userRepo.save(user);
    }

    // UPDATE
    public User update(Long id, UserUpdateRequest req) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + id));

        if (req.getName() != null && !req.getName().trim().isEmpty()) {
            user.setName(req.getName().trim());
        }

        if (req.getPhone() != null && !req.getPhone().trim().isEmpty()) {
            user.setPhone(req.getPhone().trim());
        }

        return userRepo.save(user);
    }

    // DELETE
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("User not found with id = " + id);
        }
        userRepo.deleteById(id);
    }
}

