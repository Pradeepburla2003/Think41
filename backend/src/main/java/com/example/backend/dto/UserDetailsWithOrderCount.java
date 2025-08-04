package com.example.backend.dto;

import com.example.backend.entity.User;

public class UserDetailsWithOrderCount {
    private User user;
    private long orderCount;

    public UserDetailsWithOrderCount(User user, long orderCount) {
        this.user = user;
        this.orderCount = orderCount;
    }

    public User getUser() {
        return user;
    }

    public long getOrderCount() {
        return orderCount;
    }
}
