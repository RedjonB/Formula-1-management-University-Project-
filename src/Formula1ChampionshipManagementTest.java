import java.util.Scanner;

public class Formula1ChampionshipManagementTest {
    public static void main(String[] args) {
            Formula1ChampionshipManagement formula1Championship = new Formula1ChampionshipManagement();
            try {
                formula1Championship.uploadRacesFromFile();
                formula1Championship.readDriversFromFile();
            }catch(Exception e){
                System.out.print("");
            }

            formula1Championship.mainMenu();

        while (true){
//            formula1Championship.mainMenu();
//            switch (sc.nextInt()){
//                case 1:
//                    formula1Championship.createNewDriver();
//                    break;
//                case 2:
//                    formula1Championship.removeExistingDriver();
//                    break;
//                case 3:
//                    formula1Championship.changeDriverForExistingTeam();
//                    break;
//                case 4:
//                    formula1Championship.getStatisticsForTheSpecifiedDriver();
//                    break;
//                case 5:
//                    formula1Championship.printOrderedListOfDrivers();
//                    break;
//                case 6:
//                    formula1Championship.createRace();
//                    break;
//                case 7:
//                    System.out.println("Program exited succesfully.");
//                    return;
//                default:
//                    System.out.println("Wrong choice, choose again");
//            }
         }
    }
}
