package com.fullcle.admin.catalogo.application.category.retrieve.list;

import com.fullcle.admin.catalogo.domain.category.CategoryGetway;
import com.fullcle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGetway categoryGetway;

    public DefaultListCategoriesUseCase(final CategoryGetway categoryGetway) {
        this.categoryGetway = Objects.requireNonNull(categoryGetway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {
        return this.categoryGetway.findAll(aQuery).map(CategoryListOutput::from);
    }
}
