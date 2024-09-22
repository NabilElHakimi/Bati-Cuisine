package repository;
import config.DatabaseConnection;
import entity.Material;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepository {
    private Connection connection ;
    public MaterialRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


    //    name,  component_type, vat_rate, unit_cost, quantity, transportCost, qualityCoefficient


    public boolean save(Material material) {
        String query = "INSERT INTO materials (name,unit_cost,quantity,component_type, vat_rate, project_id ,transport_cost,quality_coefficient) VALUES (?,?,?,?,?,?,?,? )";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, material.getName());
            pr.setDouble(2, material.getUnit_cost());
            pr.setDouble(3, material.getQuantity());
            pr.setString(4, material.getComponent_type());
            pr.setDouble(5, material.getVat_rate());
            pr.setInt(6, material.getProject_id());
            pr.setDouble(7, material.getTransportCost());
            pr.setDouble(8, material.getQualityCoefficient());
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Material material) {
        String query = "UPDATE materials SET name = ?, unit_cost = ?, quantity = ?, component_type = ?, vat_rate = ?, project_id = ?, transport_cost = ?, quality_coefficient = ? WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, material.getName());
            pr.setDouble(2, material.getUnit_cost());
            pr.setDouble(3, material.getQuantity());
            pr.setString(4, material.getComponent_type());
            pr.setDouble(5, material.getVat_rate());
            pr.setInt(6, material.getProject_id());
            pr.setDouble(7, material.getTransportCost());
            pr.setDouble(8, material.getQualityCoefficient());
            pr.setInt(9, material.getId());  // Bind the id to WHERE clause
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Material> findByProjectId(int projectId) {
        String query = "SELECT * FROM materials WHERE project_id = ?";
        List<Material> materials = new ArrayList<>();

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, projectId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Material mater = new Material(
                        rs.getString("name"),
                        rs.getString("component_type"),
                        rs.getDouble("vat_rate"),
                        rs.getInt("project_id"),
                        rs.getDouble("unit_cost"),
                        rs.getDouble("quantity"),
                        rs.getDouble("transport_cost"),
                        rs.getDouble("quality_coefficient")
                );
                mater.setId(rs.getInt("id"));
                materials.add(mater);
            }

            return materials;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM materials WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            int rowsAffected = pr.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateVatRat(double varRat ,int componentId){
        String query = "UPDATE components SET var_rat = ? WHERE id = ?;";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setDouble(1, varRat);
            pr.setInt(2, componentId);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
