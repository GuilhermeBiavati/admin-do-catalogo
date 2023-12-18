package com.fullcycle.admin.catalogo.infrastructure.category.models;

import com.fullcycle.admin.catalogo.JacksonTest;
import com.fullcycle.admin.catalogo.domain.utils.InstantUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
public class CategoryListResponseTest {

    @Autowired
    private JacksonTester<CategoryListResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectId = "123";
        final var expectName = "Filmes";
        final var expectDescription = "Filmes";
        final var expectedIsActive = true;
        final var expectedCreatedAt = InstantUtils.now();
        final var expectedUpdatedAt = InstantUtils.now();
        final var expectedDeletedAt = InstantUtils.now();

        final var response = new CategoryListResponse(
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
}
