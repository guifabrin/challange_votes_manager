package com.github.guifabrin.votes.rest.v1.controllers;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class AssociatedControllerTest {

    Associated getDefault() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Associated associated = new Associated();
        associated.setCPF("01661695019");
        associated.setName("Guilherme Fabrin Franco");
        associated.setPassword("123456Guii*");
        return associated;
    }

    @Autowired
    private AssociatedController controller;

    @Test
    public void contextLoads() throws Exception {
        assertTrue(controller != null);
    }

    @Test
    void creation() throws Exception {
        assertTrue(controller.add(getDefault()).getStatusCode() == HttpStatus.OK);
        controller.clear();
    }

    @Test
    void duplicate() throws Exception {
        Associated associated = getDefault();
        assertTrue(controller.add(associated).getStatusCode() == HttpStatus.OK);
        assertTrue(controller.add(associated).getStatusCode() == HttpStatus.CONFLICT);
        controller.clear();
    }

    @Test
    void update() throws Exception {
        Associated associated = getDefault();
        assertTrue(controller.add(associated).getStatusCode() == HttpStatus.OK);
        assertTrue(controller.update(associated.getCPF(), associated).getStatusCode() == HttpStatus.OK);
        controller.clear();
    }

    @Test
    void updateNotFound() throws Exception {
        assertTrue(controller.update("01234567891", null).getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    void delete() throws Exception {
        Associated associated = getDefault();
        assertTrue(controller.add(associated).getStatusCode() == HttpStatus.OK);
        assertTrue(controller.delete(associated.getCPF()).getStatusCode() == HttpStatus.OK);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertTrue(controller.delete("01234567891").getStatusCode() == HttpStatus.NOT_FOUND);
    }
}
