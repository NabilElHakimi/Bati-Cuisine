package view;

import entity.Client;
import service.ClientService;
import utils.CheckInput;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientView {


    ClientService clientService = new ClientService();
    Scanner sc = new Scanner(System.in);
    public int saveClient() {
        int idClient = 0;
        boolean continueMenu = true;

        while (continueMenu) {
            System.out.println("1. Chercher un client existant\n" +
                    "2. Ajouter un nouveau client\n" +
                    "3. Afficher tous les clients\n" +
                    "4. Quitter : ");

            int choice = CheckInput.readInt("Votre choix : ");

            switch (choice) {
                case 1:
                    idClient = searchClient();
                    if (idClient > 0) {
                        return idClient;
                    }
                    break;
                case 2:
                    idClient = addNewClient();
                    if (idClient > 0) {
                        return idClient;
                    }
                    break;
                case 3:
                    getAllClients();
                    break;
                case 4:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        }

        return idClient;
    }

    private int searchClient() {
        System.out.print("Entrez le nom du client : ");
        String nom = sc.nextLine();  // Use sc.nextLine() to read the entire name string
        Client client = clientService.getClient(nom);

        if (client != null) {
            displayClient(client);
            System.out.println("Souhaitez-vous continuer avec ce client ? (y/n) :");
            String response = sc.nextLine();  // Use sc.nextLine() for consistency
            if (response.equalsIgnoreCase("y")) {
                return client.getId();
            }
        } else {
            System.out.println("Nom du client n'existe pas");
        }
        return 0;
    }

    private int addNewClient() {
        System.out.print("Entrez le nom du client : ");
        String nom = sc.nextLine();  // Use sc.nextLine() to get full input

        System.out.print("Entrez l'adresse du client : ");
        String address = sc.nextLine();  // Use sc.nextLine() for address

        System.out.print("Entrez le téléphone du client : ");
        String telephone = sc.nextLine();  // Use sc.nextLine() for phone

        System.out.println("Le client est un professionnel\n" +
                "1 - Oui\n" +
                "2 - Non");

        int choice = sc.nextInt();
        sc.nextLine();  // Consume the leftover newline
        boolean isProfessional = (choice == 1);

        // Insert the new client and get the ID
        int idClient = clientService.insertClient(new Client(nom, address, telephone, isProfessional));

        Client newClient = new Client(nom, address, telephone, isProfessional);
        displayClient(newClient);

        System.out.println("Souhaitez-vous continuer avec ce client ? (y/n) :");
        String response = sc.nextLine();  // Use sc.nextLine() to capture full input
        if (response.equalsIgnoreCase("y")) {
            return idClient;  // Return the client ID if confirmed
        }

        return 0;
    }

    private void displayClient(Client client) {
        System.out.println("=========================================================================================");
        System.out.println("| Nom du client : " + client.getName() + " | " +
                "Adresse : " + client.getAddress() + " | " +
                "Téléphone : " + client.getPhone());
        System.out.println("=========================================================================================");
    }

    public void getAllClients() {
        List<Client> clients = clientService.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
            return;
        }

        // Table Header
        System.out.println("==========================================================================================");
        System.out.printf("| %-6s | %-20s | %-30s | %-15s |\n", "ID", "Nom", "Adresse", "Téléphone");
        System.out.println("==========================================================================================");

        // Table Rows
        clients.forEach(client -> {
            System.out.printf("| %-6d | %-20s | %-30s | %-15s |\n",
                    client.getId(),
                    client.getName(),
                    client.getAddress(),
                    client.getPhone()
            );
        });
        System.out.println("==========================================================================================");
    }

    void displayClientInfo(Optional<Client> clientOpt, String border) {
        Client client = clientOpt.get();
        System.out.println("============================================== Informations sur le Client ==============================================");
        System.out.println(border);
        System.out.printf("| %-30s | %-40s | %-20s |\n", "Nom du Client", client.getName(), "Téléphone : " + client.getPhone());
        System.out.printf("| %-30s | %-40s |\n", "Adresse du Client", client.getAddress());
        System.out.println(border);
    }


}
