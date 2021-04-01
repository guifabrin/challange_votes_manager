package com.github.guifabrin.votes.rest.v1.controllers;

import com.github.guifabrin.votes.rest.v1.components.AuthManager;
import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import com.github.guifabrin.votes.rest.v1.entities.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@SpringBootTest
public class VoteControllerTest {

    @Autowired
    private VoteController controller;

    @Autowired
    private SheduleController sheduleController;

    @Autowired
    private AssociatedController associatedController;

    Shedule getDefaultShedule() {
        Shedule shedule = new Shedule();
        shedule.setName("Lorem ipsum");
        shedule.setDescription(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ac nisi vitae lectus efficitur sodales id eleifend dolor. Phasellus vel quam ut tellus volutpat venenatis. Aenean iaculis elementum gravida. Aliquam ut est mauris. Vivamus eget viverra dui. Cras sit amet suscipit diam. Integer in ullamcorper orci, pretium gravida augue. Etiam ac facilisis velit, sit amet pharetra risus. Nam commodo non augue quis interdum. Cras tincidunt nisl eu malesuada porta.");
        shedule.setStartDate(new Date());
        shedule.setMinutes(5);
        return shedule;
    }

    Associated getDefaultAssociated() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Associated associated = new Associated();
        associated.setCPF("01661695019");
        associated.setName("Guilherme Fabrin Franco");
        associated.setPassword("123456Guii*");
        return associated;
    }

    @Test
    void creation() throws Exception {
        Vote vote = new Vote();
        vote.setVote(false);
        Associated associated = getDefaultAssociated();
        Shedule shedule = getDefaultShedule();
        associatedController.add(associated);
        sheduleController.add(shedule);

        AuthManager.setAssociatedSession("random-uuid", associated);

        assertTrue(controller.add(vote, shedule.getId(), "random-uuid").getStatusCode() == HttpStatus.OK);
        AuthManager.clear();
        controller.clear();
        associatedController.clear();
        sheduleController.clear();
    }
}
