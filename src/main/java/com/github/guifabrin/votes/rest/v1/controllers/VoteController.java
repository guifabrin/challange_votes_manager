package com.github.guifabrin.votes.rest.v1.controllers;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import com.github.guifabrin.votes.rest.v1.entities.Vote;
import com.github.guifabrin.votes.rest.v1.repositories.AssociatedRepository;
import com.github.guifabrin.votes.rest.v1.repositories.SheduleRepository;
import com.github.guifabrin.votes.rest.v1.repositories.VoteRepository;
import com.github.guifabrin.votes.rest.v1.utils.AuthManager;
import com.github.guifabrin.votes.rest.v1.utils.SheduleSocketServer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    @Autowired
    public VoteRepository repository;
    @Autowired
    public AssociatedRepository associatedRepository;
    @Autowired
    public SheduleRepository sheduleRepository;

    @GetMapping("/api/v1/vote/list")
    List<Vote> all() {
        return repository.findAll();
    }

    @PostMapping("/api/v1/vote/{sheduleId}")
    ResponseEntity<Vote> add(@RequestBody Vote vote, @PathVariable("sheduleId") Long sheduleId,
            @RequestHeader(value = "uuid") String uuid) {
        Associated associated = AuthManager.getByUUID(uuid);
        associated.addVote(vote);
        Shedule shedule = sheduleRepository.getOne(sheduleId);
        shedule.addVote(vote);
        repository.save(vote);
        associatedRepository.save(associated);
        sheduleRepository.save(shedule);
        return this.notifyChange(new ResponseEntity<>(null, HttpStatus.OK));
    }

    public void clear() {
        repository.deleteAll();
    }

    private ResponseEntity<Vote> notifyChange(ResponseEntity<Vote> entity) {
        SheduleSocketServer.broadcastShedule();
        return entity;
    }
}
