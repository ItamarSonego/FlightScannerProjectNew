package Main;

public class ageRange {

    //#region properties
    private int startAge;
    private int endAge;
    //#endregion
    


    //#region constructors
    public ageRange(int startAge, int endAge) {
        this.startAge = startAge;
        this.endAge = endAge;
    }
    //#endregion



    //#region getters and setters
    public int getStartAge() {
        return startAge;
    }
    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }
    public int getEndAge() {
        return endAge;
    }
    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }
    //#endregion

    //#region other methods
    public boolean contains(int age) {
        return age >= this.startAge && age <= this.endAge;
    }
    //#endregion

    
}
