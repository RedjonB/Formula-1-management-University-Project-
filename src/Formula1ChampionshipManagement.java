import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Formula1ChampionshipManagement {
    BufferedWriter updatedListWriter;
    BufferedWriter saveTofile;
    private List<Formula1Driver> drivers = new ArrayList<>();
    private List<Race> races = new ArrayList<>();
    public List<Race> getRaces() {
        return races;
    }

    public void createNewDriver(JTextArea jte){
        //resetoj consolen
        jte.setText("");
        if(drivers == null){
            Formula1Driver newDriver = getDriverData();
            drivers.add(newDriver);
            saveDriverToFile(newDriver);
        }
        if(drivers.size()==10){
            MsgFrame msgFrame = new MsgFrame("Create new driver","Drivers list for this championship is completed!");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("Drivers list for this championship is completed! +\n");
        }else {
            Formula1Driver newDriver = getDriverData();

            if(drivers.stream().anyMatch(driver -> driver.getTeam().equals(newDriver.getTeam()))){
                MsgFrame msgFrame = new MsgFrame("Create new driver","Team already exists.");
                msgFrame.setVisible(true);
                msgFrame.setLocationRelativeTo(null);
                jte.append("Team already exists.\n");
            }else{
                drivers.add(newDriver);
                saveDriverToFile(newDriver);
            }
        }

    }
    private Formula1Driver getDriverData(){
        //getting driver name
        String driverName = JOptionPane.showInputDialog("Please enter driver name: ");
        //getting driver nationality
        String driverNationality = JOptionPane.showInputDialog("Please enter driver Nationality: ");
        //getting driver team
        String driverTeam = JOptionPane.showInputDialog("Please enter driver Team: ");
        return new Formula1Driver(driverName,driverNationality,driverTeam);
    }

    public void removeExistingDriver(JTextArea jte){
        jte.setText("");
        if(drivers.size() == 0){
            MsgFrame msgFrame = new MsgFrame("Driver size == 0","There are no drivers left");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("There are no drivers left \n");
        }else {
            Driver driverToBeRemoved = getDriverData();
            if (!drivers.contains(driverToBeRemoved)) {
                MsgFrame msgFrame = new MsgFrame("Data for the driver you want to remove...","Driver to be removed doesn't exist.");
                msgFrame.setVisible(true);
                msgFrame.setLocationRelativeTo(null);
                jte.append("Driver to be removed doesn't exist.\n");
            } else {
                drivers.remove(driverToBeRemoved);
                saveUpdatedListOfDriversToFile();
            }
        }
    }

    public void changeDriverForExistingTeam(JTextArea jte){
        jte.setText("");
        if(drivers.size() == 0){
            MsgFrame msgFrame = new MsgFrame("ChangeDriverForExistingTeam","There are no drivers left");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("There are no drivers left \n");
//            System.out.println("No team exists.");
        }else {
//            System.out.println("Enter the team which you want to change the driver:");
//            Scanner readTeamFromUser = new Scanner(System.in);
            String team = JOptionPane.showInputDialog("Enter the team which you want to change the driver: ");
            if (drivers.stream().noneMatch(driver -> driver.getTeam().equals(team))) {
                MsgFrame msgFrame = new MsgFrame("ChangeDriverForExistingTeam","Team doesn't exist.");
                msgFrame.setVisible(true);
                msgFrame.setLocationRelativeTo(null);
                jte.append("Team doesn't exist.\n");
            } else {
//                System.out.println("Enter the data for the new driver of the team " + team);
                    Formula1Driver driverForChanging;
//                Scanner driverScanner = new Scanner(System.in);
//                System.out.print("Enter new driver name:");
                String newDriverName = JOptionPane.showInputDialog("Enter new driver name: " );
//                System.out.print("Enter new driver nationality:");
                String newDriverNationality = JOptionPane.showInputDialog("Enter new driver nationality: ");
                driverForChanging = new Formula1Driver(newDriverName, newDriverNationality, team);

                for (Formula1Driver driverToBeChanged : drivers) {
                    if (driverToBeChanged.getTeam().equals(team)) {
                        driverToBeChanged.setName(driverForChanging.getName());
                        driverToBeChanged.setNationality(driverForChanging.getNationality());
//                        System.out.println("Driver of team " + team + " changed succesfully.");
                        MsgFrame msgFrame = new MsgFrame("ChangeDriverForExistingTeam","Driver of teamm changed succesfully " );
                        msgFrame.setVisible(true);
                        msgFrame.setLocationRelativeTo(null);
                        jte.append("Driver of teamm changed succesfully !");
                        break;
                    }
                }
                saveUpdatedListOfDriversToFile();
            }
        }
    }

    public void getStatisticsForTheSpecifiedDriver(JTextArea jte){
        jte.setText("");
        if(drivers.size() == 0){
            MsgFrame msgFrame = new MsgFrame("GetStatisticsForTheSpecifiedDriver","No driver exists.");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("No driver exists.\n");
        }else {
//            System.out.println("Enter driver name:");
//            Scanner readDriverNameFromUser = new Scanner(System.in);
            String nameToBeSearched = JOptionPane.showInputDialog("Enter driver name:");
            if (drivers.stream().noneMatch(driver -> driver.getName().equals(nameToBeSearched))) {
//                System.out.println("The driver you searched for doesn't exist.");
                MsgFrame msgFrame = new MsgFrame("GetStatisticsForTheSpecifiedDriver","The driver you searched for doesn't exist.");
                msgFrame.setVisible(true);
                msgFrame.setLocationRelativeTo(null);
                jte.append("The driver you searched for doesn't exist.\n");
            } else {
//                System.out.println(drivers.stream().filter(driver -> driver.getName().equals(nameToBeSearched)).findFirst());
                String x = String.valueOf(drivers.stream().filter(driver -> driver.getName().equals(nameToBeSearched)).findFirst());
                MsgFrame msgFrame = new MsgFrame("GetStatisticsForTheSpecifiedDriver",x);
                msgFrame.setVisible(true);
                msgFrame.setLocationRelativeTo(null);
                jte.append(x + "\n");
            }
        }
    }

    public void printOrderedListOfDrivers(JTextArea jte){
        jte.setText("");
        if(drivers.size() == 0){
            MsgFrame msgFrame = new MsgFrame("PrintOrderedListOfDrivers","No drivers in the table");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("No drivers in the table");
//            System.out.println("No drivers in the table");
        }else {
            List<Formula1Driver> sortedListOfDrivers = drivers.stream().sorted(Comparator
                    .comparing(Formula1Driver::getPointsGathered)
                    .thenComparing(Formula1Driver::getTimesInTheFirstPlace)
                    .thenComparing(Formula1Driver::getTimesInTheSecondPlace)
                    .thenComparing(Formula1Driver::getTimesInTheThirdPlace)).toList();

            List<Formula1Driver> reversedList = new ArrayList<>();
            for(Formula1Driver driver: sortedListOfDrivers){
                reversedList.add(driver);
            }
            Collections.reverse(reversedList);
//            System.out.println(reversedList);

           Formula1Driver[] array = reversedList.toArray(new Formula1Driver[reversedList.size()]);
           //i shtoj ne console
            for (Formula1Driver driver: array) {
                jte.append(driver.toString() + "\n");
            }
            JList list = new JList(array);

//            JScrollPane scrollPane = new JScrollPane(list);
//            list.setSize(new Dimension(800, 800));
//            JOptionPane.showMessageDialog(null, scrollPane);
//            JFrame frame = new JFrame("Ordered list of driver!");
//            frame.add(scrollPane);
//            frame.setSize(800,800);
//            frame.setVisible(true);
//            frame.setLocationRelativeTo(null);
//            scrollPane.setPreferredSize(new Dimension(800, 800));
        }
    }
    public void createRace(JTextArea jte){
        if(drivers.size() != 10){
            MsgFrame msgFrame = new MsgFrame("CreateRace","No enough drivers to start the race");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("No enough drivers to start the race \n");
//            System.out.println("No enough drivers to start the race");
        }else {

//            System.out.println("Race started...");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.now();
            List<Formula1Driver> newOrderOfRace = drivers;
            Collections.shuffle(newOrderOfRace);
            Formula1Driver[] helpArray = new Formula1Driver[drivers.size()];
            Race raceCreated = new Race(newOrderOfRace, dtf.format(date));
            for (int i = 0; i < drivers.size(); i++) {
                helpArray[i] = newOrderOfRace.get(i);
            }
            for (Formula1Driver driverToBeChangedStats : drivers) {
                if (driverToBeChangedStats.equals(helpArray[0])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 25);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                    driverToBeChangedStats.setTimesInTheFirstPlace(driverToBeChangedStats.getTimesInTheFirstPlace() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[1])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 18);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                    driverToBeChangedStats.setTimesInTheSecondPlace(driverToBeChangedStats.getTimesInTheSecondPlace() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[2])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 15);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                    driverToBeChangedStats.setTimesInTheThirdPlace(driverToBeChangedStats.getTimesInTheThirdPlace() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[3])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 12);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[4])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 10);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[5])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 8);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[6])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 6);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[7])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 4);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[8])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 2);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                } else if (driverToBeChangedStats.equals(helpArray[9])) {
                    driverToBeChangedStats.setPointsGathered(driverToBeChangedStats.getPointsGathered() + 1);
                    driverToBeChangedStats.setNumberOfRacesTakenPart(driverToBeChangedStats.getNumberOfRacesTakenPart() + 1);
                }
            }
            saveUpdatedListOfDriversToFile();
            printRaceStats(newOrderOfRace, dtf.format(date),jte);
            MsgFrame msgFrame = new MsgFrame("CreateRace","Race started...");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
            jte.append("Race started...\n");
            //Mbyllim frame pas 2 sekondas ose 2000 milisekonda
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    msgFrame.dispose(); // Mbyllet frame
                }
            }, 1000);
            races.add(raceCreated);
            saveRaceToFile(raceCreated);
        }
    }

    private void printRaceStats(List<Formula1Driver> finishedRaceStats, String date,JTextArea jte){
        //Fshijme JTextArea
        jte.setText("");

        String string0 = "Date of race:" + date;
        String string1 = "THE 1-ST PLACE WINNER!!!";
        String string2 = "Name:"+finishedRaceStats.get(0).getName()+ " | Team:" + finishedRaceStats.get(0).getTeam() + " | Nationality:" + finishedRaceStats.get(0).getNationality();
        String string3 = "THE 2-ND PLACE WINNER!!!";
        String string4 = "Name:"+finishedRaceStats.get(1).getName()+ " | Team:" + finishedRaceStats.get(1).getTeam() + " | Nationality:" + finishedRaceStats.get(1).getNationality();
        String string5 = "THE 3-RD PLACE WINNER!!!";
        String string6 = "Name:"+finishedRaceStats.get(2).getName()+ " | Team:" + finishedRaceStats.get(2).getTeam() + " | Nationality:" + finishedRaceStats.get(2).getNationality();
//        System.out.println("Date of race:" + date);
//        System.out.println("THE 1-ST PLACE WINNER!!!");
//        System.out.println("Name:"+finishedRaceStats.get(0).getName()+ " | Team:" + finishedRaceStats.get(0).getTeam() + " | Nationality:" + finishedRaceStats.get(0).getNationality());
//        System.out.println("THE 2-ND PLACE WINNER!!!");
//        System.out.println("Name:"+finishedRaceStats.get(1).getName()+ " | Team:" + finishedRaceStats.get(1).getTeam() + " | Nationality:" + finishedRaceStats.get(1).getNationality());
//        System.out.println("THE 3-RD PLACE WINNER!!!");
//        System.out.println("Name:"+finishedRaceStats.get(2).getName()+ " | Team:" + finishedRaceStats.get(2).getTeam() + " | Nationality:" + finishedRaceStats.get(2).getNationality());

        ArrayList<String> str = new ArrayList<String>();
        str.add(string0);
        str.add(string1);
        str.add(string2);
        str.add(string3);
        str.add(string4);
        str.add(string5);
        str.add(string6);

        for(int i = 4;i <= drivers.size();i++){
//            System.out.println("THE " + i +"-TH PLACE WINNER!!!");
            str.add("THE " + i +"-TH PLACE WINNER!!!");
            str.add("Name:"+finishedRaceStats.get(i-1).getName()+
                    " | Team:" + finishedRaceStats.get(i-1).getTeam() +
                    " | Nationality:" + finishedRaceStats.get(i-1).getNationality());
//            System.out.println("Name:"+finishedRaceStats.get(i-1).getName()+
//                    " | Team:" + finishedRaceStats.get(i-1).getTeam() +
//                    " | Nationality:" + finishedRaceStats.get(i-1).getNationality());
        }
        String[] castArray = str.toArray(new String[str.size()]);

        JList list = new JList(castArray);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setSize(new Dimension(800 , 800));
        JFrame frame = new JFrame("Race states");
        frame.add(scrollPane);
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        scrollPane.setPreferredSize(new Dimension(800, 800));
        list.setFont(list.getFont().deriveFont(20f));
        for (int i=0;i<castArray.length;i++){
            jte.append(castArray[i] + "\n");
        }
    }

    //i bej cast arraylistes ne array

//    String[] castArray = str.toArray(new Formula1Driver[reversedList.size()]);


    public void saveUpdatedListOfDriversToFile(){
        try {
            updatedListWriter = new BufferedWriter(new FileWriter("Files/DriversFile.txt"));
            for(Formula1Driver driver: drivers){
                updatedListWriter.write(driver.toFile());
                updatedListWriter.newLine();
                updatedListWriter.flush();
            }
            updatedListWriter.close();
        } catch (IOException e) {
            MsgFrame msgFrame = new MsgFrame("saveUpdatedListOfDriversToFile","Problem with DriversFile!");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
        }
    }

    public void saveDriverToFile(Formula1Driver driverToBeSaved){
        try {
            saveTofile = new BufferedWriter(new FileWriter("Files/DriversFile.txt",true));
            saveTofile.write(driverToBeSaved.toFile());
            saveTofile.newLine();
            saveTofile.flush();
            saveTofile.close();

        }catch (Exception e){
            MsgFrame msgFrame = new MsgFrame("saveDriverToFile","File not found");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
        }
    }

    public void readDriversFromFile(){
        try {
            BufferedReader readDrivers = new BufferedReader(new FileReader("Files/DriversFile.txt"));
            String lineReadFromFile;
            while((lineReadFromFile = readDrivers.readLine()) != null){
                String[] driverData = lineReadFromFile.split("~");
                Formula1Driver tempDriver = new Formula1Driver(driverData[0], driverData[1], driverData[2], Integer.parseInt(driverData[3]),Integer.parseInt(driverData[4]), Integer.parseInt(driverData[5]),Integer.parseInt(driverData[6]),Integer.parseInt(driverData[7]));
                this.drivers.add(tempDriver);
            }
        } catch (IOException e) {
//            System.out.println("File not found!");
            MsgFrame msgFrame = new MsgFrame("readDriversFromFile","File not found");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
        }

    }

    public void saveRaceToFile(Race race){
        try {
            BufferedWriter raceWriter = new BufferedWriter(new FileWriter("Files/RacesFile.txt", true));
            raceWriter.write(race.toFile());
            raceWriter.newLine();
            raceWriter.flush();
        } catch (IOException e) {
//            System.out.println("File not found!");
            MsgFrame msgFrame = new MsgFrame("saveRaceToFile","File not found");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
        }
    }

    public void uploadRacesFromFile(){
        try {
            BufferedReader readRaceFromFile = new BufferedReader(new FileReader("Files/RacesFile.txt"));
            String lineReadFromRacesFile;
            while((lineReadFromRacesFile = readRaceFromFile.readLine()) != null){
                String[] helpArray = lineReadFromRacesFile.split("~");
                String date = helpArray[0];
                List<Formula1Driver> driversList = new ArrayList<>();
                for(int i=1;i<=drivers.size()*8;i+=8) {
                    driversList.add(new Formula1Driver(
                            helpArray[i],
                            helpArray[i+1],
                            helpArray[i+2],
                            Integer.parseInt(helpArray[i+3]),
                            Integer.parseInt(helpArray[i+4]),
                            Integer.parseInt(helpArray[i+5]),
                            Integer.parseInt(helpArray[i+6]),
                            Integer.parseInt(helpArray[i+7])));
                }
                races.add(new Race(driversList, date));
            }
        } catch (Exception e) {
            MsgFrame msgFrame = new MsgFrame("uploadRacesFromFile","File not found");
            msgFrame.setVisible(true);
            msgFrame.setLocationRelativeTo(null);
        }
    }
    public void mainMenu(){
        JFrame frame = new JFrame("Menu");
        JPanel panel = new JPanel();
        JPanel blankPanel = new JPanel();

//        frame.setSize(800, 400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridLayout(1,2));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        Formula1ChampionshipManagement formula1Championship = new Formula1ChampionshipManagement();
        try {
            formula1Championship.uploadRacesFromFile();
            formula1Championship.readDriversFromFile();
        }catch(Exception e){
            System.out.print("");
        }

        JButton button = new JButton( " 1 - Create a new driver");

        JButton button2 = new JButton( " 2 - Remove an existing driver");

        JButton button3 = new JButton( " 3 - Change driver for an existing team");

        JButton button5 = new JButton( " 5 - Show table of points");

        JButton button6 = new JButton( " 6 - Create a race");

        JButton button4 = new JButton( " 4 - Show statisics for a driver");

        JButton button7 = new JButton( " 7 - PrintOrderedListOfDrivers");

        panel.setLayout(new GridLayout(3,2));
        panel.setVisible(true);
        panel.setSize(800,400);

        blankPanel.setVisible(true);
        blankPanel.setSize(800,800);
         blankPanel.setLayout(new GridLayout());

        //JTextArea

        JTextArea jTextArea = new JTextArea();
        //e bej te paeditueshme
        jTextArea.setEditable(false);
        //e bej scrollable
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        blankPanel.add(jScrollPane);


        //add buttons to the label
        panel.add(button);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
        panel.add(button7);

        frame.add(blankPanel);
        frame.add(panel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.createNewDriver(jTextArea);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.removeExistingDriver(jTextArea);
            }
        });

        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.changeDriverForExistingTeam(jTextArea);
            }
        });

        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.getStatisticsForTheSpecifiedDriver(jTextArea);
            }
        });

        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.printOrderedListOfDrivers(jTextArea);
            }
        });

        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.createRace(jTextArea);
            }
        });

        button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formula1Championship.printOrderedListOfDrivers(jTextArea);
            }
        });


    }
}
