package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.IntegrationTest;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

@IntegrationTest
public class ListCategoryUseCaseIT {

    @Autowired
    private ListCategoriesUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void mockUp() throws InterruptedException {

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Filmes", null, true)));
        Thread.sleep(1);
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Amazon", "Originais da Amazon", true)));
        Thread.sleep(1);
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Netflix", null, true)));
        Thread.sleep(1);
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Documentarios", null, true)));
        Thread.sleep(1);
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Esportes", null, true)));
        Thread.sleep(1);
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Kids", "Crianças", true)));
        Thread.sleep(1);
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(Category.newCategory("Z", "Last", true)));

    }

    @Test
    public void givenAValidTerm_whenTermDoesntMatchsPrePersisted_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "asdfasdfas";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0;
        final var expectedTotal = 0;

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());


    }

    @ParameterizedTest
    @CsvSource({
            "fil,0,10,1,1,Filmes",
            "Net,0,10,1,1,Netflix",
            "ZON,0,10,1,1,Amazon",
            "kids,0,10,1,1,Kids",
            "crianças,0,10,1,1,Kids",
            "da Amazon,0,10,1,1,Amazon",
    })
    public void givenAValidTerm_whenCallsListCategories_shouldReturnCategoriesFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoryName
    ) {
        final var expectedSort = "name";
        final var expectedDirection = "asc";


        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());

    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,7,7,Amazon",
            "name,desc,0,10,7,7,Z",
            "createdAt,asc,0,10,7,7,Filmes",
            "createdAt,desc,0,10,7,7,Z",
    })
    public void givenAValidSortAndDirection_whenCallsListCategories_thenShouldReturnCategoriesOrdered(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoryName
    ) {

        final var expectedTerms = "";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());

    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,7,Amazon;Documentarios",
            "1,2,2,7,Esportes;Filmes",
            "2,2,2,7,Kids;Netflix",
            "3,2,1,7,Z",
    })
    public void givenAvalidPage_whenCallsListCategories_shouldReturnCategoriesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoriesName
    ) {

        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";


        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (String expectedName : expectedCategoriesName.split(";")) {
            Assertions.assertEquals(expectedName, actualResult.items().get(index).name());
            index++;
        }


    }
}
