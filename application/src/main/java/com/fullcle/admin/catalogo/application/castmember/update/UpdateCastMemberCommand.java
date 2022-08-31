package com.fullcle.admin.catalogo.application.castmember.update;

import java.util.List;

public record UpdateCastMemberCommand(
        String id,
        String name,
        boolean isActive,
        List<String> categories
) {

    public static UpdateCastMemberCommand with(
            final String anId,
            final String aName,
            final Boolean isActive,
            final List<String> categories
    ) {
        return new UpdateCastMemberCommand(anId, aName, isActive != null ? isActive : true, categories);
    }


}
