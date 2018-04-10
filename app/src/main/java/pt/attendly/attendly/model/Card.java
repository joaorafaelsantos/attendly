package pt.attendly.attendly.model;

public class Card {

    private String subjectBeginning, subjectEnding, subjectClassroom, subjectName, subjectBeacon, subjectCourse;
    private int subjectSchedule;
    private int subjectId;

    public Card(String subjectBeginning, String subjectEnding, String subjectClassroom, String subjectName, String subjectBeacon, String subjectCourse, int subjectSchedule, int subjectId, int subjectClassroomID) {
        this.subjectBeginning = subjectBeginning;
        this.subjectEnding = subjectEnding;
        this.subjectClassroom = subjectClassroom;
        this.subjectName = subjectName;
        this.subjectBeacon = subjectBeacon;
        this.subjectCourse = subjectCourse;
        this.subjectSchedule = subjectSchedule;
        this.subjectId = subjectId;
        this.subjectClassroomID = subjectClassroomID;
    }

    public int getSubjectClassroomID() {

        return subjectClassroomID;
    }

    public void setSubjectClassroomID(int subjectClassroomID) {
        this.subjectClassroomID = subjectClassroomID;
    }

    private int subjectClassroomID;


    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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

    public int getSubjectSchedule() {
        return subjectSchedule;
    }

    public void setSubjectSchedule(int subjectSchedule) {
        this.subjectSchedule = subjectSchedule;
    }

}
