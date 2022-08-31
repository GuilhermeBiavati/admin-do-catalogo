package com.fullcle.admin.catalogo.application.castmember.retrieve.get;

import java.util.Objects;
import java.util.function.Supplier;

import com.fullcle.admin.catalogo.domain.castmember.CastMember;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;

public class DefaultGetCastMemberByIdUseCase extends GetCastMemberByIdUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultGetCastMemberByIdUseCase(final CastMemberGateway castMemberGateway) {

        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public CastMemberOutput execute(final String anIn) {

        final var anCastMemberID = CastMemberID.from(anIn);
        return this.castMemberGateway.findById(anCastMemberID).map(CastMemberOutput::from)
                .orElseThrow(notFound(anCastMemberID));
    }

    private Supplier<NotFoundException> notFound(final CastMemberID anId) {

        return () -> NotFoundException.with(CastMember.class, anId);
    }
}
