package fr.nilowk.spawnhead.db;

import java.util.*;
import java.util.UUID;

public class Profile {

    private UUID uuid;
    private String name;
    private List<String> heads;
    private List<Integer> achievements;

    public Profile(UUID uuid, String name, List<String> heads, List<Integer> achievements) {

        this.uuid = uuid;
        this.name = name;
        this.heads = heads;
        this.achievements = achievements;

    }

    public UUID getUuid() {

        return uuid;

    }

    public String getName() {

        return name;

    }

    public List<String> getHeads() {

        return heads;

    }

    public List<Integer> getAchievements() {

        return achievements;

    }

    public void setUuid(UUID uuid) {

        this.uuid = uuid;

    }

    public void setName(String name) {

        this.name = name;

    }

}
