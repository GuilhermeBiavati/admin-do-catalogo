package com.fullcycle.admin.catalogo.infrastructure.category.models;

import com.fullcycle.admin.catalogo.JacksonTest;
import com.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
public class CategoryResponseTest {

    @Autowired
    private JacksonTester<CategoryResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectId = "123";
        final var expectName = "Filmes";
        final var expectDescription = "Filmes";
        final var expectedIsActive = true;
        final var expectedCreatedAt = InstantUtils.now();
        final var expectedUpdatedAt = InstantUtils.now();
        final var expectedDeletedAt = InstantUtils.now();

        final var response = new CategoryResponse(
                expectId,
                expectName,
                expectDescription,
                expectedIsActive,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt
        );

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectId)
                .hasJsonPathValue("$.name", expectName)
                .hasJsonPathValue("$.description", expectDescription)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.updated_at", expectedUpdatedAt.toString())
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());

    }

    @Test
    public void testUnmarshall() throws Exception {
        final var expectId = "123";
        final var expectName = "Filmes";
        final var expectDescription = "Filmes";
        final var expectedIsActive = true;
        final var expectedCreatedAt = InstantUtils.now();
        final var expectedUpdatedAt = InstantUtils.now();
        final var expectedDeletedAt = InstantUtils.now();

        final var json = """
                    {
                        "id": "%s",
                        "name": "%s",
                        "description": "%s",
                        "is_active": "%s",
                        "created_at": "%s",
                        "updated_at": "%s",
                        "deleted_at": "%s"
                    }
                """.formatted(
                expectId,
                expectName,
                expectDescription,
                expectedIsActive,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt
        );

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("id", expectId)
                .hasFieldOrPropertyWithValue("name", expectName)
                .hasFieldOrPropertyWithValue("description", expectDescription)
                .hasFieldOrPropertyWithValue("active", expectedIsActive)
                .hasFieldOrPropertyWithValue("createdAt", expectedCreatedAt)
                .hasFieldOrPropertyWithValue("updatedAt", expectedUpdatedAt)
                .hasFieldOrPropertyWithValue("deletedAt", expectedDeletedAt);

    }
}
