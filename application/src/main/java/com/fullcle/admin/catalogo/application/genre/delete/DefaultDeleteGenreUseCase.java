package com.fullcle.admin.catalogo.application.genre.delete;

import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteGenreUseCase extends DeleteGenreUseCase {

    private final CategoryGeteway CategoryGeteway;

    public DefaultDeleteGenreUseCase(final CategoryGeteway CategoryGeteway) {
        this.CategoryGeteway = Objects.requireNonNull(CategoryGeteway);
    }

    @Override
    public void execute(final String anId) {
        this.CategoryGeteway.deleteById(CategoryID.from(anId));
    }




}
