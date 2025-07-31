package com.project.journal.service;

import com.project.journal.entity.UserEntity;
import com.project.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(UserEntity UserEntity) {
        userRepository.save(UserEntity);
    }

    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }
    public Optional<UserEntity> findById(ObjectId id) {
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
