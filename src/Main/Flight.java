package Main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Flight {

    static Scanner input = new Scanner(System.in);
    //#region properties
    private LocalDate startDate;
    private LocalDate endDate;
    private String departureCity;
    private String landingCity;
    private int maxPrice;
    private int maxStops;
    private List<Passenger> passengers;

    //#endregion


    //#region constructors
    public Flight(String departureCity, String landingCity,
    int maxPrice, int maxStops, LocalDate startDate, LocalDate endDate, List<Passenger> passengers) {
        this.departureCity = departureCity;
        this.landingCity = landingCity;
        this.maxPrice = maxPrice;
        this.maxStops = maxStops;
        this.startDate = startDate;
        this.endDate = endDate;
        this.passengers = passengers;
    }

    //#endregion

    //#region getters and setters
    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getLandingCity() {
        return landingCity;
    }

    public void setLandingCity(String landingCity) {
        this.landingCity = landingCity;
    }

    public int getMaxStops() {
        return maxStops;
    }

    public void setMaxStops(int maxStops) {
        this.maxStops = maxStops;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    //#endregion
    
    public static Flight buildFlight() {
        
        ArrayList<Passenger> passengers = new ArrayList<>();

        //getting the number of passengers and validating it
        System.out.print("Enter the number of passengers (1-9): ");
        int numOfPassengers = UsefulFunctions.chooseNumber();

        //if numOfPassengers isn't between 1-9, there is a while loop that ensures that a valid number is entered
        if (!(numOfPassengers > 0 && numOfPassengers < 10) ) { 
            while (numOfPassengers < 1 || numOfPassengers > 9) {
                System.out.print("Enter a valid number between 1-9: ");
                numOfPassengers = UsefulFunctions.chooseNumber();
            }
        }

        //getting the age for each passenger and assigning it to the passenger list
        System.out.println("Enter age for each passenger: ");
        for (int i = 1; i <= numOfPassengers; i++) {
            int age = Integer.MIN_VALUE;
            System.out.print("Passenger " + i + ": ");
            while (age < 0) {
                age = UsefulFunctions.chooseNumber();
                if (age < 0) System.out.print("Enter a valid age: ");
            }
            passengers.add(new Passenger(age));
        }


        //getting the max price number and validating it
        int maxPrice = Integer.MIN_VALUE;
        System.out.print("Enter a max price (minimum : 50): ");
        while (maxPrice < 50) {
            maxPrice = UsefulFunctions.chooseNumber();
            if (maxPrice < 50) System.out.print("Enter a valid max price: ");
        }

        //getting the max stops number and validating it
        int maxStops = Integer.MIN_VALUE;
        System.out.print("Enter max stops (0-2): ");
        while (maxStops < 0 || maxStops > 2) {
            maxStops = UsefulFunctions.chooseNumber();
            if (maxStops < 0 || maxStops > 2) System.out.print("Enter a valid number between 0-2: ");
        }

        //getting the takeoff city and checking input
        System.out.print("Enter the city you would like to takeoff from: ");
        String takeOffCity = input.nextLine();
        while (UsefulFunctions.containNumbers(takeOffCity)) {
            System.out.print("Enter a valid text: ");
            takeOffCity = input.nextLine();
        }

        //getting the landing city and checking input
        System.out.print("Enter the city you would like to land in: ");
        String landingCity = input.nextLine();
        while (UsefulFunctions.containNumbers(landingCity)) {
            System.out.print("Enter a valid text: ");
            landingCity = input.nextLine();
        }

        //getting the start date and the end date, and checking if the start is before the end
        System.out.println("Enter a Start Date: ");
        LocalDate startDate = LocalDate.of(UsefulFunctions.chooseYear(), UsefulFunctions.chooseMonth(), UsefulFunctions.chooseDay());

        System.out.println("Enter a End Date: ");
        LocalDate endDate = LocalDate.of(UsefulFunctions.chooseYear(), UsefulFunctions.chooseMonth(), UsefulFunctions.chooseDay());
        
        //validating dates
        while (!startDate.isBefore(endDate)) {
            System.out.println("The start date isn't before the end date! enter valid dates! ");

            System.out.println("Enter a Start Date: ");
            startDate = LocalDate.of(UsefulFunctions.chooseYear(), UsefulFunctions.chooseMonth(), UsefulFunctions.chooseDay());

            System.out.println("Enter a End Date: ");
            endDate = LocalDate.of(UsefulFunctions.chooseYear(), UsefulFunctions.chooseMonth(), UsefulFunctions.chooseDay());
        }

        //building the flight object
        Flight flight = new Flight(takeOffCity, landingCity, maxPrice, maxStops, startDate, endDate, passengers);

        return flight;
    }


}
