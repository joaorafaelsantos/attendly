package pt.attendly.attendly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.User;

public class ManagerActivity extends AppCompatActivity {

    public static ArrayList<User> currentStudents = new ArrayList<>();
    public static ArrayList<Log> currentLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        manageData.getManagerActivityData();
    }

    public static void getCurrentStudents() {

        // Clear data
        currentStudents.clear();
        currentLogs.clear();

        // Load data
        ArrayList<Log> logs = manageData.logs;
        ArrayList<User> users = manageData.users;
        User teacher = LoginActivity.loggedUser;
        ArrayList<Card> cards = MainActivity.cards;



        // If exists a current class
        if (cards.size() > 0) {
            int currentSchedule = cards.get(0).getSubjectSchedule();
            Date currentDate = new Date();

            // Check if exists logs of the user on the current class (if it's true, add to the current students array)
            for (int i = 0; i < logs.size(); i++) {
                Log tempLog = logs.get(i);
                Date tempDate = parseDate(tempLog.getDate());

                if (tempLog.getId_schedule() == currentSchedule
                        && currentDate.getDay() + 1 == tempDate.getDay() + 1
                        && currentDate.getMonth() == tempDate.getMonth()
                        && currentDate.getYear() == tempDate.getYear()
                        && !teacher.getId().equals(tempLog.getId_user())
                        && tempLog.getPresence() == 1) {

                    for (int j = 0; j<users.size(); j++) {
                        User tempUser = users.get(j);
                        String tempUserID = tempUser.getId();
                        if (tempUserID.equals(tempLog.getId_user())) {
                            currentStudents.add(tempUser);
                            currentLogs.add(tempLog);
                        }
                    }
                }
            }
        }

        // To remove (log on the console)
        for (int i = 0; i<currentStudents.size(); i++) {
            android.util.Log.d("teste", "This student is on the class: " + currentStudents.get(i).getName());
        }
    }

    // Date parse method
    static Date parseDate (String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date date = simpleDateFormat.parse(stringDate);

            return date;
        } catch (ParseException ex) {
            android.util.Log.d("error", "Exception " + ex);
            return null;
        }
    }

    // Open activity handler
    public void openAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    // Method to remove the presence of the student
    public void removePresence(int id) {

        // Get the student to delete
        User userToDelete = currentStudents.get(id);
        String logToChangeID = "";

        // Check the log of the selected student
        for (int i = 0; i<currentLogs.size(); i++) {
            if (currentLogs.get(i).getId_user().equals(userToDelete.getId())) {
                logToChangeID = currentLogs.get(i).getLogID();
            }
        }
        manageData.updateLog(logToChangeID);
    }

    // To replace by the RecyclerView position
    public void some (View v) {
    removePresence(0);
    }


}


