package com.festival.vozesdonorteapi.repository;

import com.festival.vozesdonorteapi.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    @Query("SELECT p FROM Participant p JOIN FETCH p.city ORDER BY p.createdAt DESC")
    List<Participant> findAllWithCity();

    @Query("SELECT p FROM Participant p JOIN FETCH p.city WHERE p.city.id = :cityId ORDER BY p.createdAt DESC")
    List<Participant> findByCityIdWithCity(@Param("cityId") UUID cityId);

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    @Query("SELECT COUNT(p) FROM Participant p WHERE p.city.id = :cityId")
    Long countByCityId(@Param("cityId") UUID cityId);
}