package com.festival.vozesdonorteapi.dto;

import java.util.UUID;

public class CityDTO {
    private UUID id;
    private String name;
    private Long participantCount;

    // Constructors
    public CityDTO() {}

    public CityDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityDTO(UUID id, String name, Long participantCount) {
        this.id = id;
        this.name = name;
        this.participantCount = participantCount;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getParticipantCount() { return participantCount; }
    public void setParticipantCount(Long participantCount) { this.participantCount = participantCount; }
}