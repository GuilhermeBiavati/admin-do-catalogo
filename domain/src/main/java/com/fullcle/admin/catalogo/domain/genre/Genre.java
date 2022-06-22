package com.fullcle.admin.catalogo.domain.genre;

import com.fullcle.admin.catalogo.domain.AggregateRoot;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.utils.InstantUtils;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Genre extends AggregateRoot<GenreID> implements Cloneable {
    private String name;

    private List<CategoryID> categories;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Genre(
            final GenreID andId,
            final String aName,
            List<CategoryID> categories,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate) {
        super(andId);
        this.name = aName;
        this.categories = categories;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updatedAt' should not be null");
        this.deletedAt = aDeleteDate;
        selfValidate();
    }

    public static Genre newGenre(final String aName, final boolean isActive) {
        final var id = GenreID.unique();
        final var now = InstantUtils.now();
        final var deletedAT = isActive ? null : now;
        return new Genre(id, aName, new ArrayList<>(), isActive, now, now, deletedAT);
    }

    public static Genre with(final GenreID anId,
                             final String name,
                             final boolean active,
                             final Instant createdAt,
                             final Instant updatedAt,
                             final Instant deletedAt) {
        return new Genre(
                anId,
                name,
                new ArrayList<>(),
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Genre with(final Genre aGenre) {
        return with(
                aGenre.getId(),
                aGenre.name,
                aGenre.isActive(),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }


    @Override
    public void validate(final ValidationHandler handler) {
        new GenreValidator(this, handler).validate();
    }

    public Genre deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }
        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Genre update(final String aName, final boolean isActive, final List<CategoryID> categories) {

        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.categories = new ArrayList<>(categories != null ? categories : Collections.emptyList());
        this.updatedAt = InstantUtils.now();

        selfValidate();

        return this;
    }


    public GenreID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Genre clone() {
        try {
            return (Genre) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Genre addCategory(final CategoryID aCategoryID) {
        if (aCategoryID == null) {
            return this;
        }

        this.categories.add(aCategoryID);
        this.updatedAt = InstantUtils.now();

        return this;
    }

    public Genre removeCategory(final CategoryID aCategoryID) {
        if (aCategoryID == null) {
            return this;
        }

        this.categories.remove(aCategoryID);
        this.updatedAt = InstantUtils.now();

        return this;
    }


    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("", notification);
        }
    }

}