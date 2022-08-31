package com.fullcle.admin.catalogo.application.castmember.retrieve.get;

import com.fullcle.admin.catalogo.domain.castmember.CastMember;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberType;

public record CastMemberOutput(
        String name,
        CastMemberType type
) {

    public static CastMemberOutput from(final CastMember aMember) {

        return new CastMemberOutput(
                aMember.getName(),
                aMember.getType()
        );
    }
}
