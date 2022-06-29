package com.fullcle.admin.catalogo.infrastructure.genre;

import com.fullcle.admin.catalogo.MySQLGatewayTest;
import com.fullcle.admin.catalogo.infrastructure.category.persistence.CategoryMySQLGateway;
import com.fullcle.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class GenreMySQLGatewayTest {
    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private GenreMySQLGateway genreMySQLGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void testDependencies() {
        Assertions.assertNotNull(categoryMySQLGateway);
        Assertions.assertNotNull(genreMySQLGateway);
        Assertions.assertNotNull(genreRepository);
    }

    @Test
    pu
}
