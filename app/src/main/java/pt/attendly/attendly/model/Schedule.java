package pt.attendly.attendly.model;

/**
 * Created by JOAO on 21/03/2018.
 */

public class Schedule {
    private int id;
    private String beginning;
    private String ending;
    private int day_week;

    public Schedule(int id, String beginning, String ending, int day_week) {
        this.id = id;
        this.beginning = beginning;
        this.ending = ending;
        this.day_week = day_week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginning() {
        return beginning;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public int getDay_week() {
        return day_week;
    }

    public void setDay_week(int day_week) {
        this.day_week = day_week;
    }
}
