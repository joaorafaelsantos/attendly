package pt.attendly.attendly;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.User;

public class ManagerActivity extends AppCompatActivity {

    public static ArrayList<User> currentStudents = new ArrayList<>();
    public static ArrayList<Log> currentLogs = new ArrayList<>();

    private static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    //private List<Character> charactersList;
    private static RVAdapter studentsAdpter;

    static TextView txtStudents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        txtStudents = findViewById(R.id.txtStudents);

        manageData.getManagerActivityData();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this );
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)
        // mAdapter = new MyAdapter(myDataset);
        // mRecyclerView.setAdapter(mAdapter);
        //initializeDat();

        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });







    }

    // Flag to verify if the on resume event is called only after the first time that is executed
    // This flag is needed to verify if the back button was pressed on the Add Activity to refresh the data
    int magicFlag = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if (magicFlag >= 1) {
            getCurrentStudents();
        }
        magicFlag++;

    }

    public static void getCurrentStudents( ) {

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

            String currentDay = (String) DateFormat.format("dd", currentDate);

            // Check if exists logs of the user on the current class (if it's true, add to the current students array)
            for (int i = 0; i < logs.size(); i++) {
                Log tempLog = logs.get(i);
                Date tempDate = parseDate(tempLog.getDate());
                String tempDay = (String) DateFormat.format("dd", tempDate);

                if (tempLog.getId_schedule() == currentSchedule
                        && currentDate.getDay() + 1 == tempDate.getDay() + 1
                        && currentDay.equals(tempDay)
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

        android.util.Log.d("teste", "This student is on the class: " + currentStudents.size());

        AddActivity.getStudentsArray();



        txtStudents.setText("Alunos presentes: " + String.valueOf(currentStudents.size()) + "/" + String.valueOf(currentStudents.size() + AddActivity.missingStudents.size()));

        Collections.sort(currentStudents, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        studentsAdpter = new RVAdapter(currentStudents, "Manager");
        mRecyclerView.setAdapter(studentsAdpter);
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
    public static void removePresence(int id) {

        // Get the student to delete
        User userToDelete = currentStudents.get(id);
        String logToChangeID = "";

        // Check the log of the selected student
        for (int i = 0; i<currentLogs.size(); i++) {
            if (currentLogs.get(i).getId_user().equals(userToDelete.getId())) {
                logToChangeID = currentLogs.get(i).getLogID();
            }
        }
        manageData.updateLog(logToChangeID, 0);
        currentStudents.remove(userToDelete);
//        android.util.Log.d("teste", String.valueOf(currentStudents.size()));
        studentsAdpter = new RVAdapter(currentStudents, "Manager");
        mRecyclerView.setAdapter(studentsAdpter);

    }

    // To replace by the RecyclerView position
    public static void some(View v, int position) {
        removePresence(position);
    }


}


