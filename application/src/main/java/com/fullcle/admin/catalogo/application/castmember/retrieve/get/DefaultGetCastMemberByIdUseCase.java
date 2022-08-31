package com.fullcle.admin.catalogo.application.castmember.retrieve.get;

import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;
import com.fullcle.admin.catalogo.domain.genre.Genre;
import com.fullcle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcle.admin.catalogo.domain.genre.GenreID;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCastMemberByIdUseCase extends GetCastMemberByIdUseCase {

    private final GenreGateway genreGateway;

    public DefaultGetCastMemberByIdUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public CastMemberOutput execute(final String anIn) {
        final var anGenreID = GenreID.from(anIn);
        return this.genreGateway.findById(anGenreID).map(CastMemberOutput::from).orElseThrow(notFound(anGenreID));
    }

    private Supplier<NotFoundException> notFound(final GenreID anId) {
        return () -> NotFoundException.with(Genre.class, anId);
    }
}
