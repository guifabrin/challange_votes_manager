package com.github.guifabrin.votes;

import com.github.guifabrin.votes.rest.v1.utils.SheduleSocketServer;
import java.io.IOException;
import javax.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotesApplication {

	public static void main(String[] args) throws IOException {
            SpringApplication.run(VotesApplication.class, args);
	}

	@PreDestroy
	public void onExit() throws IOException, InterruptedException {
            SheduleSocketServer.stopServer();
	}
}