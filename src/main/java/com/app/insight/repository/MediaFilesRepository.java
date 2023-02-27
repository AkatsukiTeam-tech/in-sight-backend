package com.app.insight.repository;

import com.app.insight.domain.MediaFiles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the MediaFiles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaFilesRepository extends JpaRepository<MediaFiles, UUID> {}
