package com.fullcle.admin.catalogo.domain.genre;

import com.fullcle.admin.catalogo.domain.category.CategoryID;
import com.fullcle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcle.admin.catalogo.domain.exceptions.NotificationException;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;
import com.fullcle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenreTest {
    @Test
    public void givenAValidParams_whenCallNewGenre_thenInstantiateAGenre() {

        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var expectedCategories = 0;
        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        Assertions.assertNotNull(actualGenre);
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories().size());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewGenreAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedIsActive = true;


        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,
                actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewGenreAndValidate_thenShouldReceiveError() {
        final String expectedName = "    ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedIsActive = true;

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,
                actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewGenreAndValidate_thenShouldReceiveError() {
        final String expectedName = "FI ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedIsActive = true;

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,
                actualException.getErrors().size());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewGenreAndValidate_thenShouldReceiveError() {
        final String expectedName = """
                abcdefghijklmnopqrstuabcdefghijklmnopqrstuabcdefghijklmnopqrstuabcdefghijklmnop
                qrstuabcdefghijklmnopqrstuabcdefghijklmnopqrstuabcdefghijklmnopqrstuabcdefghijk
                lmnopqrstuabcdefghijklmnopqrstuabcdefghijklmnopqrstuabcdefghijklmnopqrstuabcdef
                ghijklmnopqrstuabcdefghijklmnopqrstu""";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedIsActive = true;

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,
                actualException.getErrors().size());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewGenreAndValidate_thenShouldNotReceiveError() {
        final var expectedName = "Filmes";
        final var expectedIsActive = false;
        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualGenre.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualGenre);
        Assertions.assertNotNull(actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNotNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAValidActiveGenre_whenCallDeactivate_thenReturnGenreInactivated() {
        final var expectedName = "Filmes";
        final var expectedIsActive = false;
        final var aGenre = Genre.newGenre(expectedName, true);

        Assertions.assertDoesNotThrow(() -> aGenre.validate(new ThrowsValidationHandler()));

        final var createdAt = aGenre.getCreatedAt();
        final var updatedAt = aGenre.getUpdatedAt();

        Assertions.assertTrue(aGenre.isActive());
        Assertions.assertNull(aGenre.getDeletedAt());

        final var actualGenre = aGenre.deactivate();

        Assertions.assertDoesNotThrow(() -> actualGenre.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualGenre.getDeletedAt());

    }

    @Test
    public void givenAValidInactiveGenre_whenCallActivate_thenReturnGenreActivated() {
        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var aGenre = Genre.newGenre(expectedName, false);

        Assertions.assertDoesNotThrow(() -> aGenre.validate(new ThrowsValidationHandler()));

        final var createdAt = aGenre.getCreatedAt();
        final var updatedAt = aGenre.getUpdatedAt();

        Assertions.assertFalse(aGenre.isActive());
        Assertions.assertNotNull(aGenre.getDeletedAt());

        final var actualGenre = aGenre.activate();

        Assertions.assertDoesNotThrow(() -> actualGenre.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualGenre.getDeletedAt());
    }

    @Test
    public void givenAValidGenre_whenCallUpdate_thenReturnGenreUpdated() {
        final var expectedName = "Filmes";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));

        final var aGenre = Genre.newGenre("Film", expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aGenre.validate(new ThrowsValidationHandler()));

        final var createdAt = aGenre.getCreatedAt();
        final var updatedAt = aGenre.getUpdatedAt();

        final var actualGenre = aGenre.update(expectedName, expectedIsActive, expectedCategories);

        Assertions.assertDoesNotThrow(() -> actualGenre.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());

    }

    @Test
    public void givenAValidGenre_whenCallUpdateToInactive_thenReturnGenreUpdated() {
        final var expectedName = "Filmes";
        final var expectedIsActive = false;
        final var expectedCategories = List.of(CategoryID.from("123"));

        final var aGenre = Genre.newGenre("Film", true);

        Assertions.assertDoesNotThrow(() -> aGenre.validate(new ThrowsValidationHandler()));

        Assertions.assertTrue(aGenre.isActive());
        Assertions.assertNull(aGenre.getDeletedAt());

        final var createdAt = aGenre.getCreatedAt();
        final var updatedAt = aGenre.getUpdatedAt();

        final var actualGenre = aGenre.update(expectedName, expectedIsActive, expectedCategories);

        Assertions.assertDoesNotThrow(() -> actualGenre.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertFalse(aGenre.isActive());
        Assertions.assertNotNull(aGenre.getDeletedAt());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());

    }

//    @Test
//    public void givenAValidGenre_whenCallUpdateWithInvalidParams_thenReturnGenreUpdated() {
//        final String expectedName = null;
//        final var expectedIsActive = true;
//        final var expectedCategories = List.of(CategoryID.from("123"));
//
//        final var aGenre = Genre.newGenre("Filmes", expectedIsActive);
//
//        Assertions.assertDoesNotThrow(() -> aGenre.validate(new ThrowsValidationHandler()));
//
//        final var createdAt = aGenre.getCreatedAt();
//        final var updatedAt = aGenre.getUpdatedAt();
//
//        final var actualGenre = aGenre.update(expectedName, expectedIsActive, expectedCategories);
//
//        Assertions.assertEquals(aGenre.getId(), actualGenre.getId());
//        Assertions.assertEquals(expectedName, actualGenre.getName());
//        Assertions.assertTrue(aGenre.isActive());
//        Assertions.assertNull(aGenre.getDeletedAt());
//        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
//        Assertions.assertNotNull(actualGenre.getCreatedAt());
//        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
//    }

    @Test
    public void givenAValidGenre_whenCallUpdateWithEmptyName_thenShouldReceiveNotificationException() {
        final String expectedName = " ";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualGenre = Genre.newGenre("acao", false);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            actualGenre.update(expectedName, expectedIsActive, expectedCategories);
        });

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,
                actualException.getErrors().size());
    }

    @Test
    public void givenAValidGenre_whenCallUpdateWithNullName_thenShouldReceiveNotificationException() {
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(CategoryID.from("123"));
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualGenre = Genre.newGenre("acao", false);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            actualGenre.update(expectedName, expectedIsActive, expectedCategories);
        });

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount,
                actualException.getErrors().size());
    }

    @Test
    public void givenAValidGenre_whenCallUpdateWithNullCategories_thenShouldReceiveOK() {
        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = new ArrayList<CategoryID>();

        final var actualGenre = Genre.newGenre("acao", expectedIsActive);

        final var createdAt = actualGenre.getCreatedAt();
        final var updatedAt = actualGenre.getUpdatedAt();

        Assertions.assertDoesNotThrow(() -> {
            actualGenre.update(expectedName, expectedIsActive, null);
        });

        Assertions.assertEquals(actualGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAValidEmptyCategoriesGenre_whenCallAddCatetory_thenShouldReceiveOK() {
        final var seriesId = CategoryID.from("123");
        final var moviesId = CategoryID.from("321");

        final var expectedName = "Ação";
        final var expectedIsActive = true;

        final var expectedCategories = List.of(seriesId, moviesId);

        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        Assertions.assertEquals(0, actualGenre.getCategories().size());

        final var createdAt = actualGenre.getCreatedAt();
        final var updatedAt = actualGenre.getUpdatedAt();

        actualGenre.addCategory(seriesId);
        actualGenre.addCategory(moviesId);

        Assertions.assertEquals(actualGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAInvalidNullAsCategoryID_whenCallAddCatetory_thenShouldReceiveOK() {
        final var seriesId = CategoryID.from("123");
        final var moviesId = CategoryID.from("321");

        final var expectedName = "Ação";
        final var expectedIsActive = true;

        final var expectedCategories = new ArrayList<CategoryID>();

        final var actualGenre = Genre.newGenre(expectedName, expectedIsActive);

        Assertions.assertEquals(0, actualGenre.getCategories().size());

        final var createdAt = actualGenre.getCreatedAt();
        final var updatedAt = actualGenre.getUpdatedAt();

        actualGenre.addCategory(null);

        Assertions.assertEquals(actualGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertEquals(updatedAt, actualGenre.getUpdatedAt());
    }

    @Test
    public void givenAValidGenreWithTwoCategories_whenCallRemoveCatetory_thenShouldReceiveOK() {
        final var seriesId = CategoryID.from("123");
        final var moviesId = CategoryID.from("321");

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(moviesId);

        final var actualGenre = Genre.newGenre("acao", expectedIsActive);

        actualGenre.update(expectedName, expectedIsActive, List.of(seriesId, moviesId));

        Assertions.assertEquals(2, actualGenre.getCategories().size());

        final var createdAt = actualGenre.getCreatedAt();
        final var updatedAt = actualGenre.getUpdatedAt();

        actualGenre.removeCategory(seriesId);

        Assertions.assertEquals(actualGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertTrue(actualGenre.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    public void givenAnInvalidNullAsCategoryID_whenCallRemoveCatetory_thenShouldReceiveOK() {
        final var seriesId = CategoryID.from("123");
        final var moviesId = CategoryID.from("321");

        final var expectedName = "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(seriesId, moviesId);

        final var actualGenre = Genre.newGenre("acao", expectedIsActive);

        actualGenre.update(expectedName, expectedIsActive, expectedCategories);

        Assertions.assertEquals(2, actualGenre.getCategories().size());

        final var createdAt = actualGenre.getCreatedAt();
        final var updatedAt = actualGenre.getUpdatedAt();

        actualGenre.removeCategory(null);

        Assertions.assertEquals(actualGenre.getId(), actualGenre.getId());
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertTrue(actualGenre.isActive());
        Assertions.assertNull(actualGenre.getDeletedAt());
        Assertions.assertEquals(createdAt, actualGenre.getCreatedAt());
        Assertions.assertEquals(expectedCategories, actualGenre.getCategories());
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertEquals(updatedAt, actualGenre.getUpdatedAt());
    }


}
