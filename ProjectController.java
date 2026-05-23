package com.example.demo.project;

import com.example.demo.board.BoardDto;
import com.example.demo.board.BoardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;

    public ProjectController(ProjectRepository projectRepository, BoardRepository boardRepository) {
        this.projectRepository = projectRepository;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/projects")
    public List<ProjectDto> getProjects() {
        return projectRepository.findAll().stream().map(ProjectDto::fromEntity).toList();
    }

    @GetMapping("/projects/{projectId}/boards")
    public List<BoardDto> getBoardsByProject(@PathVariable String projectId) {
        return boardRepository.findAllByProjectId(projectId).stream().map(BoardDto::fromEntity).toList();
    }
}

