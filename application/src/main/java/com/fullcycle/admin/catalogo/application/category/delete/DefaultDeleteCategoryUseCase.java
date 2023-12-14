package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway CategoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway CategoryGateway) {
        this.CategoryGateway = Objects.requireNonNull(CategoryGateway);
    }

    @Override
    public void execute(final String anId) {
        this.CategoryGateway.deleteById(CategoryID.from(anId));
    }




}
