package com.fullcle.admin.catalogo.application.category.retrieve.get;

import com.fullcle.admin.catalogo.domain.category.CategoryGetway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcle.admin.catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGetway categoryGetway;

    public DefaultGetCategoryByIdUseCase(final CategoryGetway categoryGetway) {
        this.categoryGetway = Objects.requireNonNull(categoryGetway);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var anCategoryID = CategoryID.from(anIn);
        return this.categoryGetway.findById(anCategoryID).map(CategoryOutput::from).orElseThrow(notFound(anCategoryID));
    }

    private Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(anId.getValue())));
    }
}
