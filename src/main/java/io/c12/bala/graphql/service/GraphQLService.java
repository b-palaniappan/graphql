package io.c12.bala.graphql.service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.c12.bala.graphql.entity.BookEntity;
import io.c12.bala.graphql.fetcher.AllBooksDataFetcher;
import io.c12.bala.graphql.fetcher.BookDataFetcher;
import io.c12.bala.graphql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

@Log4j2
@Service
@RequiredArgsConstructor
public class GraphQLService {

    private final BookRepository bookRepository;
    private final AllBooksDataFetcher allBooksDataFetcher;
    private final BookDataFetcher bookDataFetcher;

    private GraphQL graphQL;

    @Value("classpath:books.graphql")
    Resource resource;

    @PostConstruct
    private void loadSchema() throws IOException {
        loadMongoData();

        //Get the graphql file
        File file = resource.getFile();

        //Parse SchemaF
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(file);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private void loadMongoData() {
        Stream.of(
                new BookEntity("n67BPWthH7bjlxYVjIGRc", "1001", "The C Programming Language", "PHI Learning", "1978", Arrays.asList("Brian W. Kernighan (Contributor)", "Dennis M. Ritchie")),
                new BookEntity("BoBIIKSgA6XYSzNH6HXfz", "1002", "Your Guide To Scrivener", "MakeUseOf.com", " April 21st 2013", Collections.singletonList("Nicole Dionisio (Goodreads Author)")),
                new BookEntity("meJUJiI_6a6XeixwJ3aot", "1003", "Beyond the Inbox: The Power User Guide to Gmail", " Kindle Edition", "November 19th 2012", Arrays.asList("Shay Shaked", "Justin Pot", "Angela Randall (Goodreads Author)")),
                new BookEntity("WVrx0lJPSOSnC3qGUkHx9", "1004", "Scratch 2.0 Programming", "Smashwords Edition", "February 5th 2015", Collections.singletonList("Denis Golikov (Goodreads Author)")),
                new BookEntity("2whX0QPAxAyUJe0XQ81Jt", "1005", "Pro Git", "by Apress (first published 2009)", "2014", Collections.singletonList("Scott Chacon")))
                .forEach(bookRepository::save);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }

}
