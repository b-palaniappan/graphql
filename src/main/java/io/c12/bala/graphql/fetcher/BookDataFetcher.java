package io.c12.bala.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.c12.bala.graphql.entity.BookEntity;
import io.c12.bala.graphql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookDataFetcher implements DataFetcher<BookEntity> {

    private final BookRepository bookRepository;

    @Override
    public BookEntity get(DataFetchingEnvironment environment) {
        String isn = environment.getArgument("id");
        return bookRepository.findById(isn).orElse(null);
    }
}
