package com.fullcle.admin.catalogo.application;

import com.fullcle.admin.catalogo.domain.castmember.CastMemberType;
import com.github.javafaker.Faker;

/**
 * @author guilherme.cagnini
 * @since 1.0 (30/08/2022)
 */
public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String uuid() {

        return FAKER.internet().uuid();
    }

    public static String name() {

        return FAKER.name().fullName();
    }

    public static class CastMember {

        public static CastMemberType type() {

            return FAKER.options().option(CastMemberType.ACTOR, CastMemberType.DIRECTOR);
        }
    }

}
