package com.fullcle.admin.catalogo.infrastructure.configuration.usecases;

import com.fullcle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcle.admin.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.fullcle.admin.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.fullcle.admin.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.fullcle.admin.catalogo.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.fullcle.admin.catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fullcle.admin.catalogo.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.fullcle.admin.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import com.fullcle.admin.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.fullcle.admin.catalogo.application.category.update.UpdateCategoryUseCase;
import com.fullcle.admin.catalogo.domain.category.CategoryGeteway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGeteway categoryGeteway;

    public CategoryUseCaseConfig(final CategoryGeteway categoryGeteway) {
        this.categoryGeteway = categoryGeteway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGeteway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGeteway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGeteway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGeteway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGeteway);
    }

}
