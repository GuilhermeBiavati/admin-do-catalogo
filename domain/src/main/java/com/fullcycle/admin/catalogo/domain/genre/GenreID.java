package com.fullcycle.admin.catalogo.domain.genre;

import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class GenreID extends Identifier {
    private final String value;

    public GenreID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static GenreID unique() {
        return GenreID.from(IdUtils.uuid());
    }

    public static GenreID from(final String anId) {
        return new GenreID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenreID that = (GenreID) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
