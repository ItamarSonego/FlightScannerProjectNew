package Sites;
import Main.Flight;
public interface FlightSearchSite {
    public static final String googleFlightsUrl = "https://www.google.com/travel/flights";
    public static final String kiwiUrl = "https://www.kiwi.com/en/";
    public static final String elalUrl = "https://www.elal.com/he/Israel/Pages/default.aspx";
    public static final String momondoUrl = "https://www.momondo.com/";
    public static final String SkyScannersUrl = "https://www.skyscanner.net";


    public abstract void enterPlaces(Flight flight);
    

}

