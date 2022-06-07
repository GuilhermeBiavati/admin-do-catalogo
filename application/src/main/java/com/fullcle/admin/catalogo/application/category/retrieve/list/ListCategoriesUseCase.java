package com.fullcle.admin.catalogo.application.category.retrieve.list;

import com.fullcle.admin.catalogo.application.UseCase;
import com.fullcle.admin.catalogo.domain.category.CategorySearchQuery;
import com.fullcle.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
