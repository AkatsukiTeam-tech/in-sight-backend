package com.app.insight.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionUser.class);
        OptionUser optionUser1 = new OptionUser();
        optionUser1.setId(1L);
        OptionUser optionUser2 = new OptionUser();
        optionUser2.setId(optionUser1.getId());
        assertThat(optionUser1).isEqualTo(optionUser2);
        optionUser2.setId(2L);
        assertThat(optionUser1).isNotEqualTo(optionUser2);
        optionUser1.setId(null);
        assertThat(optionUser1).isNotEqualTo(optionUser2);
    }
}
