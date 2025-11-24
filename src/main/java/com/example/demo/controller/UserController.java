package com.example.demo.controller;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@SecurityRequirement(name = "Authorization")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getListUser() {
        log.info("Controller:getListUser called");
        List<User> users = userService.getAllUser();
        log.info("Controller:getListUser returning {} users", users.size());
        return users;
    }

    // ------------------------------------------
    // CREATE USER + @Valid
    // ------------------------------------------
    @PostMapping
    public User createUser(@Valid @RequestBody UserCreateRequest req) {
        log.info("Create user {}", req);
        return userService.create(req);
    }

}

































package com.example.demo.controller;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    // ============================================================
    // GET USERS WITH PAGING
    // ============================================================
    @GetMapping("/paging")
    public Page<User> getUsersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return userService.getUsersByPage(page, size);
    }


    // ============================================================
    // SEARCH + PAGING
    // ============================================================
    @GetMapping("/search")
    public Page<User> search(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return userService.searchByName(name, page, size);
    }


    // CRUD
    @PostMapping
    public User create(@RequestBody UserCreateRequest req) {
        return userService.create(req);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserUpdateRequest req) {
        return userService.update(id, req);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}

