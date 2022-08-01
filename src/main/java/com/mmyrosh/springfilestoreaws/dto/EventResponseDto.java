package com.mmyrosh.springfilestoreaws.dto;

import com.mmyrosh.springfilestoreaws.model.Event;
import lombok.Data;

import java.util.Date;

@Data
public class EventResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private Date created;
    private String status;

    public static EventResponseDto fromEvent(Event event) {
        EventResponseDto eventResponseDto = new EventResponseDto();
        eventResponseDto.setId(event.getId());
        eventResponseDto.setUserId(event.getUser().getId());
        eventResponseDto.setUsername(event.getUser().getUsername());
        eventResponseDto.setCreated(event.getCreated());
        eventResponseDto.setStatus(event.getStatus().name());

        return eventResponseDto;
    }
}
