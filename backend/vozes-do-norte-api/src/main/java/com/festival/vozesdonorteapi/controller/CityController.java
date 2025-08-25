package com.festival.vozesdonorteapi.controller;

import com.festival.vozesdonorteapi.dto.CityDTO;
import com.festival.vozesdonorteapi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cities")
@CrossOrigin(origins = "http://localhost:3000")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        List<CityDTO> cities = cityService.getAllCitiesWithParticipantCount();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable UUID id) {
        CityDTO city = cityService.getCityById(id);
        return ResponseEntity.ok(city);
    }

    @PostMapping
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
        CityDTO createdCity = cityService.createCity(cityDTO);
        return ResponseEntity.ok(createdCity);
    }
}