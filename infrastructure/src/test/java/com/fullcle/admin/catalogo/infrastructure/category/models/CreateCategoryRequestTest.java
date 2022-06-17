package com.fullcle.admin.catalogo.infrastructure.category.models;

import com.fullcle.admin.catalogo.JacksonTets;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTets
public class CreateCategoryRequestTest {

    @Autowired
    private JacksonTester<CreateCategoryRequest> json;

    @Test
    public void testMarshall() throws Exception {

        final var expectName = "Filmes";
        final var expectDescription = "Filmes";
        final var expectedIsActive = true;

        final var response = new CreateCategoryRequest(
                expectName,
                expectDescription,
                expectedIsActive
        );

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.name", expectName)
                .hasJsonPathValue("$.description", expectDescription)
                .hasJsonPathValue("$.is_active", expectedIsActive);

    }

    @Test
    public void testUnmarshall() throws Exception {
        final var expectName = "Filmes";
        final var expectDescription = "Filmes";
        final var expectedIsActive = true;

        final var json = """
                    {
                        "name": "%s",
                        "description": "%s",
                        "is_active": "%s"
                    }
                """.formatted(
                expectName,
                expectDescription,
                expectedIsActive
        );

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectName)
                .hasFieldOrPropertyWithValue("description", expectDescription)
                .hasFieldOrPropertyWithValue("active", expectedIsActive);

    }
}
