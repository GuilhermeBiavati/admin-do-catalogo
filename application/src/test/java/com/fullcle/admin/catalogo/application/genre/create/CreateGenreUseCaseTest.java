package com.fullcle.admin.catalogo.application.genre.create;


import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.genre.GenreGeteway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateGenreUseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private GenreGeteway genreGeteway;

    @Mock
    private CategoryGeteway categoryGeteway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(genreGeteway);
    }

//    Teste do caminho feliz
//    Teste passando uma propriedade inválida (name)
//    Teste criando uma categoria inativa
//    Teste simulando um erro generico vindo do getway

    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = CreateGenreCommand.with(expectedName, asString(expectedCategories), expectedIsActive);

        when(genreGeteway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGeteway, times(1))
                .create(argThat(aGenre ->
                        Objects.equals(expectedName, aGenre.getName())
                                && Objects.equals(expectedCategories, aGenre.getCategories())
                                && Objects.equals(expectedIsActive, aGenre.isActive())
                                && Objects.nonNull(aGenre.getId())
                                && Objects.nonNull(aGenre.getCreatedAt())
                                && Objects.nonNull(aGenre.getUpdatedAt())
                                && Objects.nonNull(aGenre.getUpdatedAt())
                                && Objects.isNull(aGenre.getDeletedAt())
                ));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_shouldReturnGenreId() {
        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("97")
        );

        final var aCommand = CreateGenreCommand.with(expectedName, asString(expectedCategories), expectedIsActive);

        when(categoryGeteway.existsByIds(any())).thenReturn(expectedCategories);

        when(genreGeteway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGeteway, times(1)).existsByIds(expectedCategories);

        Mockito.verify(genreGeteway, times(1))
                .create(argThat(aGenre ->
                        Objects.equals(expectedName, aGenre.getName())
                                && Objects.equals(expectedCategories, aGenre.getCategories())
                                && Objects.equals(expectedIsActive, aGenre.isActive())
                                && Objects.nonNull(aGenre.getId())
                                && Objects.nonNull(aGenre.getCreatedAt())
                                && Objects.nonNull(aGenre.getUpdatedAt())
                                && Objects.nonNull(aGenre.getUpdatedAt())
                                && Objects.isNull(aGenre.getDeletedAt())
                ));
    }

    @Test
    public void givenAInvalidNullName_whenCallsCreateGenre_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateGenreCommand.with(expectedName, asString(expectedCategories), expectedIsActive);

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> {
                    useCase.execute(aCommand);
                }
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGeteway, times(0)).create(any());
        Mockito.verify(categoryGeteway, times(0)).existsByIds(any());

    }

    @Test
    public void givenAInvalidEmptyName_whenCallsCreateGenre_thenShouldReturnDomainException() {
        final String expectedName = "    ";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aCommand = CreateGenreCommand.with(expectedName, asString(expectedCategories), expectedIsActive);

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> {
                    useCase.execute(aCommand);
                }
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGeteway, times(0)).create(any());
        Mockito.verify(categoryGeteway, times(0)).existsByIds(any());

    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsCreateGenre_thenShouldReturnDomainException() {
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("321");

        final String expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                filmes, series, documentarios
        );
        final var expectedErrorMessage = "Some categories could not be found: 123, 321";
        final var expectedErrorCount = 1;

        when(categoryGeteway.existsByIds(any())).thenReturn(List.of(series));

        final var aCommand = CreateGenreCommand.with(expectedName, asString(expectedCategories), expectedIsActive);

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> {
                    useCase.execute(aCommand);
                }
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGeteway, times(0)).create(any());
        Mockito.verify(categoryGeteway, times(1)).existsByIds(any());

    }

    @Test
    public void givenAInvalidCommandNullNameWithCategories_whenCallsCreateGenre_thenShouldReturnDomainException() {
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("321");

        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                filmes, series, documentarios
        );

        final var expectedErrorMessage = "Some categories could not be found: 123, 321";
        final var expectedError2Message = "'name' should not be null";

        final var expectedErrorCount = 2;

        when(categoryGeteway.existsByIds(any())).thenReturn(List.of(series));

        final var aCommand = CreateGenreCommand.with(expectedName, asString(expectedCategories), expectedIsActive);

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> {
                    useCase.execute(aCommand);
                }
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedError2Message, actualException.getErrors().get(1).message());

        Mockito.verify(genreGeteway, times(0)).create(any());
        Mockito.verify(categoryGeteway, times(1)).existsByIds(any());

    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsCreateGenre_shouldReturnInactiveGenreId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCommand = CreateGenreCommand.with(expectedName, asString(List.<CategoryID>of()), expectedIsActive);

        when(genreGeteway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGeteway, times(1))
                .create(argThat(aGenre -> Objects.equals(expectedName, aGenre.getName())
                        && Objects.equals(expectedIsActive, aGenre.isActive())
                        && Objects.nonNull(aGenre.getId())
                        && Objects.nonNull(aGenre.getCreatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.nonNull(aGenre.getUpdatedAt())
                        && Objects.nonNull(aGenre.getDeletedAt())
                ));
    }

//    @Test
//    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
//        final var expectedName = "Filmes";
//        final var expectedIsActive = true;
//        final var expectedErrorMessage = "Gateway error";
//        final var expectedErrorCount = 1;
//
//        final var aCommand = CreateGenreCommand.with(expectedName, asString(List.<CategoryID>of()), expectedIsActive);
//
//
//        final var actualException = Assertions.assertThrows(
//                NotificationException.class,
//                () -> {
//                    useCase.execute(aCommand);
//                }
//        );
//
//        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
//        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
//
//        Mockito.verify(genreGeteway, times(1))
//                .create(argThat(aGenre -> Objects.equals(expectedName, aGenre.getName())
//                        && Objects.equals(expectedIsActive, aGenre.isActive())
//                        && Objects.nonNull(aGenre.getId())
//                        && Objects.nonNull(aGenre.getCreatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.isNull(aGenre.getDeletedAt())
//                ));
//    }

    private List<String> asString(final List<CategoryID> categories) {
        return categories.stream().map(CategoryID::getValue).toList();
    }
}
