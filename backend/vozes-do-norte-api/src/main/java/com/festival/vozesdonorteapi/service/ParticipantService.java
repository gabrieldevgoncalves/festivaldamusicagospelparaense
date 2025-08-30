package com.festival.vozesdonorteapi.service;

import com.festival.vozesdonorteapi.dto.ParticipantDTO;
import com.festival.vozesdonorteapi.dto.ParticipantRequestDTO;
import com.festival.vozesdonorteapi.entity.City;
import com.festival.vozesdonorteapi.entity.Participant;
import com.festival.vozesdonorteapi.repository.CityRepository;
import com.festival.vozesdonorteapi.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private CityRepository cityRepository;

    public ParticipantDTO createParticipant(ParticipantRequestDTO dto) {
        if (participantRepository.existsByCpf(dto.getCpf())) {
            throw new RuntimeException("CPF já cadastrado");
        }

        if (participantRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        LocalDate today = LocalDate.now();
        int age = Period.between(dto.getBirthDate(), today).getYears();
        if (age < 12) {
            throw new RuntimeException("Idade mínima para participação é de 12 anos completos");
        }
        if (age > 100) {
            throw new RuntimeException("Idade máxima para participação é de 100 anos. Verifique a data informada");
        }

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));

        Participant participant = new Participant();
        participant.setName(dto.getName());
        participant.setEmail(dto.getEmail());
        participant.setPhone(dto.getPhone());
        participant.setCpf(dto.getCpf());
        participant.setBirthDate(dto.getBirthDate());
        participant.setCity(city);
        participant.setReason(dto.getReason());

        Participant savedParticipant = participantRepository.save(participant);
        return convertToDTO(savedParticipant);
    }

    public List<ParticipantDTO> getAllParticipants() {
        return participantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ParticipantDTO> getParticipantsByCity(UUID cityId) {
        return participantRepository.findAll().stream()
                .filter(p -> p.getCity().getId().equals(cityId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ParticipantDTO getParticipantById(UUID id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado"));
        return convertToDTO(participant);
    }

    public void deleteParticipant(UUID id) {
        if (!participantRepository.existsById(id)) {
            throw new RuntimeException("Participante não encontrado");
        }
        participantRepository.deleteById(id);
    }

    private ParticipantDTO convertToDTO(Participant participant) {
        ParticipantDTO dto = new ParticipantDTO();
        dto.setId(participant.getId());
        dto.setName(participant.getName());
        dto.setEmail(participant.getEmail());
        dto.setPhone(participant.getPhone());
        dto.setCpf(participant.getCpf());
        dto.setBirthDate(participant.getBirthDate());
        dto.setCityId(participant.getCity().getId());
        dto.setCityName(participant.getCity().getName());
        dto.setReason(participant.getReason());
        dto.setCreatedAt(participant.getCreatedAt());
        return dto;
    }
}
