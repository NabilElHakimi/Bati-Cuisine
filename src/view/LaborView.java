package view;

import entity.Labor;
import entity.Project;
import utils.CheckInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaborView {

    Scanner sc = new Scanner(System.in);
    public List<Labor> saveLabor(Project project) {
        System.out.println("=================================================== Ajout de la main-d'œuvre ===================================================");
        List<Labor> labors = new ArrayList<>();
        boolean adding = true;
        while (adding) {
            System.out.print("Entrez le type de main-d'œuvre (e.g., Ouvrier de base, Spécialiste) : ");
            String laborType = sc.next();

            System.out.print("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            double hourlyRate = sc.nextDouble();

            System.out.print("Entrez le nombre d'heures travaillées : ");
            double hoursWorked = sc.nextDouble();

            System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
            double productivityFactor = sc.nextDouble();

            String type = " Main-d'œuvre" ;

            String askForVarRAt = CheckInput.readString("Souhaitez-vous appliquer une TVA au projet ? (y/n) :") ;
            double varRat = 0.0 ;
            if(askForVarRAt.equals("y")){
                varRat = CheckInput.readInt("Entrez le pourcentage de TVA (%) :");
            }

            Labor labor = new Labor(laborType , varRat , project.getId() ,type , hoursWorked , hourlyRate , productivityFactor );
            labors.add(labor);

            System.out.println("Voulez-vous ajouter une autre main-d'œuvre ? (y/n) : ");
            String check = sc.next();
            if (check.equalsIgnoreCase("n")) {
                adding = false;
            }
        }

        return labors;
    }

    void displayLabors(List<Labor> labors, String projectName, String border) {
        System.out.println("Travail pour le Projet : " + projectName);
        System.out.println(border);
        if (labors.isEmpty()) {
            System.out.println("| Aucun travail trouvé pour ce projet. |");
        } else {
            labors.forEach(labor -> System.out.printf(
                    "| %-8d | %-30s | %-30s | %-15.2f | %-12.2f | %-20.2f |\n",
                    labor.getId(), labor.getName(), labor.getComponent_type(),
                    labor.getHourly_rate(), labor.getWork_hours(), labor.getWorker_productivity()));
        }
        System.out.println(border);
    }


}

