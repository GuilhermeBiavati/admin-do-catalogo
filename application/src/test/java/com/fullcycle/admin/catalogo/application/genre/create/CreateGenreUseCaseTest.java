package com.fullcycle.admin.catalogo.application.genre.create;


import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CreateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
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

        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGateway, times(1))
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

        when(categoryGateway.existsByIds(any())).thenReturn(expectedCategories);

        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).existsByIds(expectedCategories);

        Mockito.verify(genreGateway, times(1))
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

        Mockito.verify(genreGateway, times(0)).create(any());
        Mockito.verify(categoryGateway, times(0)).existsByIds(any());

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

        Mockito.verify(genreGateway, times(0)).create(any());
        Mockito.verify(categoryGateway, times(0)).existsByIds(any());

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

        when(categoryGateway.existsByIds(any())).thenReturn(List.of(series));

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

        Mockito.verify(genreGateway, times(0)).create(any());
        Mockito.verify(categoryGateway, times(1)).existsByIds(any());

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

        when(categoryGateway.existsByIds(any())).thenReturn(List.of(series));

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

        Mockito.verify(genreGateway, times(0)).create(any());
        Mockito.verify(categoryGateway, times(1)).existsByIds(any());

    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsCreateGenre_shouldReturnInactiveGenreId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var aCommand = CreateGenreCommand.with(expectedName, asString(List.<CategoryID>of()), expectedIsActive);

        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(genreGateway, times(1))
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
//        Mockito.verify(genreGateway, times(1))
//                .create(argThat(aGenre -> Objects.equals(expectedName, aGenre.getName())
//                        && Objects.equals(expectedIsActive, aGenre.isActive())
//                        && Objects.nonNull(aGenre.getId())
//                        && Objects.nonNull(aGenre.getCreatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.isNull(aGenre.getDeletedAt())
//                ));
//    }


}
