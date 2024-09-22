package service;
import entity.Project;
import repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

public class ProjectService {

    public Project insertProject(Project project) {
        project.setId(new ProjectRepository().save(Optional.of(project)));
        return project;
    }

    public boolean updateProject(Project project)  {
        return
                new ProjectRepository()
                        .updateProject(project);
    }

    public boolean updateProjectStatus(Project project)  {
        return
                new ProjectRepository()
                        .updateStatus(project);
    }

    public boolean assignToClient(Project project) {
        return
                new ProjectRepository()
                        .updateStatus(project);
    }

    public List<Project> getClientProjectById(int id) {
        return
                new ProjectRepository().getClientProjectById(id);
    }

    public List<Project> getAllProjectsWithClient(int client_id) {
        return new ProjectRepository().getAllProjectWithClient(client_id);
    }

    public List<Project> getAllProjects() {
        return new ProjectRepository().findAll();
    }

    public Optional<Project> findById(int id) {
        return new ProjectRepository().findById(id);
    }

    public boolean delete(int id) {
        return true ;
    }
}
