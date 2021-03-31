package com.github.guifabrin.votes.rest.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import com.github.guifabrin.votes.rest.v1.repositories.SheduleRepository;

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
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(SheduleController.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SheduleRepository repository;

    @Test
    void creation() throws Exception {
        Shedule shedule = new Shedule();
        shedule.setName("Lorem ipsum");
        shedule.setDescription(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ac nisi vitae lectus efficitur sodales id eleifend dolor. Phasellus vel quam ut tellus volutpat venenatis. Aenean iaculis elementum gravida. Aliquam ut est mauris. Vivamus eget viverra dui. Cras sit amet suscipit diam. Integer in ullamcorper orci, pretium gravida augue. Etiam ac facilisis velit, sit amet pharetra risus. Nam commodo non augue quis interdum. Cras tincidunt nisl eu malesuada porta.");
        shedule.setStartDate(new Date());
        shedule.setMinutes(5);
        String json = objectMapper.writeValueAsString(shedule);
        mockMvc.perform(post("/shedule/add").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @BeforeEach
    public void setup() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<Shedule> entities = new ArrayList<>();
        Shedule shedule = new Shedule();
        shedule.setName("Lorem ipsum");
        shedule.setDescription(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ac nisi vitae lectus efficitur sodales id eleifend dolor. Phasellus vel quam ut tellus volutpat venenatis. Aenean iaculis elementum gravida. Aliquam ut est mauris. Vivamus eget viverra dui. Cras sit amet suscipit diam. Integer in ullamcorper orci, pretium gravida augue. Etiam ac facilisis velit, sit amet pharetra risus. Nam commodo non augue quis interdum. Cras tincidunt nisl eu malesuada porta.");
        shedule.setStartDate(new Date());
        shedule.setMinutes(5);
        shedule.setId(1L);
        entities.add(shedule);
        when(repository.findAll()).thenReturn(entities);
    }

    @Test
    void update() throws Exception {
        Shedule shedule = new Shedule();
        shedule.setName("Guilherme Franco Fabrin");
        String json = objectMapper.writeValueAsString(shedule);
        mockMvc.perform(put("/shedule/edit/{id}", repository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    void updateNotFound() throws Exception {
        Shedule shedule = new Shedule();
        shedule.setName("Lorem not found");
        String json = objectMapper.writeValueAsString(shedule);
        mockMvc.perform(put("/shedule/edit/9999").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/shedule/del/{id}", repository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/shedule/del/{id}", "9999").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
