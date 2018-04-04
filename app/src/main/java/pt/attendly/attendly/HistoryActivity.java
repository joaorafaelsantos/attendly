package pt.attendly.attendly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Subject;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Call the methods
        ArrayList<Log> logs = getLogs("3SGi1vnVujY7y4xsHc07JmBhS9U2");
        android.util.Log.d("FB", String.valueOf(getStudentAttendance("3SGi1vnVujY7y4xsHc07JmBhS9U2")));
    }

    // Method to return all the logs of a user
    public ArrayList<Log> getLogs(String ID) {
        // Hardcode - to replace by firebase
        ArrayList<Integer> exampleSchedules = new ArrayList<>();
        exampleSchedules.add(0);
        exampleSchedules.add(1);
        exampleSchedules.add(2);
        ArrayList<Integer> exampleTeachers = new ArrayList<>();
        exampleTeachers.add(3);
        ArrayList<Subject> subjects = new ArrayList<>();
        Subject sb1 = new Subject(0, "PDM", "TSIW", exampleSchedules , exampleTeachers);
        ArrayList<Classroom> classrooms = new ArrayList<>();
        Classroom c1 = new Classroom(0, "B206", "IDBEACONXPTO");
        classrooms.add(c1);
        ArrayList<Log> logs = new ArrayList<Log>();
        Log l1 = new Log(ID, "BLUETOOTHID", 0, "28-03-2018", 4, 1, 0);
        Log l2 = new Log(ID, "BLUETOOTHID", 0, "03-04-2018", 3, 0, 0);
        Log l3 = new Log("BsT8jtyt7HWwtDu6Jq2xcvJZvW02", "BLUETOOTHID", 0, "04-04-2018", 4, 1, 0);
        logs.add(l1);
        logs.add(l2);
        logs.add(l3);

        // For every log only adds the user ones
        ArrayList<Log> userLogs = new ArrayList<Log>();

        for (int i = 0; i<logs.size(); i++) {
            Log tempLog = logs.get(i);
            if (tempLog.getId_user().equals(ID)) {
                userLogs.add(tempLog);
            }
        }

        return userLogs;
    }

    // Method to return the attendance of the user
    public int getStudentAttendance(String id) {
        // Get the all the user logs
        ArrayList<Log> userLogs =  getLogs(id);

        // Initiliaze the attendance value with 0
        int attendance = 0;

        // For every user log increments the presence variable (it's 1 if the student went to the class)
        for (int i = 0; i<userLogs.size(); i++) {
            attendance+=userLogs.get(i).getPresence();
        }

        // Multiply the attendance by 100 and divide by all the user logs to get the average in percentage
        attendance = (attendance * 100) / userLogs.size();
        return attendance;
    }
}
