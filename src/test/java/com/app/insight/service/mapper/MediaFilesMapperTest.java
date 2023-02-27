package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MediaFilesMapperTest {

    private MediaFilesMapper mediaFilesMapper;

    @BeforeEach
    public void setUp() {
        mediaFilesMapper = new MediaFilesMapperImpl();
    }
}
