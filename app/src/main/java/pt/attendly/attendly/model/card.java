package pt.attendly.attendly.model;

import android.util.*;

/**
 * Created by Paulo on 04/04/2018.
 */

public class card {

    private String subjectBeginning,subjectEnding, subjectClassroom,subjectName,subjectBeacon,subjectCourse;


    public card(String subjectBeginning, String subjectEnding, String subjectClassroom, String subjectName, String subjectBeacon, String subjectCourse) {

        this.subjectBeginning = subjectBeginning;
        this.subjectEnding = subjectEnding;
        this.subjectClassroom = subjectClassroom;
        this.subjectName = subjectName;
        this.subjectBeacon = subjectBeacon;
        this.subjectCourse = subjectCourse;
    }


    public String getSubjectBeginning() {
        return subjectBeginning;
    }

    public void setSubjectBeginning(String subjectBeginning) {
        this.subjectBeginning = subjectBeginning;
    }

    public String getSubjectEnding() {
        return subjectEnding;
    }

    public void setSubjectEnding(String subjectEnding) {
        this.subjectEnding = subjectEnding;
    }

    public String getSubjectClassroom() {
        return subjectClassroom;
    }

    public void setSubjectClassroom(String subjectClassroom) {
        this.subjectClassroom = subjectClassroom;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectBeacon() {
        return subjectBeacon;
    }

    public void setSubjectBeacon(String subjectBeacon) {
        this.subjectBeacon = subjectBeacon;
    }

    public String getSubjectCourse() {
        return subjectCourse;
    }

    public void setSubjectCourse(String subjectCourse) {
        this.subjectCourse = subjectCourse;
    }
}
