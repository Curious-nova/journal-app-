package com.project.journal.controller;


import com.project.journal.entity.JournalEntity;
import com.project.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    public Map<Long, JournalEntity> journalEntityList = new HashMap<>();

    @GetMapping("/get-all")
    public List<JournalEntity> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping("/add-entry")
    public void createEntry(@RequestBody JournalEntity myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
    }
    @GetMapping("/id/{id}")
    public JournalEntity getJournalEntryById(@PathVariable ObjectId id) {
        return journalEntryService.findById(id).orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return true;
    }

    @PutMapping("/update/{id}")
    public JournalEntity updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry) {
        JournalEntity old = journalEntryService.findById(id).orElse(null);
        if(old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return old;
    }
}
