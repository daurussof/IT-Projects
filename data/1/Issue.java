package com.example.jira.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Issue {
    private final String id;
    private final String boardId;
    private final String title;
    private final String description;
    private String status;
    private final String assignee;
    private final String priority;
    private final List<Comment> comments = new ArrayList<>();

    public Issue(String id, String boardId, String title, String description, String status, String assignee, String priority) {
        this.id = id;
        this.boardId = boardId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignee = assignee;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getPriority() {
        return priority;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}

