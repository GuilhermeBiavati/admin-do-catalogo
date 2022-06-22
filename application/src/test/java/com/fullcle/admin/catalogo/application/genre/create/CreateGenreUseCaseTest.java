package com.fullcle.admin.catalogo.application.genre.create;



import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
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

//    @Test
//    public void givenAInvalidName_whenCallsCreateGenre_thenShouldReturnDomainException() {
//        final String expectedName = null;
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//        final var expectedErrorMessage = "'name' should not be null";
//        final var expectedErrorCount = 1;
//
//        final var aCommand = CreateGenreCommand.with(expectedName, expectedDescription, expectedIsActive);
//
//        final var notification = useCase.execute(aCommand).getLeft();
//
//        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
//        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
//
//        Mockito.verify(genreGeteway, times(0)).create(any());
//
//    }


//
//    @Test
//    public void givenAValidCommandWithInactiveGenre_whenCallsCreateGenre_shouldReturnInactiveGenreId() {
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = false;
//
//        final var aCommand = CreateGenreCommand.with(expectedName, expectedDescription, expectedIsActive);
//
//        when(genreGeteway.create(any())).thenAnswer(returnsFirstArg());
//
//        final var actualOutput = useCase.execute(aCommand).get();
//
//        Assertions.assertNotNull(actualOutput);
//        Assertions.assertNotNull(actualOutput.id());
//
//        Mockito.verify(genreGeteway, times(1))
//                .create(argThat(aGenre -> Objects.equals(expectedName, aGenre.getName())
//                        && Objects.equals(expectedDescription, aGenre.getDescription())
//                        && Objects.equals(expectedIsActive, aGenre.isActive())
//                        && Objects.nonNull(aGenre.getId())
//                        && Objects.nonNull(aGenre.getCreatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.nonNull(aGenre.getDeletedAt())
//                ));
//    }
//
//    @Test
//    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
//        final var expectedName = "Filmes";
//        final var expectedDescription = "A categoria mais assistida";
//        final var expectedIsActive = true;
//        final var expectedErrorMessage = "Gateway error";
//        final var expectedErrorCount = 1;
//
//        final var aCommand = CreateGenreCommand.with(expectedName, expectedDescription, expectedIsActive);
//
//        when(genreGeteway.create(any())).thenThrow(new IllegalStateException("Gateway error"));
//
//        final var notification = useCase.execute(aCommand).getLeft();
//
//        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
//        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
//
//        Mockito.verify(genreGeteway, times(1))
//                .create(argThat(aGenre -> Objects.equals(expectedName, aGenre.getName())
//                        && Objects.equals(expectedDescription, aGenre.getDescription())
//                        && Objects.equals(expectedIsActive, aGenre.isActive())
//                        && Objects.nonNull(aGenre.getId())
//                        && Objects.nonNull(aGenre.getCreatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.nonNull(aGenre.getUpdatedAt())
//                        && Objects.isNull(aGenre.getDeletedAt())
//                ));
//    }
//
    private List<String> asString(final List<CategoryID> categories) {
        return categories.stream().map(CategoryID::getValue).toList();
    }
}
