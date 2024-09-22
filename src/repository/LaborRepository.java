package repository;

import config.DatabaseConnection;
import entity.Labor;
import entity.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaborRepository {
    private Connection connection;
    public LaborRepository() {

        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // name,unitCost,quantity,componentType, VATRate, project_id , hourlyRate, hoursWorked, workerProductivity


    public boolean save(Labor labor) {
        String query = "INSERT INTO " +
                "labors (name,component_type, vat_rate, project_id ," +
                " hourly_rate, work_hours, worker_productivity)" +
                "VALUES (?,?,?,?,?,?,? )";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, labor.getName());
            pr.setString(2, labor.getComponent_type());
            pr.setDouble(3, labor.getVat_rate());
            pr.setInt(4, labor.getProject_id());
            pr.setDouble(5, labor.getHourly_rate());
            pr.setDouble(6, labor.getWork_hours());
            pr.setDouble(7, labor.getWorker_productivity());
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean update(Labor labor) {
        String query = "UPDATE labors SET name = ?, component_type = ?, vat_rate = ?, project_id = ?, hourly_rate = ?, work_hours = ?, worker_productivity = ? WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, labor.getName());
            pr.setString(2, labor.getComponent_type());
            pr.setDouble(3, labor.getVat_rate());
            pr.setInt(4, labor.getProject_id());
            pr.setDouble(5, labor.getHourly_rate());
            pr.setDouble(6, labor.getWork_hours());
            pr.setDouble(7, labor.getWorker_productivity());
            pr.setInt(8, labor.getId());
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM labors WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Labor findById(int id) {
        String query = "SELECT * FROM labors WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                Labor labor = new Labor(
                        rs.getString("name"),
                        rs.getDouble("vat_rate"),
                        rs.getInt("project_id"),
                        rs.getString("component_type"),
                        rs.getDouble("hourly_rate"),
                        rs.getDouble("work_hours"),
                        rs.getDouble("worker_productivity")
                );
                labor.setId(rs.getInt("id"));  // Set ID separately
                return labor;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Labor> findByProjectId(int projectId) {
        String query = "SELECT * FROM labors WHERE project_id = ?";
        List<Labor> labors = new ArrayList<>();

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, projectId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Labor labor = new Labor(
                        rs.getString("name"),
                        rs.getDouble("vat_rate"),
                        rs.getInt("project_id"),
                        rs.getString("component_type"),
                        rs.getDouble("hourly_rate"),
                        rs.getDouble("work_hours"),
                        rs.getDouble("worker_productivity")
                );
                labor.setId(rs.getInt("id"));
                labors.add(labor);
            }

            return labors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
