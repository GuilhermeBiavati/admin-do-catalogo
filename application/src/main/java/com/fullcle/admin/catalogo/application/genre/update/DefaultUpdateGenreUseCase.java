package com.fullcle.admin.catalogo.application.genre.update;


import com.fullcle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.genre.Genre;
import com.fullcle.admin.catalogo.domain.genre.GenreGateway;
import com.fullcle.admin.catalogo.domain.genre.GenreID;
import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DefaultUpdateGenreUseCase extends UpdateGenreUseCase {

    private CategoryGateway categoryGateway;
    private GenreGateway genreGateway;

    public DefaultUpdateGenreUseCase(final CategoryGateway categoryGateway, final GenreGateway genreGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public UpdateGenreOutput execute(final UpdateGenreCommand aCommand) {
        final var anId = GenreID.from(aCommand.id());
        final var categories = toCategoryId(aCommand.categories());
        final var aGenre = this.genreGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.append(validateCategories(categories));
        notification.validate(() -> aGenre.update(aCommand.name(), aCommand.isActive(), categories));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate Genre", notification);
        }

        return UpdateGenreOutput.from(this.genreGateway.update(aGenre));
    }

    private List<CategoryID> toCategoryId(List<String> categories) {
        return categories.stream().map(CategoryID::from).toList();
    }

    private Supplier<NotFoundException> notFound(final GenreID anId) {
        return () -> NotFoundException.with(Genre.class, anId);
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

}
