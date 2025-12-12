package com.example.jira.data;

import com.example.jira.data.model.Board;
import com.example.jira.data.model.Comment;
import com.example.jira.data.model.Issue;
import com.example.jira.data.model.Project;
import com.example.jira.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class IssueRepository {
    private static IssueRepository INSTANCE;

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Project> projects = new HashMap<>();
    private final Map<String, Board> boards = new HashMap<>();
    private final Map<String, Issue> issues = new HashMap<>();

    private IssueRepository() {
        seedData();
    }

    public static IssueRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IssueRepository();
        }
        return INSTANCE;
    }

    private void seedData() {
        User admin = new User("u1", "Admin", "admin", "password");
        User demo = new User("u2", "Demo User", "demo", "password");
        users.put(admin.getUsername(), admin);
        users.put(demo.getUsername(), demo);

        Project project = new Project("p1", "Mobile App", "MOB");
        Project project2 = new Project("p2", "Backend API", "API");
        projects.put(project.getId(), project);
        projects.put(project2.getId(), project2);

        Board board1 = new Board("b1", project.getId(), "Mobile Kanban", "Mobile backlog and delivery");
        Board board2 = new Board("b2", project2.getId(), "API Board", "Backend roadmap");
        boards.put(board1.getId(), board1);
        boards.put(board2.getId(), board2);

        Issue i1 = new Issue("i1", board1.getId(), "Экран авторизации", "Разработать экраны входа и регистрации", "К выполнению", "Admin", "Высокий");
        Issue i2 = new Issue("i2", board1.getId(), "Список проектов", "Отобразить проекты со статусами", "В работе", "Demo User", "Средний");
        Issue i3 = new Issue("i3", board2.getId(), "REST API", "Определить endpoints для задач", "К выполнению", "Admin", "Высокий");

        i2.addComment(new Comment(UUID.randomUUID().toString(), i2.getId(), "Admin", "Черновик макета готов", System.currentTimeMillis()));
        issues.put(i1.getId(), i1);
        issues.put(i2.getId(), i2);
        issues.put(i3.getId(), i3);
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects.values());
    }

    public List<Board> getBoardsForProject(String projectId) {
        return boards.values()
                .stream()
                .filter(board -> board.getProjectId().equals(projectId))
                .collect(Collectors.toList());
    }

    public List<Board> getAllBoards() {
        return new ArrayList<>(boards.values());
    }

    public Board findBoard(String boardId) {
        return boards.get(boardId);
    }

    public List<Issue> getIssuesForBoard(String boardId) {
        return issues.values()
                .stream()
                .filter(issue -> issue.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    }

    public Issue findIssue(String issueId) {
        return issues.get(issueId);
    }

    public Issue updateStatus(String issueId, String status) {
        Issue issue = issues.get(issueId);
        if (issue != null) {
            issue.setStatus(status);
        }
        return issue;
    }

    public Issue createIssue(String boardId, String title, String description, String priority, String assignee) {
        String id = UUID.randomUUID().toString();
        Issue issue = new Issue(id, boardId, title, description, "To Do", assignee, priority);
        issues.put(issue.getId(), issue);
        return issue;
    }

    public Comment addComment(String issueId, String author, String message) {
        Issue issue = issues.get(issueId);
        if (issue == null) {
            return null;
        }
        Comment comment = new Comment(UUID.randomUUID().toString(), issueId, author, message, System.currentTimeMillis());
        issue.addComment(comment);
        return comment;
    }
}

