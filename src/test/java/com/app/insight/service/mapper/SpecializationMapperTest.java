package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpecializationMapperTest {

    private SpecializationMapper specializationMapper;

    @BeforeEach
    public void setUp() {
        specializationMapper = new SpecializationMapperImpl();
    }
}
