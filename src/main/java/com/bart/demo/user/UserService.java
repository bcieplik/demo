package com.bart.demo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    User createUser(CreateUserDTO createUserDTO);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long userId);
    Page<User> getUsers(Pageable pageable);
    User addSubscription(Long userId, AddSubscriptionDTO addSubscriptionDTO);
    Set<User> getSubscriptions(Long userId);
}
