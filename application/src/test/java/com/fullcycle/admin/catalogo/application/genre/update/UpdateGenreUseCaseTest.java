package com.fullcycle.admin.catalogo.application.genre.update;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcycle.admin.catalogo.domain.genre.Genre;
import com.fullcycle.admin.catalogo.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class UpdateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateGenreUseCase useCase;

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
    //    Teste atualizando uma categoria para inativa
    //    Teste simulando um erro generico vindo do getway
    //    Teste atualizar categoria passando id inválido

    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() {
        //        given

        final var aGenre = Genre.newGenre("Film", true);

        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedId = aGenre.getId();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories));

        waitToUpdate();

        Mockito.when(genreGateway.findById(eq(expectedId))).thenReturn(Optional.of(aGenre.clone()));

        Mockito.when(genreGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, Mockito.times(1)).findById(eq(expectedId));
        Mockito.verify(genreGateway, Mockito.times(1)).update(ArgumentMatchers.argThat(
                aUpdatedGenre -> Objects.equals(
                        expectedName, aUpdatedGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdatedGenre.isActive())
                        && Objects.equals(expectedId, aUpdatedGenre.getId())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdatedGenre.getCreatedAt())
                        && Objects.nonNull(aUpdatedGenre.getUpdatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt())
                        && Objects.isNull(aUpdatedGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
        //        given

        final var aGenre = Genre.newGenre("Film", true);

        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var expectedId = aGenre.getId();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories));

        when(genreGateway.findById(any())).thenReturn(Optional.of(Genre.with(aGenre)));

        when(categoryGateway.existsByIds(any())).thenReturn(expectedCategories);

        Mockito.when(genreGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, Mockito.times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, Mockito.times(1)).existsByIds(expectedCategories);


        Mockito.verify(genreGateway, Mockito.times(1)).update(ArgumentMatchers.argThat(
                aUpdatedGenre -> Objects.equals(
                        expectedName, aUpdatedGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdatedGenre.isActive())
                        && Objects.equals(expectedId, aUpdatedGenre.getId())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdatedGenre.getCreatedAt())
                        && Objects.nonNull(aUpdatedGenre.getUpdatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt())
                        && Objects.isNull(aUpdatedGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("acao", true);

        final var expectedId = aGenre.getId();
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        waitToUpdate();

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aGenre.isActive());
        Assertions.assertNull(aGenre.getDeletedAt());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdatedGenre ->
                Objects.equals(expectedId, aUpdatedGenre.getId())
                        && Objects.equals(expectedName, aUpdatedGenre.getName())
                        && Objects.equals(expectedIsActive, aUpdatedGenre.isActive())
                        && Objects.equals(expectedCategories, aUpdatedGenre.getCategories())
                        && Objects.equals(aGenre.getCreatedAt(), aUpdatedGenre.getCreatedAt())
                        && aGenre.getUpdatedAt().isBefore(aUpdatedGenre.getUpdatedAt())
                        && Objects.nonNull(aUpdatedGenre.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateGenre_thenShouldReturnNotificationException() {

        final var aGenre = Genre.newGenre("Film", true);

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand
                .with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.findById(expectedId)).thenReturn(Optional.of(Genre.with(aGenre)));

        final var actualException = Assertions.assertThrows(NotificationException.class,
                () -> {
                    useCase.execute(aCommand);
                }
        );

//        then

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(0)).existsByIds(any());
        Mockito.verify(genreGateway, times(0)).update(any());

    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_shouldReturnNotificationException() {
        // given
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");

        final var aGenre = Genre.newGenre("acao", true);

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series, documentarios);

        final var expectedErrorCount = 2;
        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(List.of(filmes));

        // when
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));

        Mockito.verify(genreGateway, times(0)).update(any());
    }

}
