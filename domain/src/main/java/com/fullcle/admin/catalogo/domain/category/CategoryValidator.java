package com.fullcle.admin.catalogo.domain.category;

import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public static final int NAME_MAX_LEGTH = 255;
    public static final int NAME_MIN_LEGTH = 3;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
        }

        final int length = name.trim().length();

        if (length > NAME_MAX_LEGTH || length < NAME_MIN_LEGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }


    }
}
