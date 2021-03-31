package com.github.guifabrin.votes.rest.v1.controllers;

import java.util.List;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import com.github.guifabrin.votes.rest.v1.entities.Vote;
import com.github.guifabrin.votes.rest.v1.repositories.AssociatedRepository;
import com.github.guifabrin.votes.rest.v1.repositories.SheduleRepository;
import com.github.guifabrin.votes.rest.v1.repositories.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    @Autowired
    public VoteRepository repository;
    @Autowired
    public AssociatedRepository associatedRepository;
    @Autowired
    public SheduleRepository sheduleRepository;

    @GetMapping("/vote/list")
    List<Vote> all() {
        return repository.findAll();
    }

    @PostMapping("/vote/{sheduleId}/{cpf}")
    ResponseEntity<Vote> add(@RequestBody Vote vote, @PathVariable("sheduleId") Long sheduleId,
            @PathVariable("cpf") String cpf) {
        Associated associated = associatedRepository.getOne(cpf);
        associated.addVote(vote);
        Shedule shedule = sheduleRepository.getOne(sheduleId);
        shedule.addVote(vote);
        repository.save(vote);
        associatedRepository.save(associated);
        sheduleRepository.save(shedule);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    public void clear() {
        repository.deleteAll();
    }
}
