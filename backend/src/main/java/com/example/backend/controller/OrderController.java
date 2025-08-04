package com.example.backend.controller;

import com.example.backend.entity.Order;
import com.example.backend.repository.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Endpoint to get all orders - verify data load
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
