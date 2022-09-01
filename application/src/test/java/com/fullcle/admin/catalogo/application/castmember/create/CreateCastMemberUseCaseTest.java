package com.fullcle.admin.catalogo.application.castmember.create;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fullcle.admin.catalogo.application.Fixture;
import com.fullcle.admin.catalogo.application.UseCaseTest;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberType;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;

public class CreateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateCastMemberUseCase useCase;
    @Mock
    private CastMemberGateway castMemberGateway;

    @BeforeEach
    void cleanUp() {

        Mockito.reset(castMemberGateway);
    }

    @Override
    protected List<Object> getMocks() {

        return List.of(castMemberGateway);
    }

//    Teste do caminho feliz
//    Teste passando uma propriedade inválida (name)
//    Teste criando uma categoria inativa
//    Teste simulando um erro generico vindo do getway

    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnCastMemberId() {
//        given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        //when
        when(castMemberGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

//        then
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(castMemberGateway, times(1))
                .create(
                        argThat(
                                aCastMember ->
                                        Objects.equals(expectedName, aCastMember.getName())
                                                && Objects.equals(expectedType, aCastMember.getType())
                                                && Objects.nonNull(aCastMember.getId())
                                                && Objects.nonNull(aCastMember.getCreatedAt())
                                                && Objects.nonNull(aCastMember.getUpdatedAt())
                        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCastMember_thenShouldReturnDomainException() {

        final String expectedName = null;
        final var expectedType = Fixture.CastMember.type();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        final var actualException = Assertions.assertThrows(NotificationException.class,
                () -> useCase.execute(aCommand));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway, times(0)).create(any());

    }

    @Test
    public void givenAInvalidType_whenCallsCreateCastMember_thenShouldReturnDomainException() {

        final String expectedName = Fixture.name();
        final CastMemberType expectedType = null;
        final var expectedErrorMessage = "'type' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        final var actualException = Assertions.assertThrows(NotificationException.class,
                () -> useCase.execute(aCommand));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(castMemberGateway, times(0)).create(any());

    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {

        final var expectedName = "Filmes";
        final var expectedType = Fixture.CastMember.type();
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCastMemberCommand.with(expectedName, expectedType);

        when(castMemberGateway.create(any())).thenThrow(new IllegalStateException("Gateway error"));

        final var actualOutput = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

//        Assertions.assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualOutput.getMessage());

        Mockito.verify(castMemberGateway, times(1))
                .create(argThat(aCastMember ->
                        Objects.equals(expectedName, aCastMember.getName())
                                && Objects.equals(expectedType, aCastMember.getType())
                                && Objects.nonNull(aCastMember.getId())
                                && Objects.nonNull(aCastMember.getCreatedAt())
                                && Objects.nonNull(aCastMember.getUpdatedAt())
                ));
    }
}
