package com.fullcle.admin.catalogo.application.castmember.retrieve.list;

import java.util.Objects;

import com.fullcle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;

public class DefaultListCastMemberUseCase extends ListCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultListCastMemberUseCase(final CastMemberGateway castMemberGateway) {

        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(final SearchQuery aQuery) {

        return this.castMemberGateway.findAll(aQuery).map(CastMemberListOutput::from);
    }
}
