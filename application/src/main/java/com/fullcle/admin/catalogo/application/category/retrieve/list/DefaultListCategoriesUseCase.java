package com.fullcle.admin.catalogo.application.category.retrieve.list;

import com.fullcle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcle.admin.catalogo.domain.pagination.SearchQuery;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;

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
