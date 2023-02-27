package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskAnswerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAnswerDTO.class);
        TaskAnswerDTO taskAnswerDTO1 = new TaskAnswerDTO();
        taskAnswerDTO1.setId(1L);
        TaskAnswerDTO taskAnswerDTO2 = new TaskAnswerDTO();
        assertThat(taskAnswerDTO1).isNotEqualTo(taskAnswerDTO2);
        taskAnswerDTO2.setId(taskAnswerDTO1.getId());
        assertThat(taskAnswerDTO1).isEqualTo(taskAnswerDTO2);
        taskAnswerDTO2.setId(2L);
        assertThat(taskAnswerDTO1).isNotEqualTo(taskAnswerDTO2);
        taskAnswerDTO1.setId(null);
        assertThat(taskAnswerDTO1).isNotEqualTo(taskAnswerDTO2);
    }
}
