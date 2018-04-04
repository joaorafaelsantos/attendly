package pt.attendly.attendly;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    protected static final String TAG = "MonitoringActivity";
    protected static final String TAG2 = "MonitoringActivity2";
    private BeaconManager beaconManager;
    private String BluetoothMacAddressUser, beaconSala;
    private boolean presença,tempo, classOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        currentCard("3SGi1vnVujY7y4xsHc07JmBhS9U2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.d(TAG,"BEACONS"+beacons);
                if (beacons.size() > 0) {
                    for (Beacon beacon: beacons) {
                        // Obter Beacons que estão a menos de 2.5m
                        if (beacon.getDistance() <= 2.5) {
                            // Obter o macaddress do beacon ; verificar qual é o mac address da sala da proxima aula ; comparar os dos mac address
                            // verificar se o beacon detetado corresponde com o da sala
                            if (beacons.iterator().next().getBluetoothAddress()==beaconSala){
                                // verificar se prof abriu a aula

                                if(classOpen== true){



                                    // se coincidirem  verificar se já tiver falta não pode marcar presença
                                    if(presença== false){

                                        // caso não tenha falta verificar se está dentro do tempo para marcar presença, caso contrário não faz nada
                                        if (tempo   == true ){

                                            // registar na base de dados a presença
                                            // alterar a card principal
                                        }
                                    }
                                }
                            }
                            Log.i(TAG2, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                            Log.i(TAG, "The beacon "+beacons.iterator().next().getBluetoothAddress()+" bluetooth");
                        }
                    }
                    Log.i(TAG,"BT Device"+ getBluetoothMacAddress());
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }


    public void enterLog(View view){
        Intent intent = new Intent(this, MainTeacherActivity.class);
        intent.putExtra("teste", "teste");
        startActivity(intent);
    }

    private String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            try {
                Field mServiceField = bluetoothAdapter.getClass().getDeclaredField("mService");
                mServiceField.setAccessible(true);

                Object btManagerService = mServiceField.get(bluetoothAdapter);

                if (btManagerService != null) {
                    bluetoothMacAddress = (String) btManagerService.getClass().getMethod("getAddress").invoke(btManagerService);
                }
            } catch (NoSuchFieldException e) {

            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
        } else {
            bluetoothMacAddress = bluetoothAdapter.getAddress();
        }
        return bluetoothMacAddress;
    }

    public void currentCard(String ID) {
        final String userID = ID;
        final ArrayList<User> users = new ArrayList<>();
        final ArrayList<Schedule> schedules = new ArrayList<>();
        final ArrayList<Subject> subjects = new ArrayList<>();
        final ArrayList<Classroom> classrooms = new ArrayList<>();

        Log.d("FB", "before fb");
        //GET USERS
        DatabaseReference User_ref = FirebaseDatabase.getInstance().getReference("User");
        User_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    User user = child.getValue(User.class);
                    users.add(user);
                }
                //GET SCHEDULES
                DatabaseReference Schedule_ref = FirebaseDatabase.getInstance().getReference("Schedule");
                Schedule_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        for (DataSnapshot child : children) {
                            Schedule schedule = child.getValue(Schedule.class);
                            schedules.add(schedule);
                        }
                        //GET SUBJECTS
                        DatabaseReference Subject_ref = FirebaseDatabase.getInstance().getReference("Subject");
                        Subject_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    Subject subject = child.getValue(Subject.class);
                                    subjects.add(subject);
                                }
                                //GET CLASSROOMS
                                DatabaseReference Classroom_ref = FirebaseDatabase.getInstance().getReference("Classroom");
                                Classroom_ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            Classroom classroom = child.getValue(Classroom.class);
                                            classrooms.add(classroom);
                                        }

                                        Log.d("FB", "after all data fb");
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
                                        ArrayList<Integer> userSubjects = currentUser.getSubjects();
                                        ArrayList<Integer> userSchedulesID = new ArrayList<>();

                                        for (int i = 0; i<subjects.size(); i++) {
                                            Subject tempSubject = subjects.get(i);
                                            int tempSubjectID = subjects.get(i).getId();

                                            for (int j = 0; j<userSubjects.size(); j++) {
                                                int tempUserSubjectID = userSubjects.get(j);

                                                if (tempSubjectID == tempUserSubjectID) {
                                                    for (int k = 0; k<tempSubject.getSchedules().size(); k++) {
                                                        int tempScheduleID = tempSubject.getSchedules().get(k);
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

                                                    for (int k = 0; k<subjects.get(j).getSchedules().size(); k++) {
                                                        int tempID = subjects.get(j).getSchedules().get(k);

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

                                        Log.d("XPTO", String.valueOf(subjectExists));
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


        Log.d("FB", "after fb");


//        // Hardcode models (replace by firebase data)
//        ArrayList<User> users = new ArrayList<>();
//        int[] exampleSubjects = {0};
//        User u1 = new User("3SGi1vnVujY7y4xsHc07JmBhS9U2", 0, "João", "url", "TSIW", exampleSubjects);
//        User u2 = new User("7ygXxTdPxpNlAiuUE1Dce7naFet1", 0, "Paulo", "url", "TSIW", exampleSubjects);
//        User u3 = new User("BsT8jtyt7HWwtDu6Jq2xcvJZvW02", 0, "Daniel", "url", "TSIW", exampleSubjects);
//        users.add(u1);
//        users.add(u2);
//        users.add(u3);
//        ArrayList<Schedule> schedules = new ArrayList<>();
//        Schedule s1 = new Schedule(0, "11:00", "13:00", 3, 0);
//        Schedule s2 = new Schedule(1, "11:00", "13:00", 4, 0);
//        Schedule s3 = new Schedule(2, "14:00", "18:00", 4, 0);
//        schedules.add(s1);
//        schedules.add(s2);
//        schedules.add(s3);
//        int[] exampleSchedules = {0, 1, 2};
//        int[] exampleTeachers = {3};
//        ArrayList<Subject> subjects = new ArrayList<>();
//        Subject sb1 = new Subject(0, "PDM", "TSIW", exampleSchedules , exampleTeachers);
//        subjects.add(sb1);
//
//        ArrayList<Classroom> classrooms = new ArrayList<>();
//        Classroom c1 = new Classroom(0, "B206", "IDBEACONXPTO");
//        classrooms.add(c1);
//
//
        // Get the current user


    }

}

