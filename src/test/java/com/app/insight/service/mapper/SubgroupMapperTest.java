package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubgroupMapperTest {

    private SubgroupMapper subgroupMapper;

    @BeforeEach
    public void setUp() {
        subgroupMapper = new SubgroupMapperImpl();
    }
}
