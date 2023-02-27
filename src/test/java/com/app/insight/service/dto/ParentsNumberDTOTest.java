package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParentsNumberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParentsNumberDTO.class);
        ParentsNumberDTO parentsNumberDTO1 = new ParentsNumberDTO();
        parentsNumberDTO1.setId(1L);
        ParentsNumberDTO parentsNumberDTO2 = new ParentsNumberDTO();
        assertThat(parentsNumberDTO1).isNotEqualTo(parentsNumberDTO2);
        parentsNumberDTO2.setId(parentsNumberDTO1.getId());
        assertThat(parentsNumberDTO1).isEqualTo(parentsNumberDTO2);
        parentsNumberDTO2.setId(2L);
        assertThat(parentsNumberDTO1).isNotEqualTo(parentsNumberDTO2);
        parentsNumberDTO1.setId(null);
        assertThat(parentsNumberDTO1).isNotEqualTo(parentsNumberDTO2);
    }
}
