package com.fullcle.admin.catalogo.application.castmember.update;

import java.util.Objects;
import java.util.function.Supplier;

import com.fullcle.admin.catalogo.domain.castmember.CastMember;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberGateway;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcle.admin.catalogo.domain.exceptions.NotFoundException;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

public class DefaultUpdateCastMemberUseCase extends UpdateCastMemberUseCase {

    private CastMemberGateway castMemberGateway;

    public DefaultUpdateCastMemberUseCase(final CastMemberGateway castMemberGateway) {

        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public UpdateCastMemberOutput execute(final UpdateCastMemberCommand aCommand) {

        final var anId = CastMemberID.from(aCommand.id());
        final var aMember = this.castMemberGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        notification.validate(() -> aMember.update(aCommand.name(), aCommand.type()));

        if (notification.hasError()) {
            throw new NotificationException("Could not create Aggregate CastMember", notification);
        }

        return UpdateCastMemberOutput.from(this.castMemberGateway.update(aMember));
    }

    private Supplier<NotFoundException> notFound(final CastMemberID anId) {

        return () -> NotFoundException.with(CastMember.class, anId);
    }

}
