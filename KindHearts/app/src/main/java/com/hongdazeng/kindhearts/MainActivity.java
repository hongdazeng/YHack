package com.hongdazeng.kindhearts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import com.microsoft.windowsazure.mobileservices.*;

import java.net.MalformedURLException;

//added and mostly extraneous

import android.app.AlertDialog;
import android.os.AsyncTask;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText helpReason;
    private Button buttonHelp;
    private Button buttonRefresh;
    private Switch statusToggle;
    private MobileServiceClient mClient;

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Created by H A B & M at Yhack 2015 ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.helpReason = (EditText) findViewById(R.id.editText);
        this.name = (EditText) findViewById(R.id.editText2);
        this.buttonHelp = (Button) findViewById(R.id.button);
        this.buttonRefresh = (Button) findViewById(R.id.button2);
        this.statusToggle = (Switch) findViewById(R.id.switch1);

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Help item = new Help();
                item.Name = name.getText().toString();
                item.Request = helpReason.getText().toString();
                mClient.getTable(Help.class).insert(item, new TableOperationCallback<Help>() {
                    public void onCompleted(Help entity, Exception exception, ServiceFilterResponse response) {
                        if (exception == null) {
                            // Insert succeeded
                        } else {
                            // Insert failed
                        }
                    }
                });
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusToggle.isChecked()) {
                    //add user to helpers table
                    User user = new User();
                    user.Name = name.getText().toString();
                    user.Long = 1;
                    user.Lat = 2;
                    mClient.getTable(User.class).insert(user, new TableOperationCallback<User>() {
                        public void onCompleted(User entity, Exception exception, ServiceFilterResponse response) {
                            if (exception == null) {
                                // Insert succeeded
                            } else {
                                // Insert failed
                            }
                        }
                    });

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
                                        String body = "";
                                        for (int i = 0; i < 5 && i < result.size(); i++) {
                                            Help help = result.get(i);
                                            body += help.Name + ": " + help.Request + "\n";
                                        }
                                        createAndShowDialog(new Exception(body), "Help Requests");

//                                        getHelp(result.get(0));
//                                        mAdapter.clear();
//                                        for (ToDoItem item : result) {
//                                            mAdapter.add(item);
//                                        }
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

        } catch (MalformedURLException e) {
            createAndShowDialog(new Exception("There was an error creating the Mobile Service. Verify the URL"), "Error");
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }

        createAndShowDialog(new Exception("Welcome to KindHearts!"), "KindHearts");

        // User newuser = User(getname(), getmessage(), help, range)
        // Client newClient = new Client(User)
        // newClient.getserver()
    }

    private void yell(String message) {
        createAndShowDialog(new Exception(message), message);
    }

    public String getName() {
        return this.name != null ? name.getText().toString() : "John";
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
