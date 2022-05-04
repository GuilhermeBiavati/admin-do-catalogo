package com.fullcle.admin.catalogo.domain.category;

import com.fullcle.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGetway {
    Category create(Category aCategory);

    void deleteById(CategoryID anId);

    Optional<Category> findById(CategoryID anId);

    Category update(Category aCategory);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
