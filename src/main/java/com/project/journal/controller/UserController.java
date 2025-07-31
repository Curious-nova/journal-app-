package com.project.journal.controller;


import com.project.journal.entity.JournalEntity;
import com.project.journal.entity.UserEntity;
import com.project.journal.service.JournalEntryService;
import com.project.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public List<UserEntity>getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/add-user")
    public void createUser(@RequestBody UserEntity user) {
        userService.saveEntry(user);
    }

    @PutMapping("/update-user/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user,@PathVariable String userName) {
        UserEntity userInDb = userService.findByUserName(userName);
        if(userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
