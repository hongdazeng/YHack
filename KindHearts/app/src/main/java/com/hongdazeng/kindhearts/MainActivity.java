package com.hongdazeng.kindhearts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import com.microsoft.windowsazure.mobileservices.*;

import java.net.MalformedURLException;
import java.util.ArrayList;

//added and mostly extraneous

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import com.microsoft.windowsazure.notifications.NotificationsManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText helpReason;
    private ImageButton buttonHelp;
    private ImageButton buttonRefresh;
    private Switch statusToggle;
    private ListView list;
    public static MobileServiceClient mClient;
    private MobileServiceTable<User> UserTable;
    private MobileServiceTable<Help> HelpRequestTable;
//    private ToDoItemAdapter mAdapter;

    public static final String SENDER_ID = "1085437748397";

    private String myName;

    Help someHelp;


    public class Help {
        public String Id;
        public String Name;
        public String Request;
    }

    public class User {
        public String Id;
        public String Name;
        public int Long;
        public int Lat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Log.d("MyApp", "hi there");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Created by H A B & M at Yhack 2015 ", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        this.helpReason = (EditText) findViewById(R.id.editText3);
        this.name = (EditText) findViewById(R.id.editText2);
        this.buttonHelp = (ImageButton) findViewById(R.id.imageButton);
        this.buttonRefresh = (ImageButton) findViewById(R.id.imageButton4);
        this.statusToggle = (Switch) findViewById(R.id.switch2);
        this.list = (ListView) findViewById(R.id.listView);

        if (Math.random() > .5) {
            myName = "John White";
        }
        else {
            myName = "Ben Jones";
        }

        ((TextView)findViewById(R.id.textView8)).setText("Hello, " + myName);

//        mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Help item = new Help();
//                item.Name = name.getText().toString();
                item.Name = myName;
                item.Request = helpReason.getText().toString();
                HelpRequestTable.insert(item);
                createAndShowDialog(new Exception("   "), "Request Submitted");
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusToggle.isChecked()) {
                    //add user to helpers table
                    User user = new User();
//                    user.Name = name.getText().toString();
                    user.Name = myName;
                    user.Long = 1;
                    user.Lat = 2;
                    UserTable.insert(user);

                    final ArrayList<Help> helpList = new ArrayList<Help>();

                    //dislpay users who need help
                    new AsyncTask<Void, Void, MobileServiceList<Help>>() {

                        MobileServiceList<Help> result;

                        @Override
                        protected MobileServiceList<Help> doInBackground(Void... params) {
                            try {

                                result = mClient.getTable(Help.class).execute().get();
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //add it to list readable from outside
                                        for (Help h : result) {
                                            helpList.add(h);
                                        }
                                        //show in dialogue
                                        String body = "";
                                        for (int i = 0; i < 5 && i < result.size(); i++) {
                                            Help help = result.get(i);
                                            body += "- " + help.Name + ": " + help.Request + "\n\n";
                                        }
                                        createAndShowDialog(new Exception(body), "Help Requests");
                                    }
                                });
                            } catch (Exception exception) {
                                createAndShowDialog(exception, "Error");
                            }
                            return result;
                        }
                    }.execute();
                } else {
                    //not available
                }
            }


        });

        try {
            mClient = new MobileServiceClient(
                    "https://kh-helper.azure-mobile.net/",
                    "mjFiRdonjvSmmGdVUaEtMcLGoNCkrq37",
                    this
            );

            UserTable = mClient.getTable(User.class);
            HelpRequestTable = mClient.getTable(Help.class);

            NotificationsManager.handleNotifications(this, SENDER_ID, MyHandler.class);

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }

        createAndShowDialog(new Exception("Welcome to KindHeart!"), "KindHeart");

        // User newuser = User(getname(), getmessage(), help, range)
        // Client newClient = new Client(User)
        // newClient.getserver()
    }

    private void yell(String message) {
        createAndShowDialog(new Exception(message), message);
    }

    public String getName() {
//        return this.name != null ? name.getText().toString() : "John";
        return myName;
    }

    public void getHelp(Help a){
        someHelp = a;
    }

    public String getMessage() {
        return this.helpReason != null ? helpReason.getText().toString() : "need help";
    }

    //redundant
    public void onClick(View v) {

        if (v == buttonHelp) {
            // call server update with change

        } else if (v == buttonRefresh) {
            // call sever update with change
        } else if (v == statusToggle) {
            // call server update with change
        }
    }


    //@Override
    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttonHelp, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     *
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */
    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
}
