package com.fullcle.admin.catalogo.domain.castmember;

import com.fullcle.admin.catalogo.domain.Identifier;
import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.validation.ValidationHandler;
import com.fullcle.admin.catalogo.domain.validation.Validator;

import java.util.Objects;
import java.util.UUID;

public class CastMemberValidator extends Validator {

    private final CastMember castMember;

    public CastMemberValidator(final CastMember aMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = aMember;
    }


    @Override
    public void validate() {

    }
}
