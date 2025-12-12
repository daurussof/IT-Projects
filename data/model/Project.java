package com.example.jira.data.model;

public class Project {
    private final String id;
    private final String name;
    private final String key;

    public Project(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}

