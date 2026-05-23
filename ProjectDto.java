package com.example.demo.project;

public class ProjectDto {
    private String id;
    private String name;
    private String description;

    public static ProjectDto fromEntity(ProjectEntity e) {
        ProjectDto dto = new ProjectDto();
        dto.id = e.getId();
        dto.name = e.getName();
        dto.description = e.getDescription();
        return dto;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

