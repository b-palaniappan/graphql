package io.c12.bala.graphql.repository;

import io.c12.bala.graphql.entity.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<BookEntity, String> {
}
