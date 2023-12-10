package com.fullcycle.admin.catalogo.domain.video;

import com.fullcycle.admin.catalogo.domain.Identifier;
import com.fullcycle.admin.catalogo.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class VideoID extends Identifier {
    private final String value;

    public VideoID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static VideoID from(final String anId) {
        return new VideoID(anId.toLowerCase());
    }

    public static VideoID unique() {
        return VideoID.from(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VideoID that = (VideoID) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
