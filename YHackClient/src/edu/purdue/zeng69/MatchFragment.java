package edu.purdue.zeng69;

import java.io.Closeable;
import android.app.Fragment;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * This fragment is the "page" where the application display the log from the
 * server and wait for a match.
 *
 * @author zeng69
 * @author reed176
 */
public class MatchFragment extends Fragment implements OnClickListener {

    private TextView match;

    private TextView load1;
    private TextView load2;
    private TextView load3;

    private TextView partner;
    private TextView from;
    private TextView to;

    String result = "";


	private static final String DEBUG_TAG = "DEBUG";

	/**
	 * Activity which have to receive callbacks.
	 */
	private StartOverCallbackListener activity;

	/**
	 * AsyncTask sending the request to the server.
	 */
	private Client client;

	/**
	 * Coordinate of the server.
	 */
	private String host;
	private int port;

	/**
	 * Command the user should send.
	 */
	private String command;

	// TODO: your own class fields here

	// Class methods
	/**
	 * Creates a MatchFragment
	 * 
	 * @param activity
	 *            activity to notify once the user click on the start over
	 *            Button.
	 * @param host
	 *            address or IP address of the server.
	 * @param port
	 *            port number.
	 * 
	 * @param command
	 *            command you have to send to the server.
	 * 
	 * @return the fragment initialized.
	 */
	// TODO: you can add more parameters, follow the way we did it.
	// ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
	public static MatchFragment newInstance(StartOverCallbackListener activity,
			String host, int port, String command) {
		MatchFragment f = new MatchFragment();

		f.activity = activity;
		f.host = host;
		f.port = port;
		f.command = command;

		return f;
	}

	/**
	 * Called when the fragment will be displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		View view = inflater.inflate(R.layout.match_fragment_layout, container,
				false);

		/**
		 * Register this fragment to be the OnClickListener for the start over
		 * button.
		 */
		view.findViewById(R.id.bu_start_over).setOnClickListener(this);

        //we load them TextViews
        match = (TextView) view.findViewById(R.id.textView5);
        load1 = (TextView) view.findViewById(R.id.log1);
        load2 = (TextView) view.findViewById(R.id.log2);
        load3 = (TextView) view.findViewById(R.id.log3);
        partner = (TextView) view.findViewById(R.id.textView12);
        from = (TextView) view.findViewById(R.id.textView13);
        to =(TextView) view.findViewById(R.id.textView14);

		// TODO: import your Views from the layout here. See example in
		// ServerFragment.

		/**
		 * Launch the AsyncTask
		 */
		this.client = new Client();
		this.client.execute("");

		return view;
	}

	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		/**
		 * Close the AsyncTask if still running.
		 */
		this.client.close();

		/**
		 * Notify the Activity.
		 */
		this.activity.onStartOver();
	}

	class Client extends AsyncTask<String, String, String> implements Closeable {
        boolean timeout = false;
        Socket s;
		/**
		 * NOTE: you can access MatchFragment field from this class:
		 * 
		 * Example: The statement in doInBackground will print the message in
		 * the Eclipse LogCat view.
		 */

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		protected String doInBackground(String... params) {

			/**
			 * TODO: Your Client code here.
            try {
                Thread.sleep(1000);
                publishProgress("connect");
                Thread.sleep(1000);
                publishProgress("push");
                Thread.sleep(1000);
                publishProgress("match");
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
             */
            try {
                int timeout = 5000;
                InetSocketAddress inetAddress = new InetSocketAddress(host, port);
                s = new Socket();
                s.connect(inetAddress,timeout);
                publishProgress("wein");
                publishProgress("connect");
                if (!s.isConnected()){
                    return "failed";
                }
            }catch (Exception e){
                return "failed";
            }

            PrintWriter out;
            BufferedReader in;

            try  {
                out = new PrintWriter(s.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                if (timeout) s.setSoTimeout(500);
                out.println(command);
                publishProgress("push");

                result = in.readLine();
                publishProgress("match");
            }  catch (Exception e) {
                return "failed";
            }

            if (result.equals(null) || result.equals("")){
                return "failed";
            }

			Log.d(DEBUG_TAG, String
					.format("The Server at the address %s uses the port %d",
							host, port));
			Log.d(DEBUG_TAG, String.format(
					"The Client will send the command: %s", command));

            String res = result.substring(10);
			return res;
		}
		public void close() {
            try {
                s.close();
            } catch (Exception e){

            }
                    // TODO: Clean up the client
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */

		// TODO: use the following method to update the UI.
		// ** DO NOT TRY TO CALL UI METHODS FROM doInBackground!!!!!!!!!! **

		/**
		 * Method executed just before the task.
		 */
		@Override
		protected void onPreExecute() {

            match.setText("Please Wait");
            load1.setText("Loading");
            load2.setText("Loading");
            load3.setText("Loading");
            partner.setText("Loading");
            from.setText("Loading");
            to.setText("Loading");
            if (host.equals("10.0.2.2") || port == 8888){
                publishProgress("def");
            }
		}

		/**
		 * Method executed once the task is completed.
		 */
		@Override
		protected void onPostExecute(String result) {
            match.setText("We found a match!");
            try {
                String[] pieces = result.split(",");
                partner.setText(pieces[0]);
                from.setText(pieces[1]);
                to.setText(pieces[2]);
            } catch (Exception e) {
            }
            if (result.equals("failed")){
                match.setText("Server Error");
                load1.setText("Please");
                load2.setText("Enter a working");
                load3.setText("Server Setting");
            }
		}

		/**
		 * Method executed when progressUpdate is called in the doInBackground
		 * function.
		 */
		@Override
		protected void onProgressUpdate(String... result) {
            if (result[0].equals("connect")){
                load1.setText("We have connected to the sever!");
            }
            if (result[0].equals("push")){
                load2.setText("We have send you info to the sever!");
                load3.setText("Waiting for a match");
            }
            if (result[0].equals("match")) {
                load3.setText("We have found a match for you");
            }
            if (result[0].equals("error")) {
                load1.setText("Error connecting to the server");
            }
            if (result[0].equals("wein")) {
                load1.setText("We are in background");
            }
            if (result[0].equals("def")) {
                load1.setText("We are using default values for server or port");
                load2.setText("Click start over");
                load3.setText("if this is not intentional");
            }
		}
	}

}
