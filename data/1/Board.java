package com.example.jira.data.model;

public class Board {
    private final String id;
    private final String projectId;
    private final String name;
    private final String description;

    public Board(String id, String projectId, String name, String description) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

