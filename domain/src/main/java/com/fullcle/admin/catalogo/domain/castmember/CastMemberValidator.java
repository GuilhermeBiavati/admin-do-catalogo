package com.fullcle.admin.catalogo.domain.castmember;

import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    private static final int NAME_MAX_LEGTH = 255;
    private static final int NAME_MIN_LEGTH = 3;

    private final CastMember castMember;

    public CastMemberValidator(final CastMember aMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = aMember;
    }


    @Override
    public void validate() {
        checkNameConstraints();
        checkTypeContraints();
    }

    private void checkNameConstraints() {
        final var name = this.castMember.getName();

        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LEGTH || length < NAME_MIN_LEGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }

    private void checkTypeContraints() {
        final var type = this.castMember.getType();

        if (type == null) {
            this.validationHandler().append(new Error("'type' should not be null"));
        }
    }

}
