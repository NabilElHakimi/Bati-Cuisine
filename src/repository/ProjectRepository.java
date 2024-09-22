package repository;

import config.DatabaseConnection;
import entity.Client;
import entity.Project;
import enums.ProjectStatus;
import repository.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements Crud<Project> {
    private final Connection connection;
    public ProjectRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // Work
    public int save(Optional<Project> projectOpt) {
        if(projectOpt.isPresent()) {
            Project project = projectOpt.get();
            String query = "INSERT INTO projects (client_id, project_name, capacity, status) " +
                    "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
            pr.setInt(1, project.getClientId());
            pr.setString(2, project.getProject_name());
            pr.setDouble(3, project.getCapacity());
            pr.setObject(4, project.getStatus().name() , Types.OTHER);
            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            return 0;
        }
        }

        return 0;
    }

    // Work
    public boolean updateProject(Project project) {
        String query = "UPDATE projects SET profit_margin = ?," +
                " total_cost = ? " +
                " WHERE id = ?";

        try {
                    PreparedStatement pr =  connection.prepareStatement(query);
                    pr.setDouble(1, project.getProfit_margin());
                    pr.setDouble(2, project.getTotal_cost());
                    pr.setInt(3, project.getId());
                    pr.executeUpdate();
                    return true;
                } catch (Exception e) {
                    return false;
            }
    }

    //Work
    public boolean updateStatus(Project project) {
        String query = "UPDATE projects SET status = ? WHERE id = ?";
        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setObject(1, project.getStatus().name() , Types.OTHER );
            pr.setInt(2, project.getId());
            int rowsAffected = pr.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    //Work
    public Optional<Project> findById(int id) {
        String query = "SELECT * FROM projects WHERE id = ?";
        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, id);
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    Project project =  new Project(
                            rs.getInt("id"),
                            rs.getString("project_name"),
                            rs.getDouble("profit_margin"),
                            rs.getDouble("capacity"),
                            rs.getDouble("total_cost"),
                            ProjectStatus.valueOf(rs.getString("status"))
                            );
                    project.setId(rs.getInt("id"));
                    project.setClientId(rs.getInt("client_id"));
                    return Optional.of(project);
                } else {

                    return null;
                }
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    //Work
    public boolean assignToClient(Project project) {
        String query = "UPDATE project SET client_id = ? WHERE id = ?";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, project.getClientId());
            pr.setInt(2, project.getId());
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Work
    public List<Project> getClientProjectById(int client_id) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project WHERE client_id = ?";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, client_id);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project(
                            rs.getInt("id"),
                            rs.getString("project_name"),
                            rs.getDouble("capacity"),
                            rs.getDouble("profit_margin"),
                            rs.getDouble("total_cost"),
                            ProjectStatus.valueOf(rs.getString("project_status"))
                    );
                    project.setId(rs.getInt("id"));
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return projects;
    }

    public List<Project> getAllProjectWithClient(int client_id) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project WHERE client_id = ?";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, client_id);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String projectName = rs.getString("project_name");
                    double profitMargin = rs.getDouble("profit_margin");
                    double totalCost = rs.getDouble("total_cost");
                    ProjectStatus projectStatus = ProjectStatus.valueOf(rs.getString("status"));
                    double capacity = rs.getDouble("capacity");

                    projects.add(new Project(id, projectName, profitMargin, capacity ,totalCost , projectStatus));

                }
            }
            return projects;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Project> findAll(){
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects" ;
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project(
                            rs.getInt("client_id"),
                            rs.getString("project_name"),
                            rs.getDouble("capacity") ,
                            rs.getDouble("profit_margin") ,
                            rs.getDouble("total_cost"),
                            ProjectStatus.valueOf(rs.getString("status"))
                            );
                    project.setId(rs.getInt("id"));
                    projects.add(project);
                }
                return projects;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
