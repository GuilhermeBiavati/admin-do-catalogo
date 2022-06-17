package com.fullcle.admin.catalogo.infrastructure.category.models;

import com.fullcle.admin.catalogo.JacksonTets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTets
public class CategoryResponseTest {

    @Autowired
    private JacksonTester<CategoryResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectId = "123";
        final var expectName = "Filmes";
        final var expectDescription = "Filmes";
        final var expectedIsActive = true;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

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
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

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
