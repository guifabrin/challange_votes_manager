package com.github.guifabrin.votes.rest.v1.controllers;

import java.util.List;

import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import com.github.guifabrin.votes.rest.v1.repositories.SheduleRepository;

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
public class SheduleController {

    @Autowired
    public SheduleRepository repository;

    @GetMapping("/shedule/list")
    List<Shedule> all() {
        return repository.findAll();
    }

    @PostMapping("/shedule/add")
    ResponseEntity<Shedule> add(@RequestBody Shedule shedule) {
        return new ResponseEntity<>(repository.save(shedule), HttpStatus.CREATED);
    }

    @PutMapping("/shedule/edit/{id}")
    ResponseEntity<Shedule> update(@PathVariable("id") Long id, @RequestBody Shedule shedule) {
        Shedule record = repository.getOne(id);
        if (record == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        record.setName(shedule.getName());
        record.setDescription(shedule.getDescription());
        record.setStartDate(shedule.getStartDate());
        record.setMinutes(shedule.getMinutes());
        repository.save(record);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/shedule/del/{id}")
    ResponseEntity<Shedule> delete(@PathVariable("id") Long id) {
        Shedule record = repository.getOne(id);
        if (record == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        repository.delete(record);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    public void clear() {
        repository.deleteAll();
    }
}
