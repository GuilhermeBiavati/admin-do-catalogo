package com.fullcle.admin.catalogo.domain.genre;

import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {
    Genre create(Genre aGenre);

    void deleteById(GenreID anId);

    Optional<Genre> findById(GenreID anId);

    Genre update(Genre aGenre);

    Pagination<Genre> findAll(SearchQuery aQuery);
}
