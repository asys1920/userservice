package com.asys1920.userservice.service;

import com.asys1920.model.User;
import com.asys1920.userservice.exceptions.UserAlreadyExsitsException;
import com.asys1920.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) throws UserAlreadyExsitsException {
        LOG.trace(String.format("SERVICE %s initiated", "createUser"));
        if (user.getId() != null && userRepository.findById(user.getId()).isPresent())
            throw new UserAlreadyExsitsException("User with id: " + user.getId() + " already exists");
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        LOG.trace(String.format("SERVICE %s initiated", "deleteUser"));
        userRepository.deleteById(id);
    }

    public User getUser(long id) {
        LOG.trace(String.format("SERVICE %s initiated", "getUser"));
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public User updateUser(User user) {
        LOG.trace(String.format("SERVICE %s initiated", "updateUser"));
        return userRepository.save(user);
    }
}
