package com.fullcle.admin.catalogo.application.castmember.delete;

import com.fullcle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcle.admin.catalogo.domain.genre.GenreID;

import java.util.Objects;

public class DefaultDeleteCastMemberUseCase extends DeleteCastMemberUseCase {

    private final GenreGateway genreGateway;

    public DefaultDeleteCastMemberUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public void execute(final String anId) {
        this.genreGateway.deleteById(GenreID.from(anId));
    }

}
