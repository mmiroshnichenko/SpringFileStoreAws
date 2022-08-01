package com.mmyrosh.springfilestoreaws.service.impl;

import com.mmyrosh.springfilestoreaws.model.Event;
import com.mmyrosh.springfilestoreaws.model.File;
import com.mmyrosh.springfilestoreaws.model.Status;
import com.mmyrosh.springfilestoreaws.model.User;
import com.mmyrosh.springfilestoreaws.repository.EventRepository;
import com.mmyrosh.springfilestoreaws.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event save(Event event) {
        Event savedEvent = eventRepository.save(event);
        log.info("IN save - event: {} successfully saved", savedEvent);
        return savedEvent;
    }

    @Override
    public List<Event> getAll() {
        List<Event> result = eventRepository.findAll();
        log.info("IN getAll - {} events found", result.size());
        return result;
    }

    @Override
    public Event findById(Long id) {
        Event result = eventRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("IN findById - no event found by id: {}", id);
            return null;
        }
        log.info("IN findById - event: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public Event addNew(File file, User user) {
        Event event = createEvent(file, user);
        event.setStatus(Status.ACTIVE);

        return save(event);
    }

    @Override
    public Event delete(File file, User user) {
        Event event = createEvent(file, user);
        event.setStatus(Status.DELETED);

        return save(event);
    }

    private Event createEvent(File file, User user) {
        Event event = new Event();
        event.setFile(file);
        event.setUser(user);
        event.setCreated(new Date());

        return event;
    }
}
