package com.app.insight.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppTestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppTest.class);
        AppTest appTest1 = new AppTest();
        appTest1.setId(1L);
        AppTest appTest2 = new AppTest();
        appTest2.setId(appTest1.getId());
        assertThat(appTest1).isEqualTo(appTest2);
        appTest2.setId(2L);
        assertThat(appTest1).isNotEqualTo(appTest2);
        appTest1.setId(null);
        assertThat(appTest1).isNotEqualTo(appTest2);
    }
}
