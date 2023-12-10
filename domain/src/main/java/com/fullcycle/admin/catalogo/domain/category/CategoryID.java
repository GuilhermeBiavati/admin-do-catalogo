package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {
    private final String value;

    public CategoryID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique() {
        return CategoryID.from(IdUtils.uuid());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
