package com.fullcle.admin.catalogo.infrastructure.category.presenters;

import com.fullcle.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import com.fullcle.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import com.fullcle.admin.catalogo.infrastructure.api.CategoryApi;
import com.fullcle.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import com.fullcle.admin.catalogo.infrastructure.category.models.CategoryResponse;

import java.util.function.Function;

public interface CategoryApiPresenter {

    Function<CategoryOutput, CategoryResponse> present = output -> new CategoryResponse(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt()
    );

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
