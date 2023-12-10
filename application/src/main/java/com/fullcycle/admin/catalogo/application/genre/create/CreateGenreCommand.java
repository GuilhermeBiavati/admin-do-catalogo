package com.fullcycle.admin.catalogo.application.genre.create;

import java.util.List;

public record CreateGenreCommand(
        String name,
        boolean isActive,
        List<String> categories
) {
    public static CreateGenreCommand with(
            final String aName,
            final List<String> aCategories,
            final Boolean isActive
            ) {
        return new CreateGenreCommand(aName, isActive != null ? isActive : true, aCategories);
    }
}