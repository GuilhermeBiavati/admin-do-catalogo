package com.fullcle.admin.catalogo.application.genre.create;


import com.fullcle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.genre.Genre;
import com.fullcle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultCreateGenreUseCase extends CreateGenreUseCase {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public DefaultCreateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public CreateGenreOutput execute(final CreateGenreCommand aCommand) {
        final var notification = Notification.create();
        final var categories = toCategoryID(aCommand.categories());

        notification.append(validateCategories(categories));

        final var aGenre = notification.validate(() -> Genre.newGenre(aCommand.name(), aCommand.isActive()));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        aGenre.addCategories(categories);

        return CreateGenreOutput.from(this.genreGateway.create(aGenre));
    }

    private ValidationHandler validateCategories(final List<CategoryID> categories) {
        final var notification = Notification.create();

        if (categories == null || categories.isEmpty()) {
            return notification;
        }

        final var retrievedIds = categoryGateway.existsByIds(categories);

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
