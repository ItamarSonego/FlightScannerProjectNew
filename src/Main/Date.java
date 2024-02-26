package Main;
//#region imports
import java.util.Scanner;
//#endregion


public class Date {

    static Scanner input = new Scanner(System.in);
    
    //#region properties
    private int day;
    private int month;
    private int year;
    //#endregion

    //#region constructors
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    //#endregion

    //#region getters and setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    //#endregion


    //#region other functions

    public static int chooseDay() {
        int day = Integer.MIN_VALUE;
        System.out.print("Enter a day in this format: '08' / '27': ");
        while (day < 1 || day > 31) {
            day = UsefulFunctions.chooseNumber();
            if (day < 1 || day > 31) System.out.print("\rEnter a valid day between 1-31: ");         
        }
        return day;
    }

    public static int chooseMonth() {
        int month = Integer.MIN_VALUE;
        System.out.print("Enter a month in this format: '08' / '11': ");
        while (month < 1 || month > 12) {
            month = UsefulFunctions.chooseNumber();
            if (month < 1 || month > 12) System.out.print("\rEnter a valid month between 1-12: ");         
        }
        return month;
    }


    public static int chooseYear() {
        int year = Integer.MIN_VALUE;
        System.out.print("Enter a year in this format: '2022': ");
        while (year < 2024) {
            year = UsefulFunctions.chooseNumber();
            if (year < 2024) System.out.print("\rEnter a valid year from 2024: ");         
        }
        return year;
    }

    //#endregion
}
