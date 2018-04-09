package pt.attendly.attendly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.User;

public class ManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        manageData.getManagerActivityData();
    }

    public static void getCurrentStudents() {
        ArrayList<Log> logs = manageData.logs;
        User teacher = LoginActivity.loggedUser;

        ArrayList<Integer> teacherSubjectsIDs = teacher.getSubjects();

        ArrayList<Card> cards = MainActivity.cards;

        if (cards.size() > 0) {
            int currentSchedule = cards.get(0).getSubjectSchedule();
            Date currentDate = new Date();


            for (int i = 0 ; i<logs.size(); i++) {
                Log tempLog = logs.get(i);
                Date tempDate = parseDate(tempLog.getDate());
                if (tempLog.getId_schedule() == currentSchedule
                        && currentDate.getDay() == tempDate.getDay()
                        && currentDate.getMonth() == tempDate.getMonth()
                        && currentDate.getYear() == tempDate.getYear()
                        && !LoginActivity.loggedUser.getId().equals(tempLog.getId_user())
                        ) {

                }

            }


        }
    }

    static Date parseDate(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try
        {
            Date date = simpleDateFormat.parse(stringDate);
            return date;
        }
        catch (ParseException ex)
        {
            android.util.Log.d("teste","Exception "+ex);
            return null;
        }
    }


}
