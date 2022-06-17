package com.fullcle.admin.catalogo.application.category.delete;

import com.fullcle.admin.catalogo.domain.category.Category;
import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGeteway CategoryGeteway;

    public DefaultDeleteCategoryUseCase(final CategoryGeteway CategoryGeteway) {
        this.CategoryGeteway = Objects.requireNonNull(CategoryGeteway);
    }

    @Override
    public void execute(final String anId) {
        this.CategoryGeteway.deleteById(CategoryID.from(anId));
    }




}
