package pt.attendly.attendly;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.other.layoutChanges;

public class HistoryActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);

                    return true;
                case R.id.navigation_historic:


                    return true;
                case R.id.navigation_profile:
                    Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);

                    return true;
            }
            return false;
        }
    };

    private static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static HistoryAdapter historyAdapter;

    static TextView txtAttendence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        txtAttendence = findViewById(R.id.txtAttendence);

        manageData.getHistoryActivityData();

        BottomNavigationView mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.getMenu().findItem(R.id.navigation_historic).setChecked(true);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        layoutChanges.setIconSize(mBottomNavigationView, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this );
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public static void showHistory(){

        ArrayList<Log> userLogs = manageData.logs;

        Collections.sort(userLogs, new Comparator<Log>() {
            public int compare(Log o1, Log o2) {
                if (o1.getDate() == null || o2.getDate() == null)
                    return 0;
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        historyAdapter= new HistoryAdapter(userLogs);
        mRecyclerView.setAdapter(historyAdapter);

        getStudentAttendance();

    }


    // Method to return the attendance of the user
    public static void getStudentAttendance() {
        // Get the all the user logs
        ArrayList<Log> userLogs =  manageData.logs;

        // Initiliaze the attendance value with 0
        int attendance = 0;

        // For every user log increments the presence variable (it's 1 if the student went to the class)
        for (int i = 0; i<userLogs.size(); i++) {
            attendance+=userLogs.get(i).getPresence();
        }

        // Multiply the attendance by 100 and divide by all the user logs to get the average in percentage
        try{
            attendance = (attendance * 100) / userLogs.size();
        }
        catch (Exception e)
        {

        }

        android.util.Log.d("LOGHIS", String.valueOf(attendance));

        txtAttendence.setText("Assiduidade: " + String.valueOf(attendance) + "%");

    }

    //DISABLE BACK BUTTON PRESS TO PREVIOUS ACTIVITY
    @Override
    public void onBackPressed() {
//        this.finishAffinity();
    }

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    if(x1>x2)
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                    }

                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
