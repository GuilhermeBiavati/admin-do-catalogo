package com.fullcycle.admin.catalogo.application.castmember.retrieve.list;

import com.fullcycle.admin.catalogo.application.UseCaseTest;
import com.fullcycle.admin.catalogo.domain.Fixture;
import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListCastMembersUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListCastMembersUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCategories_thenShouldReturnAll() {

        final var members = List.of(
                CastMember.newMember(Fixture.name(), Fixture.CastMembers.type()),
                CastMember.newMember(Fixture.name(), Fixture.CastMembers.type())
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "algo";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedTotal = 2;
        final var expectedItems = members.stream().map(
                CastMemberListOutput::from
        ).toList();

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, members);

        when(castMemberGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);


        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedItems, actualResult.items());

        verify(castMemberGateway).findAll(eq(aQuery));

    }

    @Test
    public void givenAValidQuery_whenCallsListCastMemberAndIsEmpty_thenShould() {

        final var members = List.<CastMember>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, members.size(), members);

        final var expectedItemsCount = 0;
        final var expectedResult = expectedPagination.map(CastMemberListOutput::from);

        when(castMemberGateway.findAll(eq(aQuery))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(members.size(), actualResult.total());

        verify(castMemberGateway).findAll(eq(aQuery));

    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_thenShouldReturnException() {

        final var expectedErrorMessage = "Gateway error";
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";


        when(castMemberGateway.findAll(any())).thenThrow(new IllegalStateException(expectedErrorMessage));


        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aQuery));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(castMemberGateway).findAll(eq(aQuery));

    }


}
