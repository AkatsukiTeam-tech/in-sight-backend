package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubgroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubgroupDTO.class);
        SubgroupDTO subgroupDTO1 = new SubgroupDTO();
        subgroupDTO1.setId(1L);
        SubgroupDTO subgroupDTO2 = new SubgroupDTO();
        assertThat(subgroupDTO1).isNotEqualTo(subgroupDTO2);
        subgroupDTO2.setId(subgroupDTO1.getId());
        assertThat(subgroupDTO1).isEqualTo(subgroupDTO2);
        subgroupDTO2.setId(2L);
        assertThat(subgroupDTO1).isNotEqualTo(subgroupDTO2);
        subgroupDTO1.setId(null);
        assertThat(subgroupDTO1).isNotEqualTo(subgroupDTO2);
    }
}
