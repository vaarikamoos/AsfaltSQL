package com.androidopentutorials.sqlite.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidopentutorials.sqlite.MainActivity;
import com.androidopentutorials.sqlite.R;
import com.androidopentutorials.sqlite.db.TeeObjektDAO;
import com.androidopentutorials.sqlite.db.TeeProovDAO;
import com.androidopentutorials.sqlite.to.TeeObjekt;
import com.androidopentutorials.sqlite.to.TeeProov;

public class CustomTprDialogFragment extends DialogFragment {

	// UI references
	private EditText tprNameEtxt;
	private EditText tprNumberEtxt;
	private EditText tprKuupaevEtxt;
	private EditText tprVotuKohtEtxt;
	private EditText tprMaterjaltxt;
	private EditText tprTooteKirjeldustxt;

	private Spinner tobjSpinner;
	private LinearLayout submitLayout;

	private TeeProov teeProov;

	TeeProovDAO teeproovDAO;
	ArrayAdapter<TeeObjekt> adapter;

	public static final String ARG_ITEM_ID = "tpr_dialog_fragment";
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	
	/*
	 * Callback used to communicate with TprListFragment to notify the list adapter.
	 * MainActivity implements this interface and communicates with TprListFragment.
	 */
	public interface CustomEmpDialogFragmentListener {
		void onFinishDialog();
	}

	public CustomTprDialogFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		teeproovDAO = new TeeProovDAO(getActivity());

		Bundle bundle = this.getArguments();
		teeProov = bundle.getParcelable("valitudTeeProov");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.fragment_add_tpr,
				null);
		builder.setView(customDialogView);

		tprNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_name);
		tprNumberEtxt = (EditText) customDialogView
				.findViewById(R.id.etxt_tprNumber);
		tprKuupaevEtxt = (EditText) customDialogView.findViewById(R.id.etxt_tprKuupaev);
		tprVotuKohtEtxt = (EditText) customDialogView.findViewById(R.id.etxt_tprVotuKoht);


		tobjSpinner = (Spinner) customDialogView
				.findViewById(R.id.spinner_dept);
		submitLayout = (LinearLayout) customDialogView
				.findViewById(R.id.layout_submit);
		submitLayout.setVisibility(View.GONE);
		setValue();

		builder.setTitle(R.string.update_emp);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.update,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							teeProov.setProoviKp(formatter.parse(tprKuupaevEtxt.getText().toString()));
						} catch (ParseException e) {
							Toast.makeText(getActivity(),
									"Invalid date format!",
									Toast.LENGTH_SHORT).show();
							return;
						}
						//stringid
						teeProov.setName(tprNameEtxt.getText().toString());
						teeProov.setName(tprVotuKohtEtxt.getText().toString());

						//doubled
						teeProov.setProoviNr(Double.parseDouble(tprNumberEtxt
								.getText().toString()));


						TeeObjekt dept = (TeeObjekt) adapter
								.getItem(tobjSpinner.getSelectedItemPosition());
						teeProov.setTeeObjekt(dept);
						long result = teeproovDAO.update(teeProov);
						if (result > 0) {
							MainActivity activity = (MainActivity) getActivity();
							activity.onFinishDialog();
						} else {
							Toast.makeText(getActivity(),
									"Unable to update teeProov",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

					}
				});

		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	private void setValue() {
		TeeObjektDAO teeObjektDAO = new TeeObjektDAO(getActivity());

		List<TeeObjekt> teeObjekts = teeObjektDAO.getTeeObjektid();
		adapter = new ArrayAdapter<TeeObjekt>(getActivity(),
				android.R.layout.simple_list_item_1, teeObjekts);
		tobjSpinner.setAdapter(adapter);
		int pos = adapter.getPosition(teeProov.getTeeObjekt());

		if (teeProov != null) {

			tprNameEtxt.setText(teeProov.getName());

			tprKuupaevEtxt.setText(formatter.format(teeProov.getProoviKp()));

			tprNumberEtxt.setText(teeProov.getProoviNr() + "");

			tprVotuKohtEtxt.setText(teeProov.getPrVotuKoht());

			tobjSpinner.setSelection(pos);
		}
	}
}
