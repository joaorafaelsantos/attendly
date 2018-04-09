package pt.attendly.attendly.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

public class Queries {

    private void dontCall(){
//        User_ref.addValueEventListener(VEL_User = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                users.clear();
//
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    User user = child.getValue(User.class);
//                    users.add(user);
//                }
//
//                //FUNCTION TO EXECUTE AFTER
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        Subject_ref.addValueEventListener(VEL_Subject = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                subjects.clear();
//
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    Subject subject = child.getValue(Subject.class);
//                    subjects.add(subject);
//                }
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        Schedule_ref.addValueEventListener(VEL_Schedule = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                schedules.clear();
//
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    Schedule schedule = child.getValue(Schedule.class);
//                    schedules.add(schedule);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//        Subject_ref.addValueEventListener(VEL_Schedule = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                schedules.clear();
//
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    Schedule schedule = child.getValue(Schedule.class);
//                    schedules.add(schedule);
//                }
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//        Classroom_ref.addValueEventListener(VEL_Classroom = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                classrooms.clear();
//
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    Classroom classroom = child.getValue(Classroom.class);
//                    classrooms.add(classroom);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

//        Log_ref.addValueEventListener(VEL_Log = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                logs.clear();
//
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                for (DataSnapshot child : children) {
//                    Log log = child.getValue(Log.class);
//                    logs.add(log);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    }

}
