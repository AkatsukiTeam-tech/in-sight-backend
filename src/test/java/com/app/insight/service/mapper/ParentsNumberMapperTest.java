package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParentsNumberMapperTest {

    private ParentsNumberMapper parentsNumberMapper;

    @BeforeEach
    public void setUp() {
        parentsNumberMapper = new ParentsNumberMapperImpl();
    }
}
