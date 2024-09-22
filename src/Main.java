import utils.CheckInput;
import view.ClientView;
import view.ProjectView;
import view.QuoteView;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);
                ProjectView projectView = new ProjectView();
                ClientView clientView = new ClientView();
                QuoteView quoteView = new QuoteView();

                quoteView.checkDatesOfProjects();
                while (true) {
                        System.out.println("\n" +
                                "============================================================\n" +
                                "|          Bienvenue dans l'application de gestion des     |\n" +
                                "|              projets de rénovation de cuisines           |\n" +
                                "============================================================\n");

                        System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~ Menu Principal ~~~~~~~~~~~~~~~~~~~~~ ");
                        System.out.println("| 1. Créer un nouveau projet                               |");
                        System.out.println("| 2. Afficher les projets existants                        |");
                        System.out.println("| 3. Afficher Les clients                                  |");
                        System.out.println("| 4. Voir un Devis dun Projet                              |");
                        System.out.println("| 5. Quitter                                               |");
                        System.out.println("============================================================\n");

                        int choice = CheckInput.readInt("Votre choix : ");

                        switch (choice) {
                                case 1:
                                        System.out.println("\n================== Créer un nouveau projet ==================");
                                        projectView.saveProjectMenu();
                                        break;
                                case 2:
                                        projectView.displayProjects();
                                                int projectChoice = CheckInput.readInt("Choisez le project pour afficher les detail : ");
                                                String askForCalc = CheckInput.readString("Voulez-vous calculer le coût d'un projet ? (y/n) ");
                                                switch (askForCalc) {
                                                        case "y" :
                                                                projectView.getProjectWithMaterialsAndLabors(projectChoice , true);
                                                                System.out.println("Press Enter to continue...");
                                                                sc.nextLine();
                                                                break;
                                                        case "n" :
                                                                projectView.getProjectWithMaterialsAndLabors(projectChoice , false);
                                                                System.out.println("Press Enter to continue...");
                                                                sc.nextLine();
                                                                break;
                                                }

                                        break;
                                case 3 :
                                        clientView.getAllClients();
                                        System.out.println("Press Enter to continue...");
                                        sc.nextLine();
                                        break;
                                case 4 :
                                        System.out.println("Changer status dun devis : ");
                                        projectView.displayProjects();
                                        int projectForSearch = CheckInput.readInt("Enter le Id dun project ? ");
                                        quoteView.changeStatus(projectForSearch);
                                        System.out.println("Press Enter to continue...");
                                        sc.nextLine();
                                        break;

                                case 5:
                                        System.out.println("\n================== Quitter ==================");
                                        sc.close();
                                        System.exit(0);
                                        break;
                                default:
                                        System.out.println("\nChoix invalide, veuillez réessayer.");
                                        break;
                        }
                }
        }

}