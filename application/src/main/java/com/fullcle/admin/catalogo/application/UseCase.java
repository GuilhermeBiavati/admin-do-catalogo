package com.fullcle.admin.catalogo.application;

import com.fullcle.admin.catalogo.domain.category.Category;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN anIn);
}