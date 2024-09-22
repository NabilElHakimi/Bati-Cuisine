package view;
import entity.Labor;
import entity.Material;
import entity.Project;
import entity.Quote;
import enums.ProjectStatus;
import service.LaborService;
import service.MaterialService;
import service.ProjectService;
import service.QuoteService;
import utils.CheckDate;
import utils.CheckInput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class QuoteView {
    QuoteService quoteService = new QuoteService();
    ProjectService projectService = new ProjectService();

    public void calcQuote(double total_margin ,double amount  ,  int project_id){

        LocalDate issueDate = CheckInput.readDate("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
        LocalDate validityDate = CheckInput.readDate("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");

        Quote quote = new Quote();
        quote.setIssue_date(issueDate);
        quote.setValidity_date(validityDate);
        quote.setProject_id(project_id);
        quote.setEstimated_amount(amount);

        Project pr = new Project();
        pr.setId(project_id);
        pr.setProfit_margin(total_margin);
        pr.setTotal_cost(amount);
        projectService.updateProject(pr);

        boolean save = quoteService.save(quote);
        if(save){
            System.out.println("Quote saved");
        }
        else{
            System.out.println("Quote not saved");
        }

    }

    public void changeStatus(int projectId) {
        System.out.println("=================================== Devis ================================================================================");

        System.out.printf("| %-6s | %-30s | %-20s | %-20s | %-20s |\n",
                "ID", "Coût Total (€)", "Date d'Émission", "Date de Validité", "Statut");
        System.out.println("===========================================================================================================================");

        List<Quote> quotes = quoteService.findAll(projectId);

        quotes.forEach(quote -> {
            String status = quote.getIs_accepted() ? "Accepté" : "Pas Accepté";

            System.out.printf("| %-6d | %-30.2f | %-20s | %-20s | %-20s |\n",
                    quote.getId(),
                    quote.getEstimated_amount(),
                    quote.getIssue_date(),
                    quote.getValidity_date(),
                    status);
        });

        if (quotes.stream().anyMatch(quote -> !quote.getIs_accepted())) {
            String accept = CheckInput.readString("Voulez-vous accepter un devis ? (y/n) ");

            if (accept.equalsIgnoreCase("y")) {
                int id = CheckInput.readInt("Choisissez un devis par ID : ");
                Quote quoteToAccept = quotes.stream()
                        .filter(quote -> quote.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (quoteToAccept != null && !quoteToAccept.getIs_accepted()) {
                    quoteToAccept.setIs_accepted(true);

                    if (quoteService.updateStatus(quoteToAccept)) {
                        System.out.println("Devis accepté avec succès.");
                    } else {
                        System.out.println("Erreur lors de l'acceptation du devis.");
                    }
                } else {
                    System.out.println("Devis non trouvé ou déjà accepté.");
                }
            }
        } else {
            System.out.println("Tous les devis sont déjà acceptés.");
        }

        System.out.println("===========================================================================================================================");
    }

    void calculateAndSaveQuote(List<Material> materials, List<Labor> labors, Project project, String border) {
        double  margin = 0;

        if(project.getProfit_margin() > 0){
            String qst = CheckInput.readString("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) :");
            if(qst.equalsIgnoreCase("y")){
                margin = CheckInput.readDouble("Entrez le pourcentage de marge bénéficiaire (%) :");
            }
            else {
                margin = project.getProfit_margin();
            }
        }
        else {
            System.out.println("marge bénéficiaire au projet est " + project.getProfit_margin());
            String qst = CheckInput.readString("Souhaitez-vous Changer la marge bénéficiaire au projet ? (y/n) :");
            if(qst.equalsIgnoreCase("y")){
                margin = CheckInput.readDouble("Entrez le pourcentage de marge bénéficiaire (%) :");
            }
        }

        double materialsTotal = new MaterialService().calcCost(materials);
        double laborsTotal = new LaborService().calcCost(labors);
        double total = materialsTotal + laborsTotal;
        if(margin > 0 ){
            double profitMargin = total + margin/100;
        }
        else{
            double profitMargin = 0;
        }

        double profitMargin = total * materialsTotal / 100;
        double totalComplete = total + profitMargin;

        Project pr = new Project();
        pr.setId(project.getId());
        pr.setProfit_margin(profitMargin);
        pr.setTotal_cost(totalComplete);
        projectService.updateProject(pr);

        System.out.println("========================================== Résultats du Calcul ==========================================");
        System.out.println(border);
        System.out.printf("| %-35s | %-35s | %-35s |\n", "TOTAL MATÉRIAUX = " + String.format("%.2f", materialsTotal) + " €",
                "TOTAL TRAVAUX = " + String.format("%.2f", laborsTotal) + " €", "TOTAL = " + String.format("%.2f", total) + " €");
        System.out.printf("| %-35s | %-35s |\n", "Marge Bénéficiaire (€) = " + String.format("%.2f", profitMargin) + " €",
                "Total Complet = " + String.format("%.2f", totalComplete) + " €");
        System.out.println(border);

        if (CheckInput.readString("Voulez vous enregistrer ce devis ? (y/n) ").equalsIgnoreCase("y")) {
            new QuoteView().calcQuote(profitMargin ,totalComplete, project.getId());
            System.out.println("Devis enregistré avec succès !");
        }
    }

    public void checkDatesOfProjects() {
        projectService.getAllProjects().forEach(project -> {
            quoteService.findAll(project.getId()).forEach(quote -> {
                if (!CheckDate.checkDate(quote.getValidity_date(), LocalDate.now())) {
                    if (quote.getIs_accepted() && project.getStatus() != ProjectStatus.COMPLETED) {
                        project.setStatus(ProjectStatus.COMPLETED);
                        projectService.updateProjectStatus(project);
                    }
                    else {
                        project.setStatus(ProjectStatus.CANCELLED);
                        projectService.updateProjectStatus(project);
                    }
                }
            });
        });
    }

}
