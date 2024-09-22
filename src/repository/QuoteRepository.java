package repository;

import config.DatabaseConnection;
import entity.Quote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuoteRepository {

    private Connection connection;
    public QuoteRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean save(Quote quote) {
        String query = "INSERT INTO quotes (estimated_amount, issue_date, validity_date, is_accepted, project_id) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setDouble(1, quote.getEstimated_amount());
            pr.setDate(2, java.sql.Date.valueOf(quote.getIssue_date()));
            pr.setDate(3, java.sql.Date.valueOf(quote.getValidity_date()));
            pr.setBoolean(4, quote.getIs_accepted());
            pr.setInt(5, quote.getProject_id());
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean update(Quote quote) {
        String query = "UPDATE quotes SET estimated_amount = ?, issue_date = ?, validity_date = ?, is_accepted = ?, project_id = ? WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setDouble(1, quote.getEstimated_amount());
            pr.setDate(2, java.sql.Date.valueOf(quote.getIssue_date()));
            pr.setDate(3, java.sql.Date.valueOf(quote.getValidity_date()));
            pr.setBoolean(4, quote.getIs_accepted());
            pr.setInt(5, quote.getProject_id());
            pr.setInt(6, quote.getId());
            pr.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM quotes WHERE id = ?";

        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            int rowsAffected = pr.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Quote> findByProjectId(int projectId) {
        String query = "SELECT * FROM quotes WHERE project_id = ?;";
        List<Quote> quotes = new ArrayList<>();

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, projectId);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    Quote quote = new Quote(
                            rs.getDouble("estimated_amount"),
                            rs.getDate("issue_date").toLocalDate(),
                            rs.getDate("validity_date").toLocalDate(),
                            rs.getBoolean("is_accepted"),
                            rs.getInt("project_id")
                    );
                    quote.setId(rs.getInt("id"));
                    quotes.add(quote);
                }
            }
        } catch (SQLException e) {
            // Handle the exception properly, for example:
            System.err.println("Error while retrieving quotes: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return quotes;
    }




}
