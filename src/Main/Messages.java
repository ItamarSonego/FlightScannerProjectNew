package Main;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.stream.events.StartDocument;

import Sites.FlightScanners;

public class Messages {
    static Scanner input = new Scanner(System.in);

    public static ArrayList<FlightScanners> chooseSites() {
        System.out.println("Welcome to FlightScanner! please choose flight sites to scan in.");
        ArrayList<FlightScanners> sitesToSearch = new ArrayList<>();
        System.out.print("For searching in " + FlightScanners.GOOGLEFLIGHTS + " enter 1, enter 0 if not: ");
        if (UsefulFunctions.chooseSitesNumber() == 1) sitesToSearch.add(FlightScanners.GOOGLEFLIGHTS);
        System.out.print("For searching in " + FlightScanners.ELAL + " enter 1, enter 0 if not: ");
        if (UsefulFunctions.chooseSitesNumber() == 1) sitesToSearch.add(FlightScanners.ELAL);
        System.out.print("For searching in " + FlightScanners.KIWI + " enter 1, enter 0 if not: ");
        if (UsefulFunctions.chooseSitesNumber() == 1) sitesToSearch.add(FlightScanners.KIWI);
        System.out.print("For searching in " + FlightScanners.MOMONDO + " enter 1, enter 0 if not: ");
        if (UsefulFunctions.chooseSitesNumber() == 1) sitesToSearch.add(FlightScanners.MOMONDO);
        System.out.print("For searching in " + FlightScanners.SKYSCANNER + " enter 1, enter 0 if not: ");
        if (UsefulFunctions.chooseSitesNumber() == 1) sitesToSearch.add(FlightScanners.SKYSCANNER);
        return sitesToSearch;
    }



    //#endregion
}



