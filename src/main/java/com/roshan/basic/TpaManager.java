package com.roshan.basic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaManager {

    // Nested class to store requester and timestamp
    private static class TpaRequest {
        UUID requester;
        long timestamp;

        TpaRequest(UUID requester) {
            this.requester = requester;
            this.timestamp = System.currentTimeMillis();
        }
    }

    // Key = target UUID, Value = TpaRequest
    private final Map<UUID, TpaRequest> requests = new HashMap<>();
    private final long EXPIRY_MILLIS = 60 * 1000; // 60 seconds

    /** Add a new TPA request */
    public void addRequest(Player requester, Player target) {
        requests.put(target.getUniqueId(), new TpaRequest(requester.getUniqueId()));
    }

    /** Get the requester Player object for a target */
    public Player getRequester(Player target) {
        TpaRequest request = requests.get(target.getUniqueId());
        if (request == null) return null;

        // Expiry check
        if (System.currentTimeMillis() - request.timestamp > EXPIRY_MILLIS) {
            requests.remove(target.getUniqueId());
            return null;
        }

        return Bukkit.getPlayer(request.requester);
    }

    /** Check if a target has a pending request */
    public boolean hasRequest(Player target) {
        Player requester = getRequester(target);
        return requester != null;
    }

    /** Remove a pending request for a target */
    public void removeRequest(Player target) {
        requests.remove(target.getUniqueId());
    }
}
