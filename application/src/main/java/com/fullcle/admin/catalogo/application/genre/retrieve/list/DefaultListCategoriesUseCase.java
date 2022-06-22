package com.fullcle.admin.catalogo.application.genre.retrieve.list;

import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGeteway CategoryGeteway;

    public DefaultListCategoriesUseCase(final CategoryGeteway CategoryGeteway) {
        this.CategoryGeteway = Objects.requireNonNull(CategoryGeteway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery aQuery) {
        return this.CategoryGeteway.findAll(aQuery).map(CategoryListOutput::from);
    }
}
