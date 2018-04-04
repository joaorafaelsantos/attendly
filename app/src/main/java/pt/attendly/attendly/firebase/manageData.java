package pt.attendly.attendly.firebase;

import android.os.Debug;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

/**
 * Created by Daniel on 21/03/2018.
 */

public class manageData {

    private static StorageReference mStorageRef;

    public static void write(String collection, Object object) {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(collection);
        String key = myRef.push().getKey();
        myRef.child(key).setValue(object);

    }


    public static ArrayList<Log> readLog() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Log");
        final ArrayList<Log> logList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    Log log = child.getValue(Log.class);
                    logList.add(log);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return logList;
    }

    public static ArrayList<Schedule> readSchedule() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedule");
        final ArrayList<Schedule> scheduleList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    Schedule schedule = child.getValue(Schedule.class);
                    scheduleList.add(schedule);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return scheduleList;

    }

    public static ArrayList<Classroom> readClassroom() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedule");
        final ArrayList<Classroom> classroomList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    Classroom classroom = child.getValue(Classroom.class);
                    classroomList.add(classroom);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return classroomList;
    }

    public static ArrayList<Subject> readSubject() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedule");
        final ArrayList<Subject> subjectList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    Subject subject = child.getValue(Subject.class);
                    subjectList.add(subject);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return subjectList;
    }

    public static ArrayList<User> readUser() {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedule");
        final ArrayList<User> userList = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    User user = child.getValue(User.class);
                    userList.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return userList;
    }


//    public static void storageTest(){
//
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//
//        Uri file = Uri.fromFile(new File());
//        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
//
//
//
//    }


}
