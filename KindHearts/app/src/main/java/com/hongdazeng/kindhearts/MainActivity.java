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

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText helpReason;
    private Button buttonHelp;
    private Button buttonRefresh;
    private Switch statusToggle;


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


        // User newuser = User(getname(), getmessage(), help, range)
        // Client newClient = new Client(User)
        // newClient.getserver()
    }

    public String getName() {
        return this.name != null ? name.getText().toString() : "John";
    }

    public String getMessage() {
        return this.helpReason != null ? helpReason.getText().toString() : "need help";
    }

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
}
