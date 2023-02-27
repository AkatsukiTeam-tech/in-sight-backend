package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionUserMapperTest {

    private OptionUserMapper optionUserMapper;

    @BeforeEach
    public void setUp() {
        optionUserMapper = new OptionUserMapperImpl();
    }
}
