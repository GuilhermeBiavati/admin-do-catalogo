package com.fullcycle.admin.catalogo.application.castmember.delete;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.Fixture;
import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {

        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aCastMember.getId();

        doNothing().when(castMemberGateway).deleteById(any());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        verify(castMemberGateway, times(1)).deleteById(eq(expectedId));

    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCastMember_shouldBeOK() {

        final var expectedId = CastMemberID.from("123");

        doNothing().when(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        verify(castMemberGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {

        final var aCastMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());
        final var expectedId = aCastMember.getId();

        doThrow(new IllegalStateException("Gateway error")).when(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        verify(castMemberGateway, times(1)).deleteById(eq(expectedId));
    }

}
