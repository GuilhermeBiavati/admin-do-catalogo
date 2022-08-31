package com.fullcle.admin.catalogo.application.castmember.retrieve.list;

import com.fullcle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListCastMemberUseCase extends ListCastMemberUseCase {
    private final GenreGateway genreGateway;

    public DefaultListCastMemberUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(final SearchQuery aQuery) {
        return this.genreGateway.findAll(aQuery).map(GenreListOutput::from);
    }
}
