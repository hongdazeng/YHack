package edu.purdue.zeng69;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


/**
 * This fragment is the "page" where the user inputs information about the
 * request, he/she wishes to send.
 *
 * @author zeng69
 * @author reed176
 */
public class ClientFragment extends Fragment implements OnClickListener {
    private EditText name;
    private Spinner spinner;
    private Spinner spinner2;

    RadioButton rb0;
    RadioButton rb1;
    RadioButton rb2;


    /**
     * Activity which have to receive callbacks.
     */
    private SubmitCallbackListener activity;

    /**
     * Creates a ProfileFragment
     *
     * @param activity activity to notify once the user click on the submit Button.
     *                 <p/>
     *                 ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
     * @return the fragment initialized.
     */
    // ** DO NOT CREATE A CONSTRUCTOR FOR ProfileFragment **
    public static ClientFragment newInstance(SubmitCallbackListener activity) {
        ClientFragment f = new ClientFragment();

        f.activity = activity;
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

        View view = inflater.inflate(R.layout.client_fragment_layout,
                container, false);

        this.name = (EditText) view.findViewById(R.id.editText);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplication(),
                R.array.planets_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getApplication(),
                R.array.planets_array2, R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter2);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(5);


        rb0 = (RadioButton) view.findViewById(R.id.radioButton3);
        rb1 = (RadioButton) view.findViewById(R.id.radioButton);
        rb2 = (RadioButton) view.findViewById(R.id.radioButton2);

        /**
         * Register this fragment to be the OnClickListener for the submit
         * Button.
         */
        view.findViewById(R.id.bu_submit).setOnClickListener(this);

        // TODO: import your Views from the layout here. See example in
        // ServerFragment.

        return view;
    }

    public String getName(String defaultNam) {
        return this.name != null ? name.getText().toString() : defaultNam;
    }

    public int getChoice() {
        if (rb0.isChecked()) {
            return 0;
        } else if (rb1.isChecked()) {
            return 1;
        } else if (rb2.isChecked()) {
            return 2;
        } else {
            return 0;
        }
    }

    public String getFrom() {
        String valToSet = spinner.getSelectedItem().toString();
        if (valToSet.contentEquals("CL50: Class of 1950 Lecture Hall")) {
            return "CL50";
        } else if (valToSet.contentEquals("EE: Electrical Engineering Building")) {
            return "EE";
        } else if (valToSet.contentEquals("LWSN: Lawson Computer Science Building")) {
            return "LWSN";
        } else if (valToSet.contentEquals("PMU: Purdue Memorial Union")) {
            return "PMU";
        } else if (valToSet.contentEquals("PUSH: Purdue University Student Health Center")) {
            return "PUSH";
        } else {
            return "*";
        }
    }

    public String getTo() {
        String valToSet = spinner2.getSelectedItem().toString();
        if (valToSet.contentEquals("CL50: Class of 1950 Lecture Hall")) {
            return "CL50";
        } else if (valToSet.contentEquals("EE: Electrical Engineering Building")) {
            return "EE";
        } else if (valToSet.contentEquals("LWSN: Lawson Computer Science Building")) {
            return "LWSN";
        } else if (valToSet.contentEquals("PMU: Purdue Memorial Union")) {
            return "PMU";
        } else if (valToSet.contentEquals("PUSH: Purdue University Student Health Center")) {
            return "PUSH";
        } else {
            return "*";
        }
    }

    /**
     * Callback function for the OnClickListener interface.
     */
    @Override
    public void onClick(View v) {

        this.activity.onSubmit();
    }
}
