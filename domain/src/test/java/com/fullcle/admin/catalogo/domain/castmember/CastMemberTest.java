package com.fullcle.admin.catalogo.domain.castmember;

import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CastMemberTest {

    @Test
    public void givenAvalidParams_whenCallsNewMenber_thenInstantiateACastMenber() {
        final var expedtedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember = CastMember.newMember(expedtedName, expectedType);

        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());
        Assertions.assertEquals(expedtedName, actualMember.getName());
        Assertions.assertNotNull(actualMember.getCreatedAt());
        Assertions.assertNotNull(actualMember.getUpdatedAt());
        Assertions.assertEquals(actualMember.getCreatedAt(), actualMember.getUpdatedAt());
    }

    @Test
    public void givenAInvalidNullName_whenCallsNewMember_shouldReceiveANotification() {

        final String expedtedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAInvalidEmptyName_whenCallsNewMember_shouldReceiveANotification() {

        final String expedtedName = "          ";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAInvalidWithLengthMoreThan255_whenCallsNewMember_shouldReceiveANotification() {
        final String expedtedName = """
                fsadfasdfasdasd fas dfas df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                sd fasdf asd fasdf asd f""";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNullType_whenCallsNewMember_shouldReceiveANotification() {
        final String expedtedName = "Vin diesel";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> CastMember.newMember(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidCastMember_whenCallUpdate_shouldReceiveUpdated() {
        final String expedtedName = "Vin diesel";
        final CastMemberType expectedType = CastMemberType.ACTOR;

        final var actualMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);

        Assertions.assertNotNull(actualMember);

        final var actualID = actualMember.getId();
        final var actualCreatedAt = actualMember.getCreatedAt();
        final var actualUpdatedAt = actualMember.getCreatedAt();

        actualMember.update(expedtedName, expectedType);

        Assertions.assertNotNull(actualMember);
        Assertions.assertEquals(actualID, actualMember.getId());
        Assertions.assertEquals(expedtedName, actualMember.getName());
        Assertions.assertEquals(actualCreatedAt, actualMember.getCreatedAt());
        Assertions.assertTrue(actualUpdatedAt.isBefore(actualMember.getUpdatedAt()));

    }

    @Test
    public void givenAValidCastMember_whenCallUpdateWithInvalidNullName_shouldReceiveNotification() {

        final String expedtedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";


        final var actualMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);

        Assertions.assertNotNull(actualMember);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualMember.update(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAValidCastMember_whenCallUpdateWithInvalidEmptyName_shouldReceiveNotification() {

        final String expedtedName = "    ";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";


        final var actualMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);

        Assertions.assertNotNull(actualMember);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualMember.update(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAValidCastMember_whenCallUpdateWithInvalidNullType_shouldReceiveNotification() {

        final String expedtedName = "Van Diesel";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'type' should not be null";


        final var actualMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);

        Assertions.assertNotNull(actualMember);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualMember.update(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAValidCastMember_whenCallUpdateWithNameLengthMoreThan255_shouldReceiveNotification() {

        final String expedtedName = """
                fsadfasdfasdasd fas dfas df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                df asdf asd fasdf asd fasdf asd ffsadfasdfasdasd fas dfas df asdf a
                sd fasdf asd fasdf asd f""";
        final CastMemberType expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";


        final var actualMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);

        Assertions.assertNotNull(actualMember);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> actualMember.update(expedtedName, expectedType));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }


}
