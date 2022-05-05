package com.fullcle.admin.catalogo.application.category.create;

import com.fullcle.admin.catalogo.domain.category.Category;
import com.fullcle.admin.catalogo.domain.category.CategoryGetway;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;
import com.fullcle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGetway categoryGetway;

    public DefaultCreateCategoryUseCase(final CategoryGetway categoryGetway) {
        this.categoryGetway = Objects.requireNonNull(categoryGetway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var notification = Notification.create();
        final var aCategory = Category.newCategory(aCommand.name(), aCommand.description(), aCommand.isActive());
        aCategory.validate(notification);
        if (notification.hasError()) {

        }

        return CreateCategoryOutput.from(this.categoryGetway.create(aCategory));
    }
}
