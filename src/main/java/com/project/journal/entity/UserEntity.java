package com.project.journal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "users") // Ye Row hoga
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    private ObjectId id;
    @Indexed(unique = true) // it will not automatically create index so we added it in application properties
    @NonNull
    private String userName;
    @NonNull
    private String password;

    @DBRef // it keeps the reference rather than complete data more like foreign key
    private List<JournalEntity> journalEntities = new ArrayList<>();
}
