package pt.attendly.attendly.firebase;

import android.app.Activity;
import android.net.Uri;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import pt.attendly.attendly.HistoryActivity;
import pt.attendly.attendly.MainActivity;
import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

/**
 * Created by Daniel on 21/03/2018.
 */

public class manageData {

    public static ArrayList<Classroom> classrooms = new ArrayList<>();
    public static ArrayList<Log> logs = new ArrayList<>();
    public static ArrayList<Schedule> schedules = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();

    static DatabaseReference Classroom_ref = FirebaseDatabase.getInstance().getReference("Classroom");
    static DatabaseReference Log_ref = FirebaseDatabase.getInstance().getReference("Log");
    static DatabaseReference Schedule_ref = FirebaseDatabase.getInstance().getReference("Schedule");
    static DatabaseReference Subject_ref = FirebaseDatabase.getInstance().getReference("Subject");
    static DatabaseReference User_ref = FirebaseDatabase.getInstance().getReference("User");


    static ValueEventListener VEL_Classroom;
    static ValueEventListener VEL_Log;
    static ValueEventListener VEL_Schedule;
    static ValueEventListener VEL_Subject;
    static ValueEventListener VEL_User;

    public static void removeAllEventListeners() {

        android.util.Log.d("XPTO", "REMOVE LISTENERS");
        try {
            Classroom_ref.removeEventListener(VEL_Classroom);
        }catch (Exception x)
        {

        }

        try {
            Log_ref.removeEventListener(VEL_Log);
        }catch (Exception x)
        {

        }

        try {
            Schedule_ref.removeEventListener(VEL_Schedule);
        }catch (Exception x)
        {

        }

        try {
            Subject_ref.removeEventListener(VEL_Subject);
        }catch (Exception x)
        {

        }

        try {
            User_ref.removeEventListener(VEL_User);
        }catch (Exception x)
        {

        }

    }

    public static void getMainActivityData(String userId) {

        removeAllEventListeners();

        User_ref.child(userId).addValueEventListener(VEL_User = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                final User user = dataSnapshot.getValue(User.class);
                android.util.Log.d("USER", user.getId());
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    User user = child.getValue(User.class);
//                    android.util.Log.d("USER", user.getId());
//                    users.add(user);
//                }
//
                Subject_ref.addValueEventListener(VEL_Subject = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        subjects.clear();

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child : children) {
                            Subject subject = child.getValue(Subject.class);
                            subjects.add(subject);
                        }

                        Schedule_ref.addValueEventListener(VEL_Schedule = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                schedules.clear();

                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    Schedule schedule = child.getValue(Schedule.class);
                                    schedules.add(schedule);
                                }

                                Classroom_ref.addValueEventListener(VEL_Classroom = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        classrooms.clear();

                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            Classroom classroom = child.getValue(Classroom.class);
                                            classrooms.add(classroom);
                                        }

                                        //FUNCTION TO EXECUTE AFTER

                                        MainActivity.getCurrentCard(user);
                                        android.util.Log.w("XPTO", user.getId());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public static void getHistoryActivityData(String userId) {

        removeAllEventListeners();

        Log_ref.orderByChild("id_user").equalTo(userId).addValueEventListener(VEL_Log = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                logs.clear();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Log log = child.getValue(Log.class);
                    android.util.Log.d("LOGHIS", String.valueOf(log.getDay_week()));
                    logs.add(log);
                }
                Subject_ref.addValueEventListener(VEL_Subject = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        subjects.clear();

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child : children) {
                            Subject subject = child.getValue(Subject.class);
                            subjects.add(subject);
                        }

                        //FUNCTION TO EXECUTE AFTER
                        HistoryActivity.getStudentAttendance();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public static void updateUserImage(String userId, Uri image_uri) {
        User_ref.child(userId).child("url_picture").setValue(image_uri.toString());
    }

    public static void addLog(Log newLog) {
        String key = Log_ref.push().getKey();
        Log_ref.child(key).setValue(newLog);
    }


}
