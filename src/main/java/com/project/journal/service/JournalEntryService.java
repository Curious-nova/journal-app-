package com.project.journal.service;

import com.project.journal.entity.JournalEntity;
import com.project.journal.entity.UserEntity;
import com.project.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional // treat the saveEntry as single code if fail then rollback
    public void saveEntry(JournalEntity journalEntity, String userName) {
       try{
           UserEntity user = userService.findByUserName(userName);
           JournalEntity saved = journalEntryRepository.save(journalEntity);
           user.getJournalEntities().add(saved); // here we are adding the journal to user's list
           userService.saveEntry(user); // here we are saving the user in the db
       }
       catch (Exception e) {
           throw new RuntimeException("An error occured while saving the entry",e);
       }
    }
    public void saveEntry(JournalEntity journalEntity) {

        journalEntryRepository.save(journalEntity);// here we are saving the user in the db
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntity> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName) {
        UserEntity user = userService.findByUserName(userName);

        user.getJournalEntities().removeIf(x -> x.getId().equals(id)); // this is the lambda function called here because when we removed the journal entry from db its reference is not automatically removed from the user's list so we are manually iternating in the list and removing the reference whose id == given id
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);


    }
}
