package com.fullcycle.admin.catalogo.application.category.retrieve.list;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway CategoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway CategoryGateway) {
        this.CategoryGateway = Objects.requireNonNull(CategoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery aQuery) {
        return this.CategoryGateway.findAll(aQuery).map(CategoryListOutput::from);
    }
}
