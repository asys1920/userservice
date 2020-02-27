package com.asys1920.service.service;

import com.asys1920.model.User;
import com.asys1920.service.exceptions.UserAlreadyExsitsException;
import com.asys1920.service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) throws UserAlreadyExsitsException {
        if (user.getId() != null && userRepository.findById(user.getId()).isPresent())
            throw new UserAlreadyExsitsException("User with id: " + user.getId() + " already exists");
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
