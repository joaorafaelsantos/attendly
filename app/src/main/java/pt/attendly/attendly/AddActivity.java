package pt.attendly.attendly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.User;

public class AddActivity extends AppCompatActivity {

    public static ArrayList<User> missingStudents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        manageData.getAddActivityData();
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
        ArrayList<User> currentStudents = ManagerActivity.currentStudents;

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
    }
}
