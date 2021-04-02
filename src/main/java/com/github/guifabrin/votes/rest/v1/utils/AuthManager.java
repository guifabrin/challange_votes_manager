package com.github.guifabrin.votes.rest.v1.utils;

import com.github.guifabrin.votes.rest.v1.entities.Associated;
import java.util.HashMap;
import java.util.Map.Entry;

public class AuthManager {
    private static HashMap<String, Associated> auths = new HashMap<>();

    public static Associated getByUUID(String uuid) {
        return auths.get(uuid);
    }

    public static void setAssociatedSession(String uuid, Associated associated) {
        if (auths.containsValue(associated)) {
            for (Entry<String, Associated> entry : auths.entrySet()) {
                if (entry.getValue().equals(associated)) {
                    auths.remove(entry.getKey());
                    break;
                }
            }
        }
        auths.put(uuid, associated);
    }

    public static void clear() {
        auths.clear();
    }
}
