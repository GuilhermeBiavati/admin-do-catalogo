package com.fullcle.admin.catalogo.application.castmember.delete;

import java.util.Objects;

import com.fullcle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberID;

public class DefaultDeleteCastMemberUseCase extends DeleteCastMemberUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultDeleteCastMemberUseCase(final CastMemberGateway castMemberGateway) {

        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public void execute(final String anId) {

        this.castMemberGateway.deleteById(CastMemberID.from(anId));
    }

}
