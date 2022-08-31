package com.fullcle.admin.catalogo.application.castmember.create;

import com.fullcle.admin.catalogo.application.UseCase;

public sealed abstract class CreateCastMemberUseCase extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput> permits DefaultCreateCastMemberUseCase {
}
