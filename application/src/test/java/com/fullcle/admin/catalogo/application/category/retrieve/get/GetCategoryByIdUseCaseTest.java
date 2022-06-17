package com.fullcle.admin.catalogo.application.category.retrieve.get;

import com.fullcle.admin.catalogo.domain.category.Category;
import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {
    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGeteway CategoryGeteway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(CategoryGeteway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria msis assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId();

        when(CategoryGeteway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualCategory.id());
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());

    }

    @Test
    public void givenAnInvalidId_whenCallsGetCategory_shouldReturnNotFound() {
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");

        when(CategoryGeteway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                NotFoundException.class, () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenCallsThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = CategoryID.from("123");

        when(CategoryGeteway.findById(eq(expectedId))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class, () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }


}
