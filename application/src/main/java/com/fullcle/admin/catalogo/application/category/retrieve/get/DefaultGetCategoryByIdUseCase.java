package com.fullcle.admin.catalogo.application.category.retrieve.get;

import com.fullcle.admin.catalogo.domain.category.Category;
import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;
import com.fullcle.admin.catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGeteway CategoryGeteway;

    public DefaultGetCategoryByIdUseCase(final CategoryGeteway CategoryGeteway) {
        this.CategoryGeteway = Objects.requireNonNull(CategoryGeteway);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var anCategoryID = CategoryID.from(anIn);
        return this.CategoryGeteway.findById(anCategoryID).map(CategoryOutput::from).orElseThrow(notFound(anCategoryID));
    }

    private Supplier<NotFoundException> notFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
