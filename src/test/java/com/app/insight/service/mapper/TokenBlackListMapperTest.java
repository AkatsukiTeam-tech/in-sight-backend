package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenBlackListMapperTest {

    private TokenBlackListMapper tokenBlackListMapper;

    @BeforeEach
    public void setUp() {
        tokenBlackListMapper = new TokenBlackListMapperImpl();
    }
}
