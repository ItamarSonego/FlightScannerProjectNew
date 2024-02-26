package Main;
import java.util.ArrayList;
import java.util.Scanner;

public class Flight {

    static Scanner input = new Scanner(System.in);
    //#region properties
    private Date startDate;
    private Date endDate;
    private Country departureCountry;
    private Country landingCountry;
    private int maxPrice;
    private int maxStops;
    private ArrayList<Passenger> passengers;

    //#endregion


    //#region constructors
    public Flight(Country departureCountry, Country landingCountry,
    int maxPrice, int maxStops, Date startDate, Date endDate, ArrayList<Passenger> passengers) {
        this.departureCountry = departureCountry;
        this.landingCountry = landingCountry;
        this.maxPrice = maxPrice;
        this.maxStops = maxStops;
        this.startDate = startDate;
        this.endDate = endDate;
        this.passengers = passengers;
    }

    //#endregion

    //#region getters and setters
    public Country getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(Country departureCountry) {
        this.departureCountry = departureCountry;
    }

    public Country getLandingCountry() {
        return landingCountry;
    }

    public void setLandingCountry(Country landingCountry) {
        this.landingCountry = landingCountry;
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

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //#endregion
    
    public static Flight buildFlight() {
        int numOfPassengers = Integer.MIN_VALUE;
        ArrayList<Passenger> passengers = new ArrayList<>();
        System.out.print("Enter the number of passengers (1-9): ");
        while (numOfPassengers < 1 || numOfPassengers > 9) {
            numOfPassengers = UsefulFunctions.chooseNumber();
            if (numOfPassengers < 1 || numOfPassengers > 9) 
            System.out.print("Enter a valid number between 1-9: ");
        }

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

        int maxPrice = Integer.MIN_VALUE;
        System.out.print("Enter a max price (minimum : 50): ");
        while (maxPrice < 50) {
            maxPrice = UsefulFunctions.chooseNumber();
            if (maxPrice < 50) System.out.print("Enter a valid max price: ");
        }

        int maxStops = Integer.MIN_VALUE;
        System.out.print("Enter max stops (0-2): ");
        while (maxStops < 0 || maxStops > 2) {
            maxStops = UsefulFunctions.chooseNumber();
            if (maxStops < 0 || maxStops > 2) System.out.print("Enter a valid number between 0-2: ");
        }

        String takeoffCountry = "";
        Country takeoffCountryEnum = Country.ISRAEL;
        boolean valid = false;
        System.out.print("Enter country to takeoff: ");
        while (valid == false) {
            takeoffCountry = input.nextLine();
            for (Country countryEnum : Country.values()) {
                if (countryEnum.getCountryString().equals(takeoffCountry)) {
                    valid = true;
                    takeoffCountryEnum = countryEnum;
                    break;
                }
            }
            if (valid == false) System.out.print("Enter a valid country name: ");
        }
        
        String landingCountry = "";
        Country landingCountryEnum = Country.ISRAEL;
        valid = false;
        System.out.print("Enter landing country: ");
        while (valid == false) {
            landingCountry = input.nextLine();
            for (Country countryEnum : Country.values()) {
                if (countryEnum.getCountryString().equals(landingCountry)) {
                    valid = true;
                    landingCountryEnum = countryEnum;
                    break;
                }
            }
            if (valid == false) System.out.print("Enter a valid country name: ");

        }

        System.out.print("Choose a start date to the trip");
        Date startDate = new Date(Date.chooseDay(), Date.chooseMonth(), Date.chooseYear());
        System.out.print("Choose an end date to the trip ");
        Date endDate = new Date(Date.chooseDay(), Date.chooseMonth(), Date.chooseYear());
        
        Flight flight = new Flight(takeoffCountryEnum, landingCountryEnum, maxPrice, maxStops, startDate, endDate, passengers);
        return flight;
    }


}
