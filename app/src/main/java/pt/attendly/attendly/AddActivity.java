package pt.attendly.attendly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.User;

public class AddActivity extends AppCompatActivity {

    public static ArrayList<User> missingStudents = new ArrayList<>();
    public static ArrayList<User> currentStudents = new ArrayList<>();

    private static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static RVAdapter studentsAdpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        manageData.getAddActivityData();

        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerView2);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this );
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public static void getMissingStudents() {

        // Clear data
        missingStudents.clear();

        // Load data
        ArrayList<User> users = manageData.users;
        User teacher = LoginActivity.loggedUser;
        ArrayList<Card> cards = MainActivity.cards;

        // All students available array
        ArrayList<User> availableStudents = new ArrayList<>();

        // If exists a current class
        if (cards.size() > 0) {
            int currentSubjectID = cards.get(0).getSubjectId();

            // Save to an array all the students available to that subject
            for (int i = 0; i< users.size(); i++) {
                User tempUser = users.get(i);
                ArrayList<Integer> tempUserSubjectsID = tempUser.getSubjects();
                for (int j = 0; j<tempUserSubjectsID.size(); j++) {
                    // Filter the students with that subject (and forget the teacher)
                    if (tempUserSubjectsID.get(j) == currentSubjectID && !teacher.getId().equals(tempUser.getId())) {
                        availableStudents.add(tempUser);
                    }
                }
            }
        }

        // Load the current students on the class
        currentStudents = ManagerActivity.currentStudents;

        // Copy the available students to the missing students array
        missingStudents = new ArrayList<>(availableStudents);

        // Check in the available students if the student is on the class, if it's true then remove that position on the missing students array
        for (int i = 0; i<availableStudents.size(); i++) {
            User availableStudent = availableStudents.get(i);
            for (int j = 0; j<currentStudents.size(); j++) {
                User currentStudent = currentStudents.get(j);
                if (availableStudent.getId().equals(currentStudent.getId())) {
                    missingStudents.remove(availableStudent);
                }
            }
        }

        // To remove (log on the console)
        for (int i = 0; i<missingStudents.size(); i++) {
            android.util.Log.d("teste", "This student is missing: " + missingStudents.get(i).getName());
        }

        Collections.sort(missingStudents, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getName().compareToIgnoreCase(u2.getName());
            }
        });

        studentsAdpter= new RVAdapter(missingStudents);
        mRecyclerView.setAdapter(studentsAdpter);
    }

    public static void addStudent (int id) {

        // Get the student to add
        User userToAdd = missingStudents.get(id);

        // Current subject
        Card currentSubject = MainActivity.cards.get(0);
        int idSubject= currentSubject.getSubjectId();
        int idClass= currentSubject.getSubjectClassroomID();
        int idSchedule= currentSubject.getSubjectSchedule();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dataFormated = simpleDateFormat.format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);

        // Log to add
        Log log = new Log(userToAdd.getId(), "", idSubject, dataFormated, day , 1, idClass, idSchedule);
        manageData.addLog(log);

        // Remove the student in the missing students array
        missingStudents.remove(userToAdd);
        currentStudents.add(userToAdd);
        ManagerActivity.currentStudents = currentStudents;

    }

    public static void some(View v, int position) {
        android.util.Log.d("teste", missingStudents.get(position).getName());
        addStudent(position);
    }
}
