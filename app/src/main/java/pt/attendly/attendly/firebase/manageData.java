package pt.attendly.attendly.firebase;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pt.attendly.attendly.AddActivity;
import pt.attendly.attendly.HistoryActivity;
import pt.attendly.attendly.LoginActivity;
import pt.attendly.attendly.MainActivity;
import pt.attendly.attendly.ManagerActivity;
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

        try {
            Classroom_ref.removeEventListener(VEL_Classroom);
        } catch (Exception x) {

        }

        try {
            Log_ref.removeEventListener(VEL_Log);
        } catch (Exception x) {

        }

        try {
            Schedule_ref.removeEventListener(VEL_Schedule);
        } catch (Exception x) {

        }

        try {
            Subject_ref.removeEventListener(VEL_Subject);
        } catch (Exception x) {

        }

        try {
            User_ref.removeEventListener(VEL_User);
        } catch (Exception x) {

        }

    }


    public static void getAddActivityData() {
        removeAllEventListeners();

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

                        Log_ref.addValueEventListener(VEL_Log = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                logs.clear();

                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    Log log = child.getValue(Log.class);
                                    logs.add(log);
                                }

                                User_ref.addValueEventListener(VEL_User = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        users.clear();

                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            User user = child.getValue(User.class);
                                            users.add(user);
                                        }

                                        //FUNCTION TO EXECUTE AFTER
                                        AddActivity.getMissingStudents();

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

    public static void getManagerActivityData() {
        removeAllEventListeners();

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

                        Log_ref.addValueEventListener(VEL_Log = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                logs.clear();

                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    Log log = child.getValue(Log.class);
                                    logs.add(log);
                                }

                                User_ref.addValueEventListener(VEL_User = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        users.clear();

                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            User user = child.getValue(User.class);
                                            users.add(user);
                                        }

                                        ManagerActivity.getCurrentStudents();

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

    public static void getMainActivityData() {

        removeAllEventListeners();

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

                                Log_ref.orderByChild("date").addValueEventListener(VEL_Log = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        logs.clear();

                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            Log log = child.getValue(Log.class);
                                            logs.add(log);
                                        }

                                        User_ref.addValueEventListener(VEL_User = new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                users.clear();

                                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                                for (DataSnapshot child : children) {
                                                    User user = child.getValue(User.class);
                                                    users.add(user);
                                                }

                                                //FUNCTION TO EXECUTE AFTER
                                                try {
                                                    MainActivity.getCurrentCard();
                                                } catch (Exception e) {

                                                }

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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getHistoryActivityData() {

        removeAllEventListeners();

        Log_ref.orderByChild("id_user").equalTo(LoginActivity.loggedUser.getId()).addValueEventListener(VEL_Log = new ValueEventListener() {
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
                        try {
                            HistoryActivity.getStudentAttendance();
                        } catch (Exception e) {

                        }


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


    public static void updateUserImage(Uri image_uri) {
        User_ref.child(LoginActivity.loggedUser.getId()).child("url_picture").setValue(image_uri.toString());
    }

    public static void addLog(Log newLog) {
        String key = Log_ref.push().getKey();
        newLog.setLogID(key);
        Log_ref.child(key).setValue(newLog);
    }

    public static void updateLog(Log changedLog, int logID) {

    }

}
