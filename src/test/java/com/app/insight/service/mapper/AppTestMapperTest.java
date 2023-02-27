package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTestMapperTest {

    private AppTestMapper appTestMapper;

    @BeforeEach
    public void setUp() {
        appTestMapper = new AppTestMapperImpl();
    }
}
