package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TokenBlackListDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenBlackListDTO.class);
        TokenBlackListDTO tokenBlackListDTO1 = new TokenBlackListDTO();
        tokenBlackListDTO1.setId(1L);
        TokenBlackListDTO tokenBlackListDTO2 = new TokenBlackListDTO();
        assertThat(tokenBlackListDTO1).isNotEqualTo(tokenBlackListDTO2);
        tokenBlackListDTO2.setId(tokenBlackListDTO1.getId());
        assertThat(tokenBlackListDTO1).isEqualTo(tokenBlackListDTO2);
        tokenBlackListDTO2.setId(2L);
        assertThat(tokenBlackListDTO1).isNotEqualTo(tokenBlackListDTO2);
        tokenBlackListDTO1.setId(null);
        assertThat(tokenBlackListDTO1).isNotEqualTo(tokenBlackListDTO2);
    }
}
