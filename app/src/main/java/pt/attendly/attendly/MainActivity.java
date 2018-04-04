package pt.attendly.attendly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCard("3SGi1vnVujY7y4xsHc07JmBhS9U2");
    }


    public void enterLog(View view){
        Intent intent = new Intent(this, MainTeacherActivity.class);
        intent.putExtra("teste", "teste");
        startActivity(intent);
    }

    public void currentCard(String ID) {
        String userID = ID;

        // Hardcode models (replace by firebase data)
        ArrayList<User> users = new ArrayList<>();
        int[] exampleSubjects = {0};
        User u1 = new User("3SGi1vnVujY7y4xsHc07JmBhS9U2", 0, "João", "url", "TSIW", exampleSubjects);
        User u2 = new User("7ygXxTdPxpNlAiuUE1Dce7naFet1", 0, "Paulo", "url", "TSIW", exampleSubjects);
        User u3 = new User("BsT8jtyt7HWwtDu6Jq2xcvJZvW02", 0, "Daniel", "url", "TSIW", exampleSubjects);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        ArrayList<Schedule> schedules = new ArrayList<>();
        Schedule s1 = new Schedule(0, "11:00", "13:00", 3, 0);
        Schedule s2 = new Schedule(1, "11:00", "13:00", 4, 0);
        Schedule s3 = new Schedule(2, "14:00", "18:00", 4, 0);
        schedules.add(s1);
        schedules.add(s2);
        schedules.add(s3);
        int[] exampleSchedules = {0, 1, 2};
        int[] exampleTeachers = {3};
        ArrayList<Subject> subjects = new ArrayList<>();
        Subject sb1 = new Subject(0, "PDM", "TSIW", exampleSchedules , exampleTeachers);
        subjects.add(sb1);

        ArrayList<Classroom> classrooms = new ArrayList<>();
        Classroom c1 = new Classroom(0, "B206", "IDBEACONXPTO");
        classrooms.add(c1);


        // Get the current user
        User currentUser = new User();
        for (int i = 0; i<users.size(); i++) {
            String tempUserID = users.get(i).getId();
            if (userID.equals(tempUserID)) {
                currentUser = users.get(i);
            }
        }

        // Get the current date (day, hour and minute)
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Get the schedules of the current user
        int[] userSubjects = currentUser.getSubjects();
        ArrayList<Integer> userSchedulesID = new ArrayList<>();

        for (int i = 0; i<subjects.size(); i++) {
            Subject tempSubject = subjects.get(i);
            int tempSubjectID = subjects.get(i).getId();

            for (int j = 0; j<userSubjects.length; j++) {
                int tempUserSubjectID = userSubjects[j];

                if (tempSubjectID == tempUserSubjectID) {
                    for (int k = 0; k<tempSubject.getSchedules().length; k++) {
                        int tempScheduleID = tempSubject.getSchedules()[k];
                        userSchedulesID.add(tempScheduleID);
                    }
                }
            }
        }

        ArrayList<Schedule> userSchedules = new ArrayList<>();
        for (int i = 0; i<userSchedulesID.size(); i++) {
            for (int j = 0; j<schedules.size(); j++) {
                if (schedules.get(j).getId() == userSchedulesID.get(i)) {
                    userSchedules.add(schedules.get(j));
                }
            }
        }

        boolean subjectExists = false;
        String subjectBeginning = "";
        String subjectEnding = "";
        String subjectClassroom = "";
        String subjectName = "";
        String subjectBeacon = "";
        String subjectCourse = "";


        for (int i = 0; i<userSchedules.size(); i++) {
            // NESTE DIA TEM AULAS

            if (day == userSchedules.get(i).getDay_week()) {

                Date currentDate = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;

                subjectBeginning = userSchedules.get(i).getBeginning();
                subjectEnding = userSchedules.get(i).getEnding();

                int tempClassroomID = userSchedules.get(i).getClassroom();

                for (int j = 0; j<classrooms.size(); j++) {
                    if (classrooms.get(j).getId() == tempClassroomID) {
                        subjectClassroom = classrooms.get(j).getName();
                        subjectBeacon = classrooms.get(j).getId_beacon();
                    }
                }

                int tempScheduleID = userSchedules.get(i).getId();

                for (int j = 0; j<subjects.size(); j++) {
                    Log.d("XPTO", String.valueOf(subjects.size()));

                    for (int k = 0; k<subjects.get(j).getSchedules().length; k++) {
                        int tempID = subjects.get(j).getSchedules()[k];

                        if (tempID == tempScheduleID) {
                            subjectName = subjects.get(j).getName();
                            subjectCourse = subjects.get(j).getCourse();
                        }

                    }


                }

                Date subjectDate = new Date();
                String[] tempArray = subjectEnding.split(":");
                subjectDate.setHours(Integer.parseInt(tempArray[0]));
                subjectDate.setMinutes(Integer.parseInt(tempArray[1]));
                subjectDate.setSeconds(0);

//                VERIFICAR HORA DA AULA - SE A AULA NÃO TIVER PASSADO (HORA FINAL DA AULA)
                if (subjectDate.after(currentDate)) {
                    subjectExists = true;
                    Log.d("XPTO", subjectBeginning);
                    Log.d("XPTO", subjectEnding);
                    Log.d("XPTO", subjectClassroom);
                    Log.d("XPTO", subjectName);
                    Log.d("XPTO", subjectBeacon);
                    Log.d("XPTO", subjectCourse);
                    break;
                }
            }

        }

//        Log.d("XPTO", String.valueOf(subjectExists));
    }

}

