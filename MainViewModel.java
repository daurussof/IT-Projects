package com.example.jira;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jira.data.IssueRepository;
import com.example.jira.data.model.Board;
import com.example.jira.data.model.Issue;
import com.example.jira.data.model.Project;
import com.example.jira.data.model.User;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final IssueRepository repository = IssueRepository.getInstance();

    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final MutableLiveData<List<Project>> projects = new MutableLiveData<>();
    private final MutableLiveData<List<Board>> boards = new MutableLiveData<>();
    private final MutableLiveData<List<Issue>> issues = new MutableLiveData<>();
    private final MutableLiveData<Issue> selectedIssue = new MutableLiveData<>();

    public boolean login(String username, String password) {
        User user = repository.login(username, password);
        if (user != null) {
            currentUser.postValue(user);
            projects.postValue(repository.getProjects());
            boards.postValue(repository.getAllBoards());
            return true;
        }
        return false;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public LiveData<List<Board>> getBoards() {
        return boards;
    }

    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    public LiveData<Issue> getSelectedIssue() {
        return selectedIssue;
    }

    public void loadBoards(String projectId) {
        boards.postValue(repository.getBoardsForProject(projectId));
    }

    public void loadIssues(String boardId) {
        issues.postValue(repository.getIssuesForBoard(boardId));
    }

    public void openIssue(String issueId) {
        Issue issue = repository.findIssue(issueId);
        selectedIssue.postValue(issue);
    }

    public void updateStatus(String issueId, String status) {
        Issue updated = repository.updateStatus(issueId, status);
        selectedIssue.postValue(updated);
        if (updated != null) {
            loadIssues(updated.getBoardId());
        }
    }

    public void createIssue(String boardId, String title, String description, String priority, String assignee) {
        Issue issue = repository.createIssue(boardId, title, description, priority, assignee);
        loadIssues(boardId);
        selectedIssue.postValue(issue);
    }

    public void addComment(String issueId, String author, String message) {
        repository.addComment(issueId, author, message);
        openIssue(issueId);
    }
}

