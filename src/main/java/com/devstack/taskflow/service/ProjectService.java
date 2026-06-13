package com.devstack.taskflow.service;

import com.devstack.taskflow.dto.projectdto.ProjectRequestDto;
import com.devstack.taskflow.dto.projectdto.ProjectResponseDto;
import com.devstack.taskflow.exception.projectexceptions.ProjectNotFoundException;
import com.devstack.taskflow.exception.userexceptions.UserNotFoundException;
import com.devstack.taskflow.model.Project;
import com.devstack.taskflow.model.User;
import com.devstack.taskflow.repository.ProjectRepository;
import com.devstack.taskflow.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectResponseDto createProject(ProjectRequestDto dto) {

        User user = userRepository.findByEmail(dto.ownerEmail()).orElseThrow(
                () -> new UserNotFoundException("Nenhum usuário foi encontrado com este email.", HttpStatus.NOT_FOUND)
        );

        Project project = dtoToEntity(dto);
        project.setOwner(user);

        projectRepository.save(project);

        return entityToDto(project);
    }

    public List<ProjectResponseDto> getAllProjects() {
        List<ProjectResponseDto> projects = new ArrayList<>();
        projectRepository.findAll().forEach(p -> projects.add(entityToDto(p)));

        return projects;
    }

    public ProjectResponseDto getProject (Long id) {

        Project project = getInternalProject(id);

        return entityToDto(project);
    }

    public void deleteProject(Long id) {

        Project project = getInternalProject(id);

        projectRepository.delete(project);
    }

    private Project getInternalProject(Long id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Projeto não encontrado.", HttpStatus.NOT_FOUND)
        );
    }

    private ProjectResponseDto entityToDto(Project project) {

        return new ProjectResponseDto(
                project.getName(),
                project.getDescription(),
                project.getOwner().getName()
        );
    }

    private Project dtoToEntity(ProjectRequestDto dto) {

        Project project = new Project();

        project.setName(dto.name());
        project.setDescription(dto.description());
        project.setCreatedAt(LocalDateTime.now());
        return project;
    }
}
