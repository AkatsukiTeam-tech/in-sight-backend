package com.app.insight.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoinsUserHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoinsUserHistory.class);
        CoinsUserHistory coinsUserHistory1 = new CoinsUserHistory();
        coinsUserHistory1.setId(1L);
        CoinsUserHistory coinsUserHistory2 = new CoinsUserHistory();
        coinsUserHistory2.setId(coinsUserHistory1.getId());
        assertThat(coinsUserHistory1).isEqualTo(coinsUserHistory2);
        coinsUserHistory2.setId(2L);
        assertThat(coinsUserHistory1).isNotEqualTo(coinsUserHistory2);
        coinsUserHistory1.setId(null);
        assertThat(coinsUserHistory1).isNotEqualTo(coinsUserHistory2);
    }
}
