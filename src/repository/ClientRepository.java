package repository;

import config.DatabaseConnection;
import entity.Client;
import repository.interfaces.Crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Crud<Client> {
    private final Connection connection;

    public ClientRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public int save(Optional<Client> clientOpt) {
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            String query = "INSERT INTO clients (name, address, phone, is_professional) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                pr.setString(1, client.getName());
                pr.setString(2, client.getAddress());
                pr.setString(3, client.getPhone());
                pr.setBoolean(4, client.getIs_professional());
                pr.executeUpdate();

                ResultSet rs = pr.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);  // Return the generated key (ID)
                }
            } catch (SQLException e) {

                throw new RuntimeException("Error inserting client", e);
            }
        } else {
            throw new IllegalArgumentException("Client object is not present");
        }
        return 0;
    }


    public Client getClient(String name) {
        String query = "SELECT * FROM clients WHERE name = ? ;";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setString(1, name);

            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    Client cl = new Client(
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getBoolean("is_professional")
                    );
                    cl.setId(rs.getInt("id"));

                    return cl;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }


    public Optional<Client> findById(int clinetId) {
        String query = "SELECT * FROM clients WHERE id = ? ;";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, clinetId);

            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    Client cl = new Client(
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getBoolean("is_professional")
                    );
                    cl.setId(rs.getInt("id"));

                    return Optional.of(cl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";

        try (PreparedStatement pr = connection.prepareStatement(query);
             ResultSet rs = pr.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("is_professional")
                );
                client.setId(rs.getInt("id"));
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching clients", e);
        }

        return clients;
    }


    public boolean delete(int id){
        String query = "DELETE FROM clients WHERE id = ? ;";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
