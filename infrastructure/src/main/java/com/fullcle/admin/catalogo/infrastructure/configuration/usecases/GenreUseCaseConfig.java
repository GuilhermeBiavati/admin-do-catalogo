package com.fullcle.admin.catalogo.infrastructure.configuration.usecases;

import com.fullcle.admin.catalogo.application.genre.create.CreateGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.create.DefaultCreateGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.delete.DefaultDeleteGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import com.fullcle.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.fullcle.admin.catalogo.application.genre.retrieve.list.DefaultListGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.retrieve.list.ListGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import com.fullcle.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import com.fullcle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcle.admin.catalogo.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {

    private final GenreGateway genreGateway;
    private final CategoryGateway categoryGateway;

    public GenreUseCaseConfig(final GenreGateway genreGateway, final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenreUseCase listGenreUseCase() {
        return new DefaultListGenreUseCase(genreGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

}
