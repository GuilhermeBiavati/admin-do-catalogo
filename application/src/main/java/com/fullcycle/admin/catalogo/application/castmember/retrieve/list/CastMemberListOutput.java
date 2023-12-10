package com.fullcycle.admin.catalogo.application.castmember.retrieve.list;

import java.time.Instant;

import com.fullcycle.admin.catalogo.domain.castmember.CastMember;
import com.fullcycle.admin.catalogo.domain.castmember.CastMemberType;

public record CastMemberListOutput(String id, String name, CastMemberType type, Instant createdAt,
                                   Instant updatedAt) {

    public static CastMemberListOutput from(CastMember castMember) {

        return new CastMemberListOutput(castMember.getId().getValue(), castMember.getName(), castMember.getType(),
                castMember.getCreatedAt(), castMember.getUpdatedAt());
    }
}
