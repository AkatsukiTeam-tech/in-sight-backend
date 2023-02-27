package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionUserDTO.class);
        OptionUserDTO optionUserDTO1 = new OptionUserDTO();
        optionUserDTO1.setId(1L);
        OptionUserDTO optionUserDTO2 = new OptionUserDTO();
        assertThat(optionUserDTO1).isNotEqualTo(optionUserDTO2);
        optionUserDTO2.setId(optionUserDTO1.getId());
        assertThat(optionUserDTO1).isEqualTo(optionUserDTO2);
        optionUserDTO2.setId(2L);
        assertThat(optionUserDTO1).isNotEqualTo(optionUserDTO2);
        optionUserDTO1.setId(null);
        assertThat(optionUserDTO1).isNotEqualTo(optionUserDTO2);
    }
}
