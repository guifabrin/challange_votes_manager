package com.github.guifabrin.votes.rest.v1.repositories;

import com.github.guifabrin.votes.rest.v1.entities.Shedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SheduleRepository extends JpaRepository<Shedule, Long> {

    @Override
    @Query(value = "SELECT * FROM shedule u WHERE u.id = ?1", nativeQuery = true)
    Shedule getOne(Long id);
}
