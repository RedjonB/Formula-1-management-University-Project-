import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Race {
    private List<Formula1Driver> driversList;
    private String dateOfRace;

    public String getDateOfRace() {
        return dateOfRace;
    }

    public void setDateOfRace(String dateOfRace) {
        this.dateOfRace = dateOfRace;
    }

    public Race(List<Formula1Driver> driversList, String date){
        this.driversList = driversList;
        this.dateOfRace = date;
    }

    public String toFile(){
        String infoRaceFormatToFile = "";
        for(Formula1Driver driver: driversList){
            infoRaceFormatToFile = infoRaceFormatToFile.concat(driver.toFile()).concat("~");
        }
//        infoRaceFormatToFile = infoRaceFormatToFile.concat("EOR\n");

        return dateOfRace+"~"+infoRaceFormatToFile;
    }

    @Override
    public String toString() {
        return  "\n"+ dateOfRace + driversList;
    }
}
