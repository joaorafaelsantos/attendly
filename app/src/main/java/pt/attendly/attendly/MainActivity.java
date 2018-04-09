package pt.attendly.attendly;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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


import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

import pt.attendly.attendly.model.Log;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    protected static final String TAG = "MonitoringActivity";
    protected static final String TAG2 = "MonitoringActivity2";
    private BeaconManager beaconManager;
    private String BluetoothMacAddressUser, beaconSala;
    private boolean presença, tempo, classOpen;
    static boolean read = false;

    static ArrayList<Card> cards = new ArrayList<>();
    static TextView aula;
    TextView sala;
    CardView cardView;

    static boolean subjectExists = false, noClass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aula = findViewById(R.id.txtClass);
        cardView = findViewById(R.id.cardView);

        manageData.getMainActivityData();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);


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
                android.util.Log.d(TAG, "BEACONS" + beacons);
                if (beacons.size() > 0) {

                    if (cards.size() > 0) {
                        android.util.Log.d("card", String.valueOf(cards.get(0).getSubjectName()));
                    }
                    for (Beacon beacon : beacons) {
                        // Obter Beacons que estão a menos de 2.5m
                        if (beacon.getDistance() <= 2.5) {
                            // Obter o macaddress do beacon ; verificar qual é o mac address da sala da proxima aula ; comparar os dos mac address
                            // verificar se o beacon detetado corresponde com o da sala
                            if (beacons.iterator().next().getBluetoothAddress() == beaconSala) {
                                // verificar se prof abriu a aula

//                                Log l= new Log(String id, String id_user, String id_bluetooth, int id_subject, String date, int day_week, int presence, int id_classroom);

                                //manageData.write("Log", );
//                                if(classOpen== true){
//
//
//
//                                    // se coincidirem  verificar se já tiver falta não pode marcar presença
//                                    if(presença== false){
//
//                                        // caso não tenha falta verificar se está dentro do tempo para marcar presença, caso contrário não faz nada
//                                        if (tempo   == true ){
//
//                                            // registar na base de dados a presença
//                                            // alterar a card principal
//                                        }
//                                    }
//                                }
                            }
                            android.util.Log.i(TAG2, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                            android.util.Log.i(TAG, "The beacon " + beacons.iterator().next().getBluetoothAddress() + " bluetooth");
                        }
                    }
                    android.util.Log.i(TAG, "BT Device" + getBluetoothMacAddress());
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }
    }


    public void openTeacherActivity(View view) {
        Intent intent = new Intent(this, MainTeacherActivity.class);
        startActivity(intent);
    }

    public void openHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private String getBluetoothMacAddress() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String bluetoothMacAddress = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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

    // Method to return the current card (with the current or the next subject of that day)
    public static void getCurrentCard(User currentUser) {
        // Load the data
        final ArrayList<User> users = manageData.users;
        final ArrayList<Schedule> schedules = manageData.schedules;
        final ArrayList<Subject> subjects = manageData.subjects;
        final ArrayList<Classroom> classrooms = manageData.classrooms;

        // Get the current date (day, hour and minute)
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        int day = c.get(Calendar.DAY_OF_WEEK);

        // Get all the schedules of the current user
        ArrayList<Integer> userSubjects = currentUser.getSubjects();
        ArrayList<Integer> userSchedulesID = new ArrayList<>();

        for (int i = 0; i < subjects.size(); i++) {
            Subject tempSubject = subjects.get(i);
            int tempSubjectID = subjects.get(i).getId();

            for (int j = 0; j < userSubjects.size(); j++) {
                int tempUserSubjectID = userSubjects.get(j);

                if (tempSubjectID == tempUserSubjectID) {
                    for (int k = 0; k < tempSubject.getSchedules().size(); k++) {
                        int tempScheduleID = tempSubject.getSchedules().get(k);
                        userSchedulesID.add(tempScheduleID);
                    }
                }
            }
        }

        ArrayList<Schedule> userSchedules = new ArrayList<>();
        for (int i = 0; i < userSchedulesID.size(); i++) {

            for (int j = 0; j < schedules.size(); j++) {
                if (schedules.get(j).getId() == userSchedulesID.get(i)) {
                    userSchedules.add(schedules.get(j));

                }
            }
        }

        // Card variables
        String subjectBeginning = "";
        String subjectEnding = "";
        String subjectClassroom = "";
        String subjectName = "";
        String subjectBeacon = "";
        String subjectCourse = "";

        // System flag variables
        int totalClasses = 0, pastClasses = 0;
        noClass = false;

        // Search if the current user has classes on the current day
        for (int i = 0; i < userSchedules.size(); i++) {

            // On the current day the user has classes
            if (day == userSchedules.get(i).getDay_week()) {
                // Increment the number of classes of the current user
                totalClasses++;

                // Add the data to the card variables
                subjectBeginning = userSchedules.get(i).getBeginning();
                subjectEnding = userSchedules.get(i).getEnding();

                int tempClassroomID = userSchedules.get(i).getClassroom();

                for (int j = 0; j < classrooms.size(); j++) {
                    if (classrooms.get(j).getId() == tempClassroomID) {
                        subjectClassroom = classrooms.get(j).getName();
                        subjectBeacon = classrooms.get(j).getId_beacon();
                    }
                }

                int tempScheduleID = userSchedules.get(i).getId();

                for (int j = 0; j < subjects.size(); j++) {

                    for (int k = 0; k < subjects.get(j).getSchedules().size(); k++) {
                        int tempID = subjects.get(j).getSchedules().get(k);

                        if (tempID == tempScheduleID) {
                            subjectName = subjects.get(j).getName();
                            subjectCourse = subjects.get(j).getCourse();
                        }
                    }
                }

                // Get the subject date
                Date subjectDate = new Date();
                String[] tempArray = subjectEnding.split(":");
                subjectDate.setHours(Integer.parseInt(tempArray[0]));
                subjectDate.setMinutes(Integer.parseInt(tempArray[1]));
                subjectDate.setSeconds(0);

                // If the subject date already happened (based on the current date) then increments the past classes variable
                if (subjectDate.before(currentDate)) {
                    pastClasses++;
                }

                // If the subject date didn't happened yet (based on the current date) increments the past classes variable, activate the flag of existent subject and finish the cycle
                if (subjectDate.after(currentDate)) {

                    subjectExists = true;
                    android.util.Log.d("XPTO", subjectBeginning);
                    android.util.Log.d("XPTO", subjectEnding);
                    android.util.Log.d("XPTO", subjectClassroom);
                    android.util.Log.d("XPTO", subjectName);
                    android.util.Log.d("XPTO", subjectBeacon);
                    android.util.Log.d("XPTO", subjectCourse);
                    break;
                }
            }
        }

        // If the number of classes is the same than the classes that already happend then there aren't more classes on that day
        if (totalClasses == pastClasses) {
            android.util.Log.d("count", String.valueOf(pastClasses));
            android.util.Log.d("count1", String.valueOf(totalClasses));
            android.util.Log.d("count", "No more classes today");
        }
        read = true;
        android.util.Log.d("card2", String.valueOf(read));
        cards.clear();
        Card card = new Card(subjectBeginning, subjectEnding, subjectClassroom, subjectName, subjectBeacon, subjectCourse);
        cards.add(card);
        aula.setText(cards.get(0).getSubjectName());

    }


    //DISABLE BACK BUTTON PRESS TO PREVIOUS ACTIVITY
    @Override
    public void onBackPressed() {
//        this.finishAffinity();
    }


}

