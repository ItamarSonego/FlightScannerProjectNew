package Main;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.s;

public class UsefulFunctions {
    static Scanner input = new Scanner(System.in);

    //returns a number. this function ensures that a number is given and not text
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

    //returns 0 or 1. being used in 'Messages' class to pick sites to search in
    public static int chooseSitesNumber() {
        int val;
        while (true) {
            val = UsefulFunctions.chooseNumber();
            if (val == 0 || val == 1) return val;
            else System.out.print("Enter either 1 or 0: ");
        }

    }

    //checks if a string has at least one digit in it
    public static boolean containNumbers(String string) {
        for (Character c : string.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }

    //returns an integer day that is between 1-31
    public static int chooseDay() {
        int day = Integer.MIN_VALUE;
        System.out.print("Enter a day in this format: '08' / '27': ");
        while (day < 1 || day > 31) {
            day = UsefulFunctions.chooseNumber();
            if (day < 1 || day > 31) System.out.print("\rEnter a valid day between 1-31: ");         
        }
        return day;
    }

    //returns an integer day that is between 1-12
    public static int chooseMonth() {
        int month = Integer.MIN_VALUE;
        System.out.print("Enter a month in this format: '08' / '11': ");
        while (month < 1 || month > 12) {
            month = UsefulFunctions.chooseNumber();
            if (month < 1 || month > 12) System.out.print("\rEnter a valid month between 1-12: ");         
        }
        return month;
    }

    //returns an integer year that is at least 2024
    public static int chooseYear() {
        int year = Integer.MIN_VALUE;
        System.out.print("Enter a year in this format: '2022': ");
        while (year < 2024) {
            year = UsefulFunctions.chooseNumber();
            if (year < 2024) System.out.print("\rEnter a valid year from 2024: ");         
        }
        return year;
    }

    public static String dateFormatterForMomondo(LocalDate ld) {
        String month = formatStringFirstUpper(ld.getMonth().toString());
        int dayOfMonth = ld.getDayOfMonth();
        int year = ld.getYear();
        return "'" + month + " " + dayOfMonth + ", " + year + "'";
    }

    public static String formatStringFirstUpper(String string) {
        string = string.toUpperCase();
        string = string.charAt(0) + string.substring(1).toLowerCase();
        return string;
    }

    public static double findMin(List<Double> list) {
        double min = list.get(0);
        for (double x : list) {
            if (x < min) min = x;
        }
        
        return min;
    }

    

}