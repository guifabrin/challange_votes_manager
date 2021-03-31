package com.github.guifabrin.votes.rest.v1.repositories;

import com.github.guifabrin.votes.rest.v1.entities.Associated;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatedRepository extends JpaRepository<Associated, String> {

    @Override
    @Query(value = "SELECT * FROM associated u WHERE u.cpf = ?1", nativeQuery = true)
    Associated getOne(String cpf);
}
