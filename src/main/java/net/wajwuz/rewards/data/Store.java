package net.wajwuz.rewards.data;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Store {
    protected final Map<String, User> userMap = new ConcurrentHashMap<>();
    //ty no nie wiem czy ci pamiec wytrzyma

    public User getUser(String playerName, String userId) {
        if (userMap.containsKey(playerName))
            return userMap.get(playerName);
        else
            return findById(userId).orElse(userMap.computeIfAbsent(playerName, name -> new User(playerName, userId, false)));
    }

    public Optional<User> getUser(String identifier) {
        return Optional.ofNullable(userMap.get(identifier)).map(Optional::of).orElse(findById(identifier));
    }

    private Optional<User> findById(String userId) {
        return userMap.entrySet().stream()
                .filter(entry -> entry.getValue().getDiscordUserId().equals(userId))
                .findAny().map(Map.Entry::getValue);
    }

    public abstract void loadData();

    public abstract void saveData();
}
