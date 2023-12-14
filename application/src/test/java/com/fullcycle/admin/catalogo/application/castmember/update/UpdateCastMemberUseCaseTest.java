package com.fullcycle.admin.catalogo.application.castmember.update;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberType;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.exceptions.NotificationException;

public class UpdateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCastMemberUseCase useCase;
    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {

        return List.of(castMemberGateway);
    }

    //    Teste do caminho feliz
    //    Teste passando uma propriedade inválida (name)
    //    Teste atualizando uma categoria para inativa
    //    Teste simulando um erro generico vindo do getway
    //    Teste atualizar categoria passando id inválido

    @Test
    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnCastMemberId() {

        final var aCastMember = CastMember.newMember("Film", CastMemberType.DIRECTOR);

        final var expectedName = Fixture.name();
        final var expectedType = CastMemberType.ACTOR;
        final var expectedId = aCastMember.getId();

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType
        );

        Mockito.when(castMemberGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCastMember.clone()));

        Mockito.when(castMemberGateway.update(ArgumentMatchers.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(castMemberGateway, Mockito.times(1)).findById(eq(expectedId));
        Mockito.verify(castMemberGateway, Mockito.times(1)).update(ArgumentMatchers.argThat(
                aUpdatedCastMember ->
                        Objects.equals(expectedName, aUpdatedCastMember.getName())
                                && Objects.equals(expectedType, aUpdatedCastMember.getType())
                                && Objects.equals(expectedId, aUpdatedCastMember.getId())
                                && Objects.equals(aCastMember.getCreatedAt(), aUpdatedCastMember.getCreatedAt())
                                && Objects.nonNull(aUpdatedCastMember.getUpdatedAt())
                                && aCastMember.getUpdatedAt().isBefore(aUpdatedCastMember.getUpdatedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCastMember_thenShouldThrowsNotoficationException() {

        final var aCastMember = CastMember.newMember("Film", CastMemberType.DIRECTOR);

        final var expectedId = aCastMember.getId();
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        when(castMemberGateway.findById(expectedId)).thenReturn(Optional.of(aCastMember.clone()));

        final var notification = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(castMemberGateway, times(0)).update(any());

    }

//    @Test
//    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
//
//        final var aCastMember = CastMember.newMember("Ação", CastMemberType.ACTOR);
//
//        final var expectedId = aCastMember.getId();
//        final var expectedName = Fixture.name();
//        final var expectedType = CastMemberType.DIRECTOR;
//        final var expectedErrorMessage = "Gateway error";
//        final var expectedErrorCount = 1;
//
//        final var aCommand = UpdateCastMemberCommand.with(
//                expectedId.getValue(),
//                expectedName,
//                expectedType);
//
//        Mockito.when(castMemberGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCastMember.clone()));
//
//        when(castMemberGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));
//
//        final var actualOutput = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));
//
////        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
//        Assertions.assertEquals(expectedErrorMessage, actualOutput.getMessage());
//
//        Mockito.verify(castMemberGateway, Mockito.times(1)).update(ArgumentMatchers.argThat(
//                aUpdatedCastMember -> Objects.equals(expectedName, aUpdatedCastMember.getName())
//                        && Objects.equals(expectedType, aUpdatedCastMember.getType())
//                        && Objects.equals(expectedId, aUpdatedCastMember.getId())
//                        && Objects.equals(aCastMember.getCreatedAt(), aUpdatedCastMember.getCreatedAt())
//                        && Objects.nonNull(aUpdatedCastMember.getUpdatedAt())
//                        && aCastMember.getUpdatedAt().isBefore(aUpdatedCastMember.getUpdatedAt())
//        ));
//    }

    @Test
    public void givenAValidCommandWithInvalidID_whenCallsUpdateCastMember_shouldReturnNotFoundException() {

        final var expectedId = CastMemberID.from("123");
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();
        final var expectedErrorMessage = String.format("CastMember with ID %s was not found", expectedId.getValue());

        final var aCommand = UpdateCastMemberCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedType);

        Mockito.when(castMemberGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(castMemberGateway, Mockito.times(1)).findById(eq(expectedId));
        Mockito.verify(castMemberGateway, Mockito.times(0)).update(any());
    }

}
