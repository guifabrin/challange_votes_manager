package com.github.guifabrin.votes.rest.v1.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.entities.Status;
import com.github.guifabrin.votes.rest.v1.repositories.AssociatedRepository;
import com.github.guifabrin.votes.rest.v1.utils.AuthManager;
import com.github.guifabrin.votes.rest.v1.utils.ChyperUtils;
import com.google.gson.Gson;
import java.util.UUID;

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

    @GetMapping("/api/v1/associated/list")
    List<Associated> all() {
        return repository.findAll();
    }

    @PostMapping("/api/v1/associated/add")
    ResponseEntity<Associated> add(@RequestBody Associated associated) {
        if (repository.getOne(associated.getCPF()) == null) {
            return new ResponseEntity<>(repository.save(associated), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @PutMapping("/api/v1/associated/edit/{cpf}")
    ResponseEntity<Associated> update(@PathVariable("cpf") String cpf, @RequestBody Associated associated)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Associated record = repository.getOne(cpf);
        if (record == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        record.setName(associated.getName());
        if (!associated.getPassword().equals(ChyperUtils.encrypt(""))) {
            record.setEncryptedPassword(associated.getPassword());
        }
        return new ResponseEntity<>(repository.save(record), HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/associated/del/{cpf}")
    ResponseEntity<Associated> delete(@PathVariable("cpf") String cpf) {
        Associated record = repository.getOne(cpf);
        if (record == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        repository.delete(record);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/api/v1/associated/login/{cpf}")
    ResponseEntity<String> check(@PathVariable("cpf") String cpf, @RequestBody String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Associated record = repository.getOne(cpf);
        if (record == null) {
            if (repository.count() == 0) {
                record = new Associated();
                record.setCPF(cpf);
                record.setPassword(password);
                record.setName("First associated");
                repository.save(record);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        boolean passwordOk = record.getPassword().equals(ChyperUtils.encrypt(password));
        if (passwordOk) {
            String uuid = UUID.randomUUID().toString();
            AuthManager.setAssociatedSession(uuid, record);
            return new ResponseEntity<>(uuid, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    private Status getStatus(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), Status.class);
    }

    @GetMapping("/api/v1/associated/{cpf}/able")
    ResponseEntity<String> able(@PathVariable("cpf") String cpf) {
        try {
            if (this.getStatus("https://user-info.herokuapp.com/users/" + cpf).isAble()) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public void clear() {
        repository.deleteAll();
    }
}
