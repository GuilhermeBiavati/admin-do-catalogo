package com.fullcle.admin.catalogo.application.castmember.update;

import com.fullcle.admin.catalogo.domain.genre.Genre;

public record UpdateCastMemberOutput(String id) {

    public static UpdateCastMemberOutput from(final String anId) {
        return new UpdateCastMemberOutput(anId);
    }
    public static UpdateCastMemberOutput from(final Genre aGenre) {
        return new UpdateCastMemberOutput(aGenre.getId().getValue());
    }
}
