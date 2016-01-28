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
import com.androidopentutorials.sqlite.db.TeeProovDAO;
import com.androidopentutorials.sqlite.to.TeeObjekt;
import com.androidopentutorials.sqlite.to.TeeProov;

public class TprAddFragment extends Fragment implements OnClickListener {

	// UI references
	private EditText tprNameEtxt;
	private EditText tprNumberEtxt;
	private EditText tprKuupaevEtxt;
	private EditText tprVotuKohtEtxt;

	private Spinner tobjSpinner;
	private Button addButton;
	private Button resetButton;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	DatePickerDialog datePickerDialog;
	Calendar dateCalendar;

	TeeProov teeProov = null;
	private TeeProovDAO teeProovDAO;
	private TeeObjektDAO teeObjektDAO;
	private GetTobjTask task;
	private AddTprTask addTprTask;

	public static final String ARG_ITEM_ID = "tpr_add_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		teeProovDAO = new TeeProovDAO(getActivity());
		teeObjektDAO = new TeeObjektDAO(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_tpr, container,
				false);

		findViewsById(rootView);

		setListeners();

		// Used for orientation change
		/*
		 * After entering the fields, change the orientation.
		 * NullPointerException occurs for date. This piece of code avoids it.
		 */
		if (savedInstanceState != null) {
			dateCalendar = Calendar.getInstance();
			if (savedInstanceState.getLong("dateCalendar") != 0)
				dateCalendar.setTime(new Date(savedInstanceState
						.getLong("dateCalendar")));
		}

		// asynchronously retrieves department from table and sets it in Spinner
		task = new GetTobjTask(getActivity());
		task.execute((Void) null);

		return rootView;
	}

	private void setListeners() {
		tprKuupaevEtxt.setOnClickListener(this);
		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(getActivity(),
				new OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						dateCalendar = Calendar.getInstance();
						dateCalendar.set(year, monthOfYear, dayOfMonth);
						tprKuupaevEtxt.setText(formatter.format(dateCalendar
								.getTime()));
					}

				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));

		addButton.setOnClickListener(this);
		resetButton.setOnClickListener(this);
	}

	protected void resetAllFields() {
		tprNameEtxt.setText("");
		tprNumberEtxt.setText("");
		tprKuupaevEtxt.setText("");
		tprVotuKohtEtxt.setText("");
		if (tobjSpinner.getAdapter().getCount() > 0)
			tobjSpinner.setSelection(0);
	}

	private void setTeeProov() {
		teeProov = new TeeProov();
		teeProov.setName(tprNameEtxt.getText().toString());
		teeProov.setProoviNr(Double.parseDouble(tprNumberEtxt.getText()
				.toString()));

		teeProov.setPrVotuKoht(tprVotuKohtEtxt.getText().toString());

		if (dateCalendar != null)
			teeProov.setProoviKp(dateCalendar.getTime());
		TeeObjekt selectedDept = (TeeObjekt) tobjSpinner.getSelectedItem();
		teeProov.setTeeObjekt(selectedDept);
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.add_emp);
		getActivity().getActionBar().setTitle(R.string.add_emp);
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (dateCalendar != null)
			outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
	}

	private void findViewsById(View rootView) {
		tprNameEtxt = (EditText) rootView.findViewById(R.id.etxt_name);
		tprNumberEtxt = (EditText) rootView.findViewById(R.id.etxt_tprNumber);
		tprKuupaevEtxt = (EditText) rootView.findViewById(R.id.etxt_tprKuupaev);
		tprKuupaevEtxt.setInputType(InputType.TYPE_NULL);

		tprVotuKohtEtxt = (EditText) rootView.findViewById(R.id.etxt_tprVotuKoht);

		tobjSpinner = (Spinner) rootView.findViewById(R.id.spinner_dept);
		addButton = (Button) rootView.findViewById(R.id.button_add);
		resetButton = (Button) rootView.findViewById(R.id.button_reset);
	}

	@Override
	public void onClick(View view) {
		if (view == tprKuupaevEtxt) {
			datePickerDialog.show();
		} else if (view == addButton) {
			setTeeProov();
			addTprTask = new AddTprTask(getActivity());
			addTprTask.execute((Void) null);
		} else if (view == resetButton) {
			resetAllFields();
		}
	}

	public class GetTobjTask extends AsyncTask<Void, Void, Void> {

		private final WeakReference<Activity> activityWeakRef;
		private List<TeeObjekt> teeObjekts;

		public GetTobjTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			teeObjekts = teeObjektDAO.getTeeObjektid();
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {

				ArrayAdapter<TeeObjekt> adapter = new ArrayAdapter<TeeObjekt>(
						activityWeakRef.get(),
						android.R.layout.simple_list_item_1, teeObjekts);
				tobjSpinner.setAdapter(adapter);

				addButton.setEnabled(true);
			}
		}
	}

	public class AddTprTask extends AsyncTask<Void, Void, Long> {

		private final WeakReference<Activity> activityWeakRef;

		public AddTprTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected Long doInBackground(Void... arg0) {
			long result = teeProovDAO.save(teeProov);
			return result;
		}

		@Override
		protected void onPostExecute(Long result) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				if (result != -1)
					Toast.makeText(activityWeakRef.get(), "TeeProov Saved",
							Toast.LENGTH_LONG).show();
			}
		}
	}
}
