package com.fullcycle.admin.catalogo.application.castmember.update;

import com.fullcycle.admin.catalogo.domain.castmember.CastMember;

public record UpdateCastMemberOutput(String id) {

    public static UpdateCastMemberOutput from(final String anId) {

        return new UpdateCastMemberOutput(anId);
    }

    public static UpdateCastMemberOutput from(final CastMember aCastMember) {

        return new UpdateCastMemberOutput(aCastMember.getId().getValue());
    }
}
