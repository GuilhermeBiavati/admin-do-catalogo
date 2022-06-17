package com.fullcle.admin.catalogo.application.category.delete;

import com.fullcle.admin.catalogo.IntegrationTest;
import com.fullcle.admin.catalogo.domain.category.Category;
import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcle.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@IntegrationTest
public class DeleteCategoryUseCaseIT {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGeteway categoryGeteway;

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();
        Assertions.assertEquals(0, categoryRepository.count());
        save(aCategory);
        Assertions.assertEquals(1, categoryRepository.count());
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {
        final var expectedId = CategoryID.from("123");
        Assertions.assertEquals(0, categoryRepository.count());
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    public void givenAValidId_whenGetewayThrowsException_shouldReturnException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error")).when(categoryGeteway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGeteway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Category... aCategory) {
        categoryRepository.saveAllAndFlush(
                Arrays.stream(aCategory)
                        .map(CategoryJpaEntity::from)
                        .toList()
        );
    }


}
