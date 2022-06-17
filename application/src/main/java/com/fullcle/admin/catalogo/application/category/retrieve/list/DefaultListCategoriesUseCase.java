package com.fullcle.admin.catalogo.application.category.retrieve.list;

import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGeteway CategoryGeteway;

    public DefaultListCategoriesUseCase(final CategoryGeteway CategoryGeteway) {
        this.CategoryGeteway = Objects.requireNonNull(CategoryGeteway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {
        return this.CategoryGeteway.findAll(aQuery).map(CategoryListOutput::from);
    }
}
