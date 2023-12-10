package com.fullcycle.admin.catalogo.domain.video;

import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {

    Video create(Video aVideo);

    void deleteById(VideoID anID);

    Optional<Video> findById(VideoID anID);

    Video update(Video aVideo);

    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);
}
