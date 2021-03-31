package com.github.guifabrin.votes.rest.v1.controllers;

import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;

@SpringBootTest
public class SheduleControllerTest {

    @Autowired
    private SheduleController controller;

    Shedule getDefault() {
        Shedule shedule = new Shedule();
        shedule.setName("Lorem ipsum");
        shedule.setDescription(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ac nisi vitae lectus efficitur sodales id eleifend dolor. Phasellus vel quam ut tellus volutpat venenatis. Aenean iaculis elementum gravida. Aliquam ut est mauris. Vivamus eget viverra dui. Cras sit amet suscipit diam. Integer in ullamcorper orci, pretium gravida augue. Etiam ac facilisis velit, sit amet pharetra risus. Nam commodo non augue quis interdum. Cras tincidunt nisl eu malesuada porta.");
        shedule.setStartDate(new Date());
        shedule.setMinutes(5);
        return shedule;
    }

    @Test
    void creation() throws Exception {
        assertTrue(controller.add(getDefault()).getStatusCode() == HttpStatus.CREATED);
        controller.clear();
    }

    @Test
    void update() throws Exception {
        Shedule shedule = getDefault();
        assertTrue(controller.add(shedule).getStatusCode() == HttpStatus.CREATED);
        assertTrue(controller.update(shedule.getId(), shedule).getStatusCode() == HttpStatus.OK);
        controller.clear();
    }

    @Test
    void updateNotFound() throws Exception {
        assertTrue(controller.update(123456789L, null).getStatusCode() == HttpStatus.NOT_FOUND);
        controller.clear();
    }

    @Test
    void delete() throws Exception {
        Shedule shedule = getDefault();
        assertTrue(controller.add(shedule).getStatusCode() == HttpStatus.CREATED);
        assertTrue(controller.delete(shedule.getId()).getStatusCode() == HttpStatus.OK);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertTrue(controller.delete(123456789L).getStatusCode() == HttpStatus.NOT_FOUND);
        controller.clear();
    }
}
