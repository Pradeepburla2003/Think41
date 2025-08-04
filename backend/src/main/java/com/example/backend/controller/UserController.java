package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.dto.UserDetailsWithOrderCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // Get all users with pagination
    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched successfully", users));
    }

    // Get specific user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found with id " + id, null)));
    }

    // Get specific user with order count
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getUserDetailsWithOrderCount(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    long count = orderRepository.countByUser(user);
                    UserDetailsWithOrderCount details = new UserDetailsWithOrderCount(user, count);
                    return ResponseEntity.ok(new ApiResponse<>(true, "User found", details));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found with id " + id, null)));
    }
}
