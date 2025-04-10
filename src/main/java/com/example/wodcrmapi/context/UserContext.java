package com.example.wodcrmapi.context;

import com.example.wodcrmapi.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public void setUser(User user) {
        currentUser.set(user);
    }

    public User getUser() {
        return currentUser.get();
    }

    public void clear() {
        currentUser.remove();
    }
}
