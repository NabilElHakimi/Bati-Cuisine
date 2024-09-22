package view;
import entity.Client;
import entity.Labor;
import entity.Material;
import entity.Project;
import enums.ProjectStatus;
import service.ClientService;
import service.LaborService;
import service.MaterialService;
import service.ProjectService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class ProjectView {
    Scanner sc = new Scanner(System.in);

    ProjectService projectService = new ProjectService();
    ClientView clientView = new ClientView();
    MaterialView materialView = new MaterialView();
    LaborView laborView = new LaborView();
    QuoteView quoteView = new QuoteView();

    public void getProjectWithMaterialsAndLabors(int projectId, boolean calc) {
        String border = "=====================================================================================================================================================================";

        // Fetch the project and client by IDs
        Optional<Project> project = new ProjectService().findById(projectId);
        if (project.isEmpty()) {
            System.out.println("Projet non trouvé.");
            return;
        }
        Optional<Client> client = new ClientService().findById(project.get().getClientId());

        // Display Client and Project Info
        clientView.displayClientInfo(client, border);
        displayProjectInfo(project.orElse(null), border);

        // Display Materials
        List<Material> materials = new MaterialService().findAll(projectId);
        materialView.displayMaterials(materials, project.get().getProject_name(), border);

        // Display Labors
        List<Labor> labors = new LaborService().findAll(projectId);
        laborView.displayLabors(labors, project.get().getProject_name(), border);

        if (calc) {
            quoteView.calculateAndSaveQuote(materials, labors, project.orElse(null), border);
        }

        System.out.println(border);
    }

    private void displayProjectInfo(Project project, String border) {
        System.out.println("============================================== Détails du Projet ==============================================");
        System.out.println(border);
        System.out.printf("| %-8s | %-40s | %-12s | %-18s | %-18s | %-20s |\n", "ID", "Nom du Projet", "Capacité", "Coût Total (€)", "Marge Bénéficiaire (%)", "Statut");
        System.out.println(border);
        System.out.printf("| %-8d | %-40s | %-12.2f | %-18.2f | %-18.2f | %-20s |\n",
                project.getId(), project.getProject_name(), project.getCapacity(),
                project.getTotal_cost(), project.getProfit_margin(), project.getStatus());
        System.out.println(border);
    }

    public void displayProjects() {
        String projectBorder = "=============================================================================================================================================";

        // Print header and project details in a structured table format
        System.out.println(" ======================= Tous les Projets ======================================================================================================= ");
        System.out.println(projectBorder);
        System.out.printf("| %-8s | %-30s | %-10s | %-12s | %-12s | %-12s |\n", "ID", "Nom du Projet", "Capacité", "Coût Total", "Marge Bénéficiaire", "Statut");
        System.out.println(projectBorder);

        new ProjectService().getAllProjects().forEach(project -> {
            System.out.printf("| %-8d | %-30s | %-10.2f | %-12.2f | %-18.2f | %-12s |\n",
                    project.getId(),
                    project.getProject_name(),
                    project.getCapacity(),
                    project.getTotal_cost(),
                    project.getProfit_margin(),
                    project.getStatus()
            );
        });

        System.out.println(projectBorder);
    }

    public void saveProjectMenu() {
        int idClient = clientView.saveClient();
        if(idClient == 0) {
            return;
        }
        Project project = saveProject(idClient);
        project = projectService.insertProject(project);

        List<Material> materials = materialView.saveMaterial(project);
        List<Labor> labors = laborView.saveLabor(project);
        materials.forEach(material ->  new MaterialService().save(material));
        labors.forEach(labor -> new LaborService().save(labor));
    }

    public Project saveProject(int clientID) {
        System.out.println("========================================= Création d'un Nouveau Projet =========================================");

        System.out.print("Entrez le nom du projet :");
        String name = sc.next();
        System.out.print("Entrez la surface de la cuisine (en m²) : ");
        double capacity = sc.nextDouble();

        Project project = new Project();
        project.setProject_name(name);
        project.setCapacity(capacity);
        project.setClientId(clientID);
        project.setStatus(ProjectStatus.INPROGRESS);

        return project;
    }

}
