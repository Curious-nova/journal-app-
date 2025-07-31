package com.project.journal.repository;

import com.project.journal.entity.JournalEntity;
import com.project.journal.entity.UserEntity;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUserName(String userName);
}
