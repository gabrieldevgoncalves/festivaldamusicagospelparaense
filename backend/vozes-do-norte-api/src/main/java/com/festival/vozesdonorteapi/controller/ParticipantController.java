package com.festival.vozesdonorteapi.controller;

import com.festival.vozesdonorteapi.dto.ParticipantDTO;
import com.festival.vozesdonorteapi.dto.ParticipantRequestDTO;
import com.festival.vozesdonorteapi.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
@CrossOrigin(origins = "http://localhost:3000")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        List<ParticipantDTO> participants = participantService.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<ParticipantDTO>> getParticipantsByCity(@PathVariable UUID cityId) {
        List<ParticipantDTO> participants = participantService.getParticipantsByCity(cityId);
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable UUID id) {
        ParticipantDTO participant = participantService.getParticipantById(id);
        return ResponseEntity.ok(participant);
    }

    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@Valid @RequestBody ParticipantRequestDTO participantRequestDTO) {
        try {
            ParticipantDTO createdParticipant = participantService.createParticipant(participantRequestDTO);
            return ResponseEntity.ok(createdParticipant);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable UUID id) {
        try {
            participantService.deleteParticipant(id);
            return ResponseEntity.ok("Participante deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
