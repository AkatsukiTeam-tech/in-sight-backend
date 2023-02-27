package com.app.insight.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParentsNumberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParentsNumber.class);
        ParentsNumber parentsNumber1 = new ParentsNumber();
        parentsNumber1.setId(1L);
        ParentsNumber parentsNumber2 = new ParentsNumber();
        parentsNumber2.setId(parentsNumber1.getId());
        assertThat(parentsNumber1).isEqualTo(parentsNumber2);
        parentsNumber2.setId(2L);
        assertThat(parentsNumber1).isNotEqualTo(parentsNumber2);
        parentsNumber1.setId(null);
        assertThat(parentsNumber1).isNotEqualTo(parentsNumber2);
    }
}
