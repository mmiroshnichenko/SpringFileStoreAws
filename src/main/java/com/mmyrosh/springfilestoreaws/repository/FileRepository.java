package com.mmyrosh.springfilestoreaws.repository;

import com.mmyrosh.springfilestoreaws.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
