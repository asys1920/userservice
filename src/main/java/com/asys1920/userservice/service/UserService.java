package com.asys1920.userservice.service;

import com.asys1920.model.User;
import com.asys1920.userservice.exceptions.UserAlreadyExistsException;
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

    /**
     * Saves the user to the repository
     * @param user the user to be saved
     * @return the saved user
     * @throws UserAlreadyExistsException
     */
    @Transactional
    public User createUser(User user) throws UserAlreadyExistsException {
        LOG.trace(String.format("SERVICE %s initiated", "createUser"));
        if (user.getId() != null && userRepository.findById(user.getId()).isPresent())
            throw new UserAlreadyExistsException("User with id: " + user.getId() + " already exists");
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the repository
     * @param id
     */
    public void deleteUser(long id) {
        LOG.trace(String.format("SERVICE %s initiated", "deleteUser"));
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            userRepository.deleteById(id);
        }else{
            throw new NoSuchElementException(String.format("There is no user known with id %d",id));
        }
    }

    /**
     * Retrieves a user from the repository
     * @param id the id associated with the user
     * @return the user
     * @exception NoSuchElementException gets thrown when no user is known for the given id
     */
    public User getUser(long id) throws NoSuchElementException{
        LOG.trace(String.format("SERVICE %s initiated", "getUser"));
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Updates the repository entry for the given user.id
     * @param user user that will get updated
     * @return the updated user
     */
    @Transactional
    public User updateUser(User user) {
        LOG.trace(String.format("SERVICE %s initiated", "updateUser"));
        return userRepository.save(user);
    }
}
