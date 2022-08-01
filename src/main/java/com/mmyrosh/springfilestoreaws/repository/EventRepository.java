package com.mmyrosh.springfilestoreaws.repository;

import com.mmyrosh.springfilestoreaws.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
