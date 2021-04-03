package com.github.guifabrin.votes.rest.v1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guifabrin.votes.rest.v1.entities.Associated;
import com.github.guifabrin.votes.rest.v1.entities.Shedule;
import com.github.guifabrin.votes.rest.v1.entities.Vote;
import com.github.guifabrin.votes.rest.v1.repositories.SheduleRepository;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SheduleSocketServer extends WebSocketServer {

    @Autowired
    public SheduleRepository repository;

    private static SheduleSocketServer socket;

    private final HashMap<String, WebSocket> connections = new HashMap<>();

    public SheduleSocketServer() {
        super(new InetSocketAddress(8081));
        socket = this;
        this.start();
    }

    public static void broadcastShedule() {
        try {
            List<Shedule> shedules = socket.repository.findAll();
            for (Map.Entry<String, WebSocket> pair : socket.connections.entrySet()) {
                ObjectMapper objectMapper = new ObjectMapper();
                Associated associated = AuthManager.getByUUID(pair.getKey());
                shedules.stream().forEachOrdered(shedule -> {
                    shedule.setVoted(false);
                    Collection<Vote> sheduleVotes = shedule.getVotes();
                    sheduleVotes.forEach(vote -> {
                        if (associated.isVoted(vote)) {
                            shedule.setVoted(associated.isVoted(vote));
                        }
                    });
                });

                String jsonString = objectMapper.writeValueAsString(shedules);
                if (pair.getValue().isOpen()) {
                    pair.getValue().send(jsonString);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void stopServer() throws IOException, InterruptedException {
        socket.stop();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        connections.put(message, conn);
        broadcastShedule();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
    }

    @Override
    public void onStart() {
    }
}
