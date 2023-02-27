package com.app.insight.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TokenBlackListTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenBlackList.class);
        TokenBlackList tokenBlackList1 = new TokenBlackList();
        tokenBlackList1.setId(1L);
        TokenBlackList tokenBlackList2 = new TokenBlackList();
        tokenBlackList2.setId(tokenBlackList1.getId());
        assertThat(tokenBlackList1).isEqualTo(tokenBlackList2);
        tokenBlackList2.setId(2L);
        assertThat(tokenBlackList1).isNotEqualTo(tokenBlackList2);
        tokenBlackList1.setId(null);
        assertThat(tokenBlackList1).isNotEqualTo(tokenBlackList2);
    }
}
