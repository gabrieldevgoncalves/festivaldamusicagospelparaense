package com.festival.vozesdonorteapi.repository;

import com.festival.vozesdonorteapi.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    @Query("SELECT c FROM City c ORDER BY c.name")
    List<City> findAllOrderByName();

    @Query("SELECT c FROM City c LEFT JOIN FETCH c.participants WHERE c.id = :id")
    City findByIdWithParticipants(UUID id);
}