package com.fullcle.admin.catalogo.application.genre.create;


import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.genre.Genre;
import com.fullcle.admin.catalogo.domain.genre.GenreGeteway;
import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase {

    private final CategoryGeteway categoryGeteway;
    private final GenreGeteway genreGeteway;

    public DefaultCreateGenreUseCase(final CategoryGeteway categoryGeteway, final GenreGeteway genreGeteway) {
        this.categoryGeteway = Objects.requireNonNull(categoryGeteway);
        this.genreGeteway = Objects.requireNonNull(genreGeteway);
    }

    @Override
    public CreateGenreOutput execute(final CreateGenreCommand aCommand) {
        final var notification = Notification.create();

        notification.append(validateCategories(toCategoryID(aCommand.categories())));

        final var aGenre = notification.validate(() -> Genre.newGenre(aCommand.name(), aCommand.isActive()));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        return CreateGenreOutput.from(this.genreGeteway.create(aGenre));
    }

    private ValidationHandler validateCategories(final List<CategoryID> categories) {
        final var notification = Notification.create();

        if (categories == null || categories.isEmpty()) {
            return notification;
        }

        final var retrievedIds = categoryGeteway.existsByIds(categories);

        if (categories.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(categories);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream().map(CategoryID::getValue).collect(Collectors.joining(", "));

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));


        }

        return notification;
    }

    private List<CategoryID> toCategoryID(final List<String> categories) {
        return categories.stream().map(CategoryID::from).toList();
    }


}
