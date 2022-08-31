package com.fullcle.admin.catalogo.application.castmember.retrieve.list;

import java.time.Instant;

import com.fullcle.admin.catalogo.domain.castmember.CastMember;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberID;
import com.fullcle.admin.catalogo.domain.castmember.CastMemberType;

public record CastMemberListOutput(CastMemberID id, String name, CastMemberType type, Instant createdAt,
                                   Instant updatedAt) {

    public static CastMemberListOutput from(CastMember castMember) {

        return new CastMemberListOutput(castMember.getId(), castMember.getName(), castMember.getType(),
                castMember.getCreatedAt(), castMember.getUpdatedAt());
    }
}
