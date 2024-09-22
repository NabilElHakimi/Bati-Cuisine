package view;

import entity.Labor;
import entity.Material;
import entity.Project;
import service.MaterialService;
import utils.CheckInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaterialView {

    Scanner sc = new Scanner(System.in);

    public List<Material> saveMaterial(Project project){
        System.out.println("=================================================== Ajouté des matériaux ===================================================");
        List<Material> materials = new ArrayList<>();

        boolean adding = true;
        while (adding){
            System.out.print("Entrez le nom du matériau : ");
            String materialName = sc.next();

            System.out.print("Entrez la quantité de ce matériau (en m²) : ");
            double quantite = sc.nextDouble();

            System.out.print("Entrez le coût unitaire de ce matériau (€/m²) : ");
            double coutUnitaire = sc.nextDouble();

            System.out.print("Entrez le coût de transport de ce matériau (€) : ");
            double coutTransport = sc.nextDouble();

            System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
            double coutQuality = sc.nextDouble();

            String type = "Matériel" ;

            String askForVarRAt = CheckInput.readString("Souhaitez-vous appliquer une TVA au projet ? (y/n) :") ;
            double varRat = 0.0 ;
            if(askForVarRAt.equals("y")){
                 varRat = CheckInput.readInt("Entrez le pourcentage de TVA (%) :");
            }

            Material material = new Material(materialName ,type , varRat , project.getId() , coutUnitaire , quantite , coutTransport , coutQuality);
            materials.add(material);

            System.out.println("Voulez-vous ajouter un autre matériau ? (y/n) :");
            String check = sc.next();
            if (check.equalsIgnoreCase("n")) {
                adding = false;
            }
        }

        return materials;
    }

    void displayMaterials(List<Material> materials, String projectName, String border) {
        System.out.println("Matériaux pour le Projet : " + projectName);
        System.out.println(border);
        if (materials.isEmpty()) {
            System.out.println("| Aucun matériau trouvé pour ce projet. |");
        } else {
            materials.forEach(material -> System.out.printf(
                    "| %-8d | %-30s | %-30s | %-12.2f | %-16.2f | %-10.2f | %-18.2f | %-16.2f |\n",
                    material.getId(), material.getName(), material.getComponent_type(),
                    material.getVat_rate(), material.getUnit_cost(), material.getQuantity(),
                    material.getTransportCost(), material.getQualityCoefficient()));
        }
        System.out.println(border);
    }

}
