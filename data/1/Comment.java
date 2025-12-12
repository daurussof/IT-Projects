package com.example.jira.data.model;

public class Comment {
    private final String id;
    private final String issueId;
    private final String author;
    private final String message;
    private final long createdAt;

    public Comment(String id, String issueId, String author, String message, long createdAt) {
        this.id = id;
        this.issueId = issueId;
        this.author = author;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}

