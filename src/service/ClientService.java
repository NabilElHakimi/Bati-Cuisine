package service;

import entity.Client;
import repository.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    public int insertClient(Client client) {
        return new ClientRepository().save(Optional.ofNullable(client));
    }

    public Client getClient(String name) {
        return new ClientRepository().getClient(name);
    }

    public Optional<Client> findById(int clientId) {
        return new ClientRepository().findById(clientId);
    }

    public List<Client> getAllClients() {
        return new ClientRepository().findAll();
    }

    public boolean deleteClient(int clientId) {
        return new ClientRepository().delete(clientId);
    }
}
