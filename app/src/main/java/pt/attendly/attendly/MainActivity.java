package pt.attendly.attendly;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Card;
import pt.attendly.attendly.model.Classroom;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Schedule;
import pt.attendly.attendly.model.Subject;
import pt.attendly.attendly.model.User;

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
    private static boolean data = false;

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

    public static boolean checkIfTimeOfClass(String classBegining, String classEnd) {
        boolean time = false;
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        Date begining = new Date();
        String[] tempArray = classBegining.split(":");
        begining.setHours(Integer.parseInt(tempArray[0]));
        begining.setMinutes(Integer.parseInt(tempArray[1]));
        begining.setSeconds(0);

        Date now = new Date();

        Date tolerance = new Date(begining.getTime() + 15 * 60 * 1000);

        android.util.Log.d("tolerance", String.valueOf(tolerance));

        if (begining.before(now) && tolerance.after(now)) {
            time = true;
        }
        return time;
    }

    public static boolean checkIfClassOpen(String classBegining, String classEnd) {
        boolean open = false;
        android.util.Log.d("enter", "enter");

        ArrayList<Log> arrayLogs = manageData.logs;
        ArrayList<Schedule> arraySchedule = manageData.schedules;
        ArrayList<User> arrayListUsers = manageData.users;
        ArrayList<Log> tempUsersClass;


        for (int i = 0; i < arrayLogs.size(); i++) {
            for (int j = 0; j < arrayListUsers.size(); j++) {
                // Verificar se o profesor já abriu a aula
                if (arrayLogs.get(i).getId_user().equals(arrayListUsers.get(j).getId())) {
                    android.util.Log.d("user", arrayListUsers.get(j).getId());
                    // verificar os registos dos profs
                    if (arrayListUsers.get(j).getType() == 1) {
                        android.util.Log.d("user2", arrayListUsers.get(j).getId());
                        // Qual é a cadeira que foi registada
                        for (int k = 0; k < arrayListUsers.get(j).getSubjects().size(); k++) {
                            // verificar se está na lista as cadeiras dos prof
                            if (arrayLogs.get(i).getId_subject() == (arrayListUsers.get(j).getSubjects().get(k))) {
                                // verificar a que horas foi feito o registo
                                Date begining = new Date();
                                String[] tempArray = classBegining.split(":");
                                begining.setHours(Integer.parseInt(tempArray[0]));
                                begining.setMinutes(Integer.parseInt(tempArray[1]));
                                begining.setSeconds(0);

                                Date tolerance = new Date(begining.getTime() + 15 * 60 * 1000);

                                String dateLog = arrayLogs.get(i).getDate();
                                Date log = ManagerActivity.parseDate(dateLog);
                                Date now = new Date();
                                android.util.Log.d("user4", String.valueOf(log));
                                android.util.Log.d("user5", String.valueOf(now));
                                android.util.Log.d("user6", cards.get(0).getSubjectBeginning());

                                if (log.after(begining) && log.before(tolerance)) {
                                    open = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        android.util.Log.d("bl1", String.valueOf(open));
        return open;
    }

    public static boolean checkBluetooth(String classBegining, String bluetoothId) {
        boolean bluetooth = false;
        android.util.Log.d("enter", "enter");

        ArrayList<Log> arrayLogs = manageData.logs;
        ArrayList<Schedule> arraySchedule = manageData.schedules;
        ArrayList<User> arrayListUsers = manageData.users;
        ArrayList<Log> tempUsersClass;


        for (int i = 0; i < arrayLogs.size(); i++) {

            if (arrayLogs.get(i).getId_bluetooth().equals(bluetoothId)) {

                // verificar a que horas foi feito o registo
                Date begining = new Date();
                String[] tempArray = classBegining.split(":");
                begining.setHours(Integer.parseInt(tempArray[0]));
                begining.setMinutes(Integer.parseInt(tempArray[1]));
                begining.setSeconds(0);

                Date tolerance = new Date(begining.getTime() + 15 * 60 * 1000);
                String dateLog = arrayLogs.get(i).getDate();
                Date log = ManagerActivity.parseDate(dateLog);

                if (log.after(begining) && log.before(tolerance)) {
                    bluetooth = true;
                }

            }

        }
        android.util.Log.d("bl", String.valueOf(bluetooth));

        return bluetooth;
    }

    public static boolean missClass(String classBegining, String userId) {
        boolean miss = false;
        android.util.Log.d("enter", "enter");

        ArrayList<Log> arrayLogs = manageData.logs;
        ArrayList<Schedule> arraySchedule = manageData.schedules;
        ArrayList<User> arrayListUsers = manageData.users;
        ArrayList<Log> tempUsersClass;


        for (int i = 0; i < arrayLogs.size(); i++) {
            if (arrayLogs.get(i).getId_user().equals(userId)) {
                // verificar a que horas foi feito o registo
                Date begining = new Date();
                String[] tempArray = classBegining.split(":");
                begining.setHours(Integer.parseInt(tempArray[0]));
                begining.setMinutes(Integer.parseInt(tempArray[1]));
                begining.setSeconds(0);

                Date tolerance = new Date(begining.getTime() + 15 * 60 * 1000);
                String dateLog = arrayLogs.get(i).getDate();
                Date log = ManagerActivity.parseDate(dateLog);
                if (log.after(begining) && log.before(tolerance)) {
                    if (arrayLogs.get(i).getPresence() == 0) {
                        miss = true;
                    }
                }
            }
        }
        android.util.Log.d("bl2", String.valueOf(miss));
        return miss;
    }


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                android.util.Log.d(TAG, "BEACONS" + beacons);
                if (beacons.size() > 0) {
                    String bluetooth = getBluetoothMacAddress();

                    for (Beacon beacon : beacons) {
                        // Obter Beacons que estão a menos de 2.5m
                        if (beacon.getDistance() <= 2.5) {
                            // Obter o macaddress do beacon ; verificar qual é o mac address da sala da proxima aula ; comparar os dos mac address
                            // verificar se o beacon detetado corresponde com o da sala
                            if (beacons.iterator().next().getBluetoothAddress().equals("C1:BA:B4:A2:1C:B3")) {
                                // verificar se prof abriu a aula

                                if (data == true) {
                                    if (cards.size() > 0) {
                                        String begining = cards.get(0).getSubjectBeginning();
                                        String end = cards.get(0).getSubjectEnding();
                                        // Verificar se está na hora da aula
                                        if (checkIfTimeOfClass(begining, end) == true) {

                                            // Verificar se a aula está aberta
                                            if (checkIfClassOpen(begining, end) == true) {

                                                // verificar se o bluethooth não foi registo naquela aula
                                                if (checkBluetooth(begining, bluetooth) == false) {

                                                    //verificar se não tem falta
                                                    if (missClass(begining, LoginActivity.loggedUser.getId()) == false) {
                                                        // registar
                                                        Date date = new Date();
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                        String dataFormated = simpleDateFormat.format(date);

                                                        String idUser = LoginActivity.loggedUser.getId();
                                                        // obter o id da aula
                                                        int idSubject= cards.get(0).getSubjectId();
                                                        // obter o id aula
                                                        int idClass= cards.get(0).getSubjectClassroomID();
                                                        // obter o id do horario
                                                        int idSchedule= cards.get(0).getSubjectSchedule();
                                                        // obter a data atual
                                                        Date currentDate = new Date();
                                                        Calendar c = Calendar.getInstance();
                                                        c.setTime(currentDate);
                                                        // obter o dia da semana
                                                        int day = c.get(Calendar.DAY_OF_WEEK);

                                                        // Registar na BD
                                                        Log log = new Log(idUser, bluetooth, idSubject, dataFormated, day, 1, idClass, idSchedule);
                                                        manageData.addLog(log);
                                                        // Enviar notificação

                                                        // Comunicação do resultado via notificações
                                                        NotificationCompat.Builder mBuilder =
                                                                new NotificationCompat.Builder(getApplicationContext())
                                                                        .setSmallIcon(R.mipmap.ic_launcher)
                                                                        .setContentTitle("Attendly")
                                                                        .setContentText("Presença Registada com Sucesso");
                                                        NotificationManager nm = (NotificationManager)
                                                                getSystemService(NOTIFICATION_SERVICE);
                                                        nm.notify(1, mBuilder.build());
                                                        //  Alterar cor logo na card
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                android.util.Log.i(TAG2, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                                android.util.Log.i(TAG, "The beacon " + beacons.iterator().next().getBluetoothAddress() + " bluetooth");
                            }
                        }
                        android.util.Log.i(TAG, "BT Device" + getBluetoothMacAddress());
                    }
                }
            }
        });

        try

        {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (
                RemoteException e)

        {
        }
    }

    public void openHistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openManagerClass(View view) {
        Intent intent = new Intent(this, ManagerActivity.class);
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
    public static void getCurrentCard() {

        data = false;
        // Load the data
        User currentUser = LoginActivity.loggedUser;
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
        int subjectSchedule = -1;
        int subject_id = 0;
        int classroom_id = 0;

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
                subjectSchedule = userSchedules.get(i).getId();

                int tempClassroomID = userSchedules.get(i).getClassroom();

                for (int j = 0; j < classrooms.size(); j++) {
                    if (classrooms.get(j).getId() == tempClassroomID) {
                        subjectClassroom = classrooms.get(j).getName();
                        classroom_id = classrooms.get(j).getId();
                        subjectBeacon = classrooms.get(j).getId_beacon();
                    }
                }

                int tempScheduleID = userSchedules.get(i).getId();

                for (int j = 0; j < subjects.size(); j++) {

                    for (int k = 0; k < subjects.get(j).getSchedules().size(); k++) {
                        int tempID = subjects.get(j).getSchedules().get(k);

                        if (tempID == tempScheduleID) {
                            subjectName = subjects.get(j).getName();
                            subject_id = subjects.get(j).getId();
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
        // If the number of classes is the same than the classes that already happend then there aren't more classes on that day
        if (totalClasses == pastClasses) {
            android.util.Log.d("count", String.valueOf(pastClasses));
            android.util.Log.d("count1", String.valueOf(totalClasses));
            android.util.Log.d("count", "No more classes today");
            aula.setText("Não tem aula");
        } else {
            android.util.Log.d("card2", String.valueOf(read));
            cards.clear();
            Card card = new Card(subjectBeginning, subjectEnding, subjectClassroom, subjectName, subjectBeacon, subjectCourse, subjectSchedule, subject_id, classroom_id);
            cards.add(card);
            aula.setText(cards.get(0).getSubjectName());

            String begining = cards.get(0).getSubjectBeginning();
            String end = cards.get(0).getSubjectEnding();
            android.util.Log.d("bool", String.valueOf(checkIfTimeOfClass(begining, end)));

            try {
                verifyPresence();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        read = true;
        data = true;
//        updateCardTimer.start();
    }

    private static void verifyPresence() throws ParseException {

        boolean runTimer = true;

        final ArrayList<Log> logs = manageData.logs;

        boolean logRegistered = false;
        int presence = 0;

        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormated = simpleDateFormat.format(currentDate);
        String startTime = cards.get(0).getSubjectBeginning();

        for (Log log : logs) {

            String[] date = log.getDate().split(" ");

//            android.util.Log.d("TIMEDONE", date[0]);
//            android.util.Log.d("TIMEDONE", dataFormated);
//            android.util.Log.d("TIMEDONE", log.getId_user());
//            android.util.Log.d("TIMEDONE", LoginActivity.loggedUser.getId());
//            android.util.Log.d("TIMEDONE", String.valueOf(log.getId_schedule()));
//            android.util.Log.d("TIMEDONE", String.valueOf(cards.get(0).getSubjectSchedule()));
//            android.util.Log.d("TIMEDONE", String.valueOf(log.getId_subject()));
//            android.util.Log.d("TIMEDONE", String.valueOf(cards.get(0).getSubjectId()));

            if (date[0].equals(dataFormated)
                    && log.getId_user().equals(LoginActivity.loggedUser.getId())
                    && log.getId_schedule() == cards.get(0).getSubjectSchedule()
                    && log.getId_subject() == cards.get(0).getSubjectId()) {
                android.util.Log.d("TIMEDONE", "AULA ENCONTRADA");
                logRegistered = true;
                presence = log.getPresence();

            }
        }

        if (logRegistered) {
            if (presence == 1) {
                //PUT GREEN LOGO AND DO WHATEVER
                android.util.Log.d("TIMEDONE", "GREEN");
                runTimer = false;
            } else {
                //PUT RED LOGO AND DO WHATEVER
                android.util.Log.d("TIMEDONE", "RED");
                runTimer = false;
            }
        } else {
            //PUT ORANGE LOGO AND DO WHATEVER

            android.util.Log.d("TIMEDONE", "ORANGE");
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");

            Date classTime = df.parse(startTime);


            Calendar cal = Calendar.getInstance();
            cal.setTime(classTime);
            cal.add(Calendar.MINUTE, 15);

            String absenceTimeTemp = df.format(cal.getTime());
            Date absenceTime = df.parse(absenceTimeTemp);


            String currentTimeOnly = String.valueOf(currentDate.getHours()) + ":" + String.valueOf(currentDate.getMinutes());
            Date currentTimeHOURS = df.parse(currentTimeOnly);
            cal.setTime(currentTimeHOURS);
            String temp = df.format(cal.getTime());

            Date nowTime = df.parse(temp);
            long elapsed = absenceTime.getTime() - nowTime.getTime();

            android.util.Log.d("TIMEDONE", String.valueOf(absenceTime.getTime()));
            android.util.Log.d("TIMEDONE", String.valueOf(nowTime.getTime()));
            android.util.Log.d("TIMEDONE", String.valueOf(elapsed));
            elapsed = -1;
            if (elapsed <= 0) {
                runTimer = false;

                //REGISTER ABSENCE
                Date currentDate2 = new Date();
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                String date = simpleDateFormat2.format(currentDate2);
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate2);
                int day_week = c.get(Calendar.DAY_OF_WEEK);

                int subject_id = 0;
                int schedule_id = 0;
                int classroom_id = 0;
                for (Card card : cards) {
                    subject_id = card.getSubjectId();
                    schedule_id = card.getSubjectSchedule();
                    classroom_id = card.getSubjectClassroomID();
                }

                Log log = new Log(LoginActivity.loggedUser.getId(), "", subject_id, date, day_week, 0, classroom_id, schedule_id);
//                manageData.addLog(log);
            }
            android.util.Log.d("TIMEDONE", String.valueOf(runTimer));
//            if(runTimer)
//            {
//                checkAbsenceTimer.start();
//            }
//            else
//            {
//                checkAbsenceTimer.cancel();
//            }


        }

    }


//    static CountDownTimer checkAbsenceTimer = new CountDownTimer(60000 * 1, 60000) {
//        @Override
//        public void onTick(long l) {
//            android.util.Log.d("TIMEDONE", "ABSENCE TIMER RUNNING");
//        }
//
//        @Override
//        public void onFinish() {
//
//            try {
//                verifyPresence();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            start();
//        }
//    };
//
//    static CountDownTimer updateCardTimer = new CountDownTimer(60000 * 1, 60000 * 1) {
//        @Override
//        public void onTick(long l) {
//            android.util.Log.d("TIMEDONE", "CARD TIMER RUNNING");
//        }
//
//        @Override
//        public void onFinish() {
//
//            getCurrentCard();
//
//            start();
//        }
//    };


    //DISABLE BACK BUTTON PRESS TO PREVIOUS ACTIVITY
    @Override
    public void onBackPressed() {
//        this.finishAffinity();
    }


}

