package ru.xpressed.javatemplatescoursework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xpressed.javatemplatescoursework.models.User;
import ru.xpressed.javatemplatescoursework.repositories.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }
}
