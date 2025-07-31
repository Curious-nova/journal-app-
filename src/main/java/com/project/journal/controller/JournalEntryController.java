package com.project.journal.controller;


import com.project.journal.entity.JournalEntity;
import com.project.journal.entity.UserEntity;
import com.project.journal.service.JournalEntryService;
import com.project.journal.service.UserService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    public Map<Long, JournalEntity> journalEntityList = new HashMap<>();

    @GetMapping("/get-all/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        UserEntity user = userService.findByUserName(userName);

        List<JournalEntity> all = user.getJournalEntities();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-entry/{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry,@PathVariable String userName) {
        try{

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName) ;
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntity> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntity> journalEntry = journalEntryService.findById(id);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id, @PathVariable String userName) {
        journalEntryService.deleteById(id,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{userName}/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry,@PathVariable String userName) {
        JournalEntity old = journalEntryService.findById(id).orElse(null);
        if(old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
