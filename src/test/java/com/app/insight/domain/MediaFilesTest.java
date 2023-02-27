package com.app.insight.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.insight.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaFilesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaFiles.class);
        MediaFiles mediaFiles1 = new MediaFiles();
        mediaFiles1.setId(1L);
        MediaFiles mediaFiles2 = new MediaFiles();
        mediaFiles2.setId(mediaFiles1.getId());
        assertThat(mediaFiles1).isEqualTo(mediaFiles2);
        mediaFiles2.setId(2L);
        assertThat(mediaFiles1).isNotEqualTo(mediaFiles2);
        mediaFiles1.setId(null);
        assertThat(mediaFiles1).isNotEqualTo(mediaFiles2);
    }
}
