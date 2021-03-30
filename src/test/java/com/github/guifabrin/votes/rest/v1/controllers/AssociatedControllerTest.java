package com.github.guifabrin.votes.rest.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.repositories.AssociatedRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(AssociatedController.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AssociatedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssociatedRepository repository;

    @BeforeEach
    public void setup() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<Associated> someEntitys = new ArrayList<>();
        Associated associated = new Associated();
        associated.setCPF("01661695019");
        associated.setName("Guilherme Fabrin Franco");
        associated.setPassword("123456Guii*");
        someEntitys.add(associated);
        when(repository.findAll()).thenReturn(someEntitys);
    }

    @Order(1)
    @Test
    void creation() throws Exception {
        Associated associated = new Associated();
        associated.setCPF("01661695099");
        associated.setName("Guilherme Fabrin Franco");
        associated.setPassword("123456Guii*");
        String json = objectMapper.writeValueAsString(associated);
        mockMvc.perform(post("/associated/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void duplicate() throws Exception {
        Associated associated = new Associated();
        associated.setCPF("01661695019");
        associated.setName("Guilherme Fabrin Franco");
        associated.setPassword("123456Guii*");
        String json = objectMapper.writeValueAsString(associated);
        mockMvc.perform(post("/associated/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        Associated associated = new Associated();
        associated.setName("Guilherme Franco Fabrin");
        String json = objectMapper.writeValueAsString(associated);
        mockMvc.perform(put("/associated/edit/01661695019").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updateNotFound() throws Exception {
        Associated associated = new Associated();
        associated.setName("Guilherme Franco Fabrin");
        String json = objectMapper.writeValueAsString(associated);
        mockMvc.perform(put("/associated/edit/12345678999").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/associated/del/{cpf}", "01661695019")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/associated/del/{cpf}", "12345678999")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}
