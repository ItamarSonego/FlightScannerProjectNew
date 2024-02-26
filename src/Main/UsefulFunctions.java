package Main;
import java.util.Scanner;

public class UsefulFunctions {
    static Scanner input = new Scanner(System.in);
    public static int chooseNumber() {
        int val = Integer.MIN_VALUE;
        while (true) {
            String inputString = input.nextLine();
            try {
                val = Integer.parseInt(inputString);
                break;
            }
            catch (NumberFormatException nfe) {
                System.out.print("Enter a valid Number: ");
            }
        }
        return val; 
    }

    public static int chooseSitesNumber() {
        int val;
        while (true) {
            val = UsefulFunctions.chooseNumber();
            if (val == 0 || val == 1) return val;
            else System.out.print("Enter either 1 or 0: ");
    }
}
}