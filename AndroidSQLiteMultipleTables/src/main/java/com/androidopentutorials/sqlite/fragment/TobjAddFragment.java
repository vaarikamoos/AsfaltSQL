package com.androidopentutorials.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidopentutorials.sqlite.R;
import com.androidopentutorials.sqlite.db.TeeObjektDAO;
import com.androidopentutorials.sqlite.to.TeeObjekt;

public class TobjAddFragment extends Fragment implements OnClickListener {

    private EditText tobjObjNimetusEtxt;
    private EditText tobjTeeNimetusEtxt;
    private EditText tobjTeeNrEtxt;
    private EditText tobjAlgusKmEtxt;
    private EditText tobjLoppKmEtxt;

    private Button lisaButton;
    private Button tyhjendaButton;

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    TeeObjekt teeObjekt = null;
    private TeeObjektDAO teeObjektDAO;
    private AddTobjTask addTobjTask;

    public static final String ARG_ITEM_ID = "tobj_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teeObjektDAO = new TeeObjektDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_uus_objekt, container,
                false);

        findViewsById(rootView);

        setListeners();

        return rootView;
    }

    private void setListeners() {

        lisaButton.setOnClickListener(this);
        tyhjendaButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        tobjObjNimetusEtxt.setText("");
        tobjTeeNimetusEtxt.setText("");
        tobjAlgusKmEtxt.setText("");
        tobjLoppKmEtxt.setText("");
        tobjTeeNrEtxt.setText("");
    }

    private void setTeeObjekt() {
        teeObjekt = new TeeObjekt();
        teeObjekt.setObjektiNimetus(tobjObjNimetusEtxt.getText().toString());
        teeObjekt.setTeeNimetus((tobjTeeNimetusEtxt.getText().toString()));
        teeObjekt.setAlgusKm(Double.parseDouble(tobjAlgusKmEtxt.getText().toString()));
        teeObjekt.setLoppKm(Double.parseDouble(tobjLoppKmEtxt.getText().toString()));
        teeObjekt.setTeeNr(Double.parseDouble(tobjTeeNrEtxt.getText().toString()));

    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_tobj);
        getActivity().getActionBar().setTitle(R.string.add_tobj);
        super.onResume();
    }

    private void findViewsById(View rootView) {
        tobjObjNimetusEtxt = (EditText) rootView.findViewById(R.id.etxt_ojbNimetus);
        tobjTeeNimetusEtxt = (EditText) rootView.findViewById(R.id.etxt_teeNimetus);
        tobjTeeNrEtxt = (EditText) rootView.findViewById(R.id.etxt_teeNumber);
        tobjAlgusKmEtxt = (EditText) rootView.findViewById(R.id.etxt_algusKm);
        tobjLoppKmEtxt = (EditText) rootView.findViewById(R.id.etxt_loppKm);

        lisaButton = (Button) rootView.findViewById(R.id.button_lisa);
        tyhjendaButton = (Button) rootView.findViewById(R.id.button_tyhjenda);
    }

    @Override
    public void onClick(View view) {
         if (view == lisaButton) {
            setTeeObjekt();
            addTobjTask = new AddTobjTask(getActivity());
            addTobjTask.execute((Void) null);
        } else if (view == tyhjendaButton) {
            resetAllFields();
        }
    }

    public class AddTobjTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddTobjTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = teeObjektDAO.save(teeObjekt);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Uus objekt on salvestatud ",
                            Toast.LENGTH_LONG).show();
            }
        }
    }














}
