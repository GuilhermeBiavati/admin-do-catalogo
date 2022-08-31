package com.fullcle.admin.catalogo.application.castmember.retrieve.list;

import com.fullcle.admin.catalogo.application.UseCase;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListCastMemberUseCase extends UseCase<SearchQuery, Pagination<CastMemberListOutput>> {
}
