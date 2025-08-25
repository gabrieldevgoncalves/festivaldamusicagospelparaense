package com.festival.vozesdonorteapi.service;

import com.festival.vozesdonorteapi.dto.CityDTO;
import com.festival.vozesdonorteapi.entity.City;
import com.festival.vozesdonorteapi.repository.CityRepository;
import com.festival.vozesdonorteapi.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public void run(String... args) throws Exception {
        if (cityRepository.count() == 0) {
            cityRepository.save(new City("Marabá"));
            cityRepository.save(new City("Santarém"));
            cityRepository.save(new City("Portel"));
            cityRepository.save(new City("Benevides"));
            cityRepository.save(new City("Belém"));
            System.out.println("Cidades inicializadas com sucesso!");
        }
    }

    public List<CityDTO> getAllCitiesWithParticipantCount() {
        return cityRepository.findAllOrderByName().stream()
                .map(city -> {
                    Long count = participantRepository.countByCityId(city.getId());
                    return new CityDTO(city.getId(), city.getName(), count);
                })
                .collect(Collectors.toList());
    }

    public CityDTO getCityById(UUID id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
        return convertToDTO(city);
    }

    public CityDTO createCity(CityDTO cityDTO) {
        City city = new City(cityDTO.getName());
        City savedCity = cityRepository.save(city);
        return convertToDTO(savedCity);
    }

    private CityDTO convertToDTO(City city) {
        return new CityDTO(city.getId(), city.getName());
    }
}