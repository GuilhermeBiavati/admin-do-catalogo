package com.fullcle.admin.catalogo.application.category.delete;

import com.fullcle.admin.catalogo.domain.category.Category;
import com.fullcle.admin.catalogo.domain.category.CategoryGetway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGetway categoryGetway;

    public DefaultDeleteCategoryUseCase(final CategoryGetway categoryGetway) {
        this.categoryGetway = Objects.requireNonNull(categoryGetway);
    }

    @Override
    public void execute(final String anId) {
        this.categoryGetway.deleteById(CategoryID.from(anId));
    }




}
