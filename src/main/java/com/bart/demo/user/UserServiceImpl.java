package com.bart.demo.user;

import com.bart.demo.config.error.NotFoundException;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Synchronized
    public User createUser(CreateUserDTO createUserDTO) {
        return userRepository.findByUsername(createUserDTO.getUsername())
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername(createUserDTO.getUsername());
                    return userRepository.save(user);
                });
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User addSubscription(Long userId, AddSubscriptionDTO addSubscriptionDTO) {

        User user = findById(userId).orElseThrow(NotFoundException::new);
        Long followedUserId = addSubscriptionDTO.getUserId();
        User followedUser = findById(followedUserId).orElseThrow(NotFoundException::new);

        user.getSubscribedUsers().add(followedUser);
        return userRepository.save(user);
    }

    public Set<User> getSubscriptions(Long userId) {
        User user = findById(userId).orElseThrow(NotFoundException::new);
        return user.getSubscribedUsers();
    }
}
