package com.fullcle.admin.catalogo.application.castmember.retrieve.get;

import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record CastMemberOutput(
        String id,
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static CastMemberOutput from(final Genre aGenre) {
        return new CastMemberOutput(
                aGenre.getId().getValue(),
                aGenre.getName(),
                aGenre.isActive(),
                aGenre.getCategories().stream().map(CategoryID::getValue).toList(),
                aGenre.getCreatedAt(),
                aGenre.getUpdatedAt(),
                aGenre.getDeletedAt()
        );
    }
}
