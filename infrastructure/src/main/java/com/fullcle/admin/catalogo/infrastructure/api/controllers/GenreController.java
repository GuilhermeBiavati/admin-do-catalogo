package com.fullcle.admin.catalogo.infrastructure.api.controllers;

import com.fullcle.admin.catalogo.application.genre.create.CreateGenreCommand;
import com.fullcle.admin.catalogo.application.genre.create.CreateGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.fullcle.admin.catalogo.application.genre.retrieve.list.ListGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.update.UpdateGenreCommand;
import com.fullcle.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcle.admin.catalogo.infrastructure.api.GenreAPI;
import com.fullcle.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.fullcle.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import com.fullcle.admin.catalogo.infrastructure.genre.models.GenreResponse;
import com.fullcle.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
import com.fullcle.admin.catalogo.infrastructure.genre.presenters.GenreApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class GenreController implements GenreAPI {

    private final CreateGenreUseCase createGenreUseCase;
    private final GetGenreByIdUseCase getGenreByIdUseCase;
    private final UpdateGenreUseCase updateGenreUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;
    private final ListGenreUseCase listGenreUseCase;

    public GenreController(
            final CreateGenreUseCase createGenreUseCase,
            final GetGenreByIdUseCase getGenreByIdUseCase,
            final UpdateGenreUseCase updateGenreUseCase,
            final DeleteGenreUseCase deleteGenreUseCase,
            final ListGenreUseCase listGenreUseCase
    ) {
        this.createGenreUseCase = Objects.requireNonNull(createGenreUseCase);
        this.getGenreByIdUseCase = Objects.requireNonNull(getGenreByIdUseCase);
        this.updateGenreUseCase = Objects.requireNonNull(updateGenreUseCase);
        this.deleteGenreUseCase = Objects.requireNonNull(deleteGenreUseCase);
        this.listGenreUseCase = Objects.requireNonNull(listGenreUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
        final var aCommand = CreateGenreCommand.with(
                input.name(),
                input.categories(),
                input.isActive()
        );
        final var output = this.createGenreUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listGenreUseCase.execute(new SearchQuery(page, perPage, search, sort, direction))
                .map(GenreApiPresenter::present);
    }

    @Override
    public GenreResponse getById(final String id) {
        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateGenreRequest input) {
        final var aCommand = UpdateGenreCommand.with(
                id,
                input.name(),
                input.isActive(),
                input.categories()
        );
        final var output = this.updateGenreUseCase.execute(aCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteGenreUseCase.execute(anId);
    }
}
