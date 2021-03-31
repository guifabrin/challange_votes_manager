package com.github.guifabrin.votes.rest.v1.controllers;

import java.util.List;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.repositories.AssociatedRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AssociatedRepository repository;

    @GetMapping("/associated/list")
    List<Associated> all() {
        return repository.findAll();
    }

    @PostMapping("/associated/add")
    ResponseEntity<Associated> add(@RequestBody Associated associated) {
        if (repository.getOne(associated.getCPF()) == null)
            return new ResponseEntity<>(repository.save(associated), HttpStatus.CREATED);
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @PutMapping("/associated/edit/{cpf}")
    ResponseEntity<Associated> update(@PathVariable("cpf") String cpf, @RequestBody Associated associated) {
        Associated record = repository.getOne(cpf);
        if (record == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        record.setName(associated.getName());
        repository.save(record);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/associated/del/{cpf}")
    ResponseEntity<Associated> delete(@PathVariable("cpf") String cpf) {
        Associated record = repository.getOne(cpf);
        if (record == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        repository.delete(record);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public void clear() {
        repository.deleteAll();
    }
}
