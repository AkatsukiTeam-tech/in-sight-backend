package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoinsUserHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinsUserHistoryDTO.class);
        CoinsUserHistoryDTO coinsUserHistoryDTO1 = new CoinsUserHistoryDTO();
        coinsUserHistoryDTO1.setId(1L);
        CoinsUserHistoryDTO coinsUserHistoryDTO2 = new CoinsUserHistoryDTO();
        assertThat(coinsUserHistoryDTO1).isNotEqualTo(coinsUserHistoryDTO2);
        coinsUserHistoryDTO2.setId(coinsUserHistoryDTO1.getId());
        assertThat(coinsUserHistoryDTO1).isEqualTo(coinsUserHistoryDTO2);
        coinsUserHistoryDTO2.setId(2L);
        assertThat(coinsUserHistoryDTO1).isNotEqualTo(coinsUserHistoryDTO2);
        coinsUserHistoryDTO1.setId(null);
        assertThat(coinsUserHistoryDTO1).isNotEqualTo(coinsUserHistoryDTO2);
    }
}
