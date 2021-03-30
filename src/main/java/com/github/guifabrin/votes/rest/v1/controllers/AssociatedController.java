package com.github.guifabrin.votes.rest.v1.controllers;

import java.util.List;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.repositories.AssociatedRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssociatedController {

    private final AssociatedRepository repository;

    AssociatedController(AssociatedRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/associated")
    List<Associated> all() {
        return repository.findAll();
    }

    @PostMapping("/associated")
    ResponseEntity<Associated> newAssociated(@RequestBody Associated associated) {
        if (repository.findByCPF(associated.getCpf()) != null)
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        return new ResponseEntity<>(repository.save(associated), HttpStatus.CREATED);
    }
}
