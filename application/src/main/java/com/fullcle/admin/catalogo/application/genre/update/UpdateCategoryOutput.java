package com.fullcle.admin.catalogo.application.genre.update;

import com.fullcle.admin.catalogo.domain.category.Category;

public record UpdateCategoryOutput(String id) {

    public static UpdateCategoryOutput from(final String anId) {
        return new UpdateCategoryOutput(anId);
    }
    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId().getValue());
    }
}
