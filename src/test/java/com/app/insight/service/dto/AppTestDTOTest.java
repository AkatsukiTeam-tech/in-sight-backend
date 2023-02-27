package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppTestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppTestDTO.class);
        AppTestDTO appTestDTO1 = new AppTestDTO();
        appTestDTO1.setId(1L);
        AppTestDTO appTestDTO2 = new AppTestDTO();
        assertThat(appTestDTO1).isNotEqualTo(appTestDTO2);
        appTestDTO2.setId(appTestDTO1.getId());
        assertThat(appTestDTO1).isEqualTo(appTestDTO2);
        appTestDTO2.setId(2L);
        assertThat(appTestDTO1).isNotEqualTo(appTestDTO2);
        appTestDTO1.setId(null);
        assertThat(appTestDTO1).isNotEqualTo(appTestDTO2);
    }
}
