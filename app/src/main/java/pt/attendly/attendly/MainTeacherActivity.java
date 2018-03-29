package pt.attendly.attendly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Schedule;

public class MainTeacherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);



//        Log log = new Log("7ygXxTdPxpNlAiuUE1Dce7naFet1", "B0:E2:35:9C:3F:B7", 2, "12-12-2017", 4, 0, 0);
//
//        manageData.write("Log", log);

//        ArrayList<Log> logList = manageData.readLog();
        ArrayList<Schedule> scheduleList = manageData.readSchedule();

//        android.util.Log.d("FB", String.valueOf(logList.get(0).getId_classroom()));

    }
}
