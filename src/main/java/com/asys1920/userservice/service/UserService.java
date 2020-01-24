package com.asys1920.userservice.service;

import com.asys1920.userservice.model.User;
import com.asys1920.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User updateDriversLicenseExpirationDate(Long id, String newExpirationDate) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            userRepository.setExpirationDateDriversLicense(id,newExpirationDate);
            User user = userRepository.findById(id).get();
            user.setExpirationDateDriversLicense(newExpirationDate);
            return user;
        } else {
            throw new NoSuchElementException("There is no user for id " + id);
        }
    }
}
