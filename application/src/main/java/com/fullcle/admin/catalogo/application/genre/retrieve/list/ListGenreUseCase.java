package com.fullcle.admin.catalogo.application.genre.retrieve.list;

import com.fullcle.admin.catalogo.application.UseCase;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {
}
