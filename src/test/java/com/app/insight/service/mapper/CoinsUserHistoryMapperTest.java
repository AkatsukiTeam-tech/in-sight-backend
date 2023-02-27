package com.app.insight.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoinsUserHistoryMapperTest {

    private CoinsUserHistoryMapper coinsUserHistoryMapper;

    @BeforeEach
    public void setUp() {
        coinsUserHistoryMapper = new CoinsUserHistoryMapperImpl();
    }
}
