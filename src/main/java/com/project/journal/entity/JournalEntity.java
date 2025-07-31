package com.project.journal.entity;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


@Document(collection = "journal_entries") // Ye Row hoga
@Data
@NoArgsConstructor
public class JournalEntity {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;
}
