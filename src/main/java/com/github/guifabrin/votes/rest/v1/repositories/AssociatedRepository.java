package com.github.guifabrin.votes.rest.v1.repositories;

import com.github.guifabrin.votes.rest.v1.entities.Associated;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociatedRepository extends JpaRepository<Associated, String> {
}
