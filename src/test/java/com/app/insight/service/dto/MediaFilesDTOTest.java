package com.app.insight.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaFilesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaFilesDTO.class);
        MediaFilesDTO mediaFilesDTO1 = new MediaFilesDTO();
        mediaFilesDTO1.setId(1L);
        MediaFilesDTO mediaFilesDTO2 = new MediaFilesDTO();
        assertThat(mediaFilesDTO1).isNotEqualTo(mediaFilesDTO2);
        mediaFilesDTO2.setId(mediaFilesDTO1.getId());
        assertThat(mediaFilesDTO1).isEqualTo(mediaFilesDTO2);
        mediaFilesDTO2.setId(2L);
        assertThat(mediaFilesDTO1).isNotEqualTo(mediaFilesDTO2);
        mediaFilesDTO1.setId(null);
        assertThat(mediaFilesDTO1).isNotEqualTo(mediaFilesDTO2);
    }
}
