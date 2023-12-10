package com.fullcycle.admin.catalogo.application.castmember.retrieve.get;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.Fixture;
import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetCastMemberByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetCastMemberByIdUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCastMember_shouldReturnIt() {

        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aCastMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aCastMember.getId();

        when(castMemberGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aCastMember.clone()));

        final var actualCastMember = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId.getValue(), actualCastMember.id());
        Assertions.assertEquals(expectedName, actualCastMember.name());
        Assertions.assertEquals(expectedType, actualCastMember.type());
        Assertions.assertEquals(aCastMember.getCreatedAt(), actualCastMember.createdAt());
        Assertions.assertEquals(aCastMember.getUpdatedAt(), actualCastMember.updatedAt());

    }

    @Test
    public void givenAInvalidId_whenCallsGetCastMemberAndNotExists_shouldReturnNotFoundException() {

        final var expectedErrorMessage = "CastMember with ID 123 was not found";
        final var expectedId = CastMemberID.from("123");

        when(castMemberGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(castMemberGateway).findById(eq(expectedId));

    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = CastMemberID.from("123");

        when(castMemberGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(castMemberGateway).findById(eq(expectedId));

    }
}
