package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        User user = null;
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            user = u.get();
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User updatedUser) {
        if (!updatedUser.getPassword().equals(getUser(updatedUser.getId()).getPassword())) {
            updatedUser.setPassword((passwordEncoder.encode((updatedUser.getPassword()))));
        }
        userRepository.save(updatedUser);
    }

    @Override
    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUsername(String email) {
        return userRepository.findByUsername(email);
    }
}
