package com.mmyrosh.springfilestoreaws.service;

import com.mmyrosh.springfilestoreaws.model.Event;
import com.mmyrosh.springfilestoreaws.model.File;
import com.mmyrosh.springfilestoreaws.model.User;

import java.util.List;

public interface EventService {
    Event save(Event event);

    List<Event> getAll();

    Event findById(Long id);

    Event addNew(File file, User user);

    Event delete(File file, User user);
}
