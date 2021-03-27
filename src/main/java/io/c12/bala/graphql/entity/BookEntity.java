package io.c12.bala.graphql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document("book")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {

    @MongoId
    private String id;
    private String isn;
    private String title;
    private String publisher;
    private String publishDate;
    private List<String> author;
}
