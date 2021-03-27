package io.c12.bala.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.c12.bala.graphql.entity.BookEntity;
import io.c12.bala.graphql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllBooksDataFetcher implements DataFetcher<List<BookEntity>> {

    private final BookRepository bookRepository;

    @Override
    public List<BookEntity> get(DataFetchingEnvironment environment) {
        return bookRepository.findAll();
    }
}
