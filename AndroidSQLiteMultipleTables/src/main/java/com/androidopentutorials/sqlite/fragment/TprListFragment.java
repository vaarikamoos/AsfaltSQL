package com.androidopentutorials.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.androidopentutorials.sqlite.R;
import com.androidopentutorials.sqlite.adapter.TproovListAdapter;
import com.androidopentutorials.sqlite.db.TeeProovDAO;
import com.androidopentutorials.sqlite.to.TeeProov;

public class TprListFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "employee_list";

	Activity activity;
	ListView employeeListView;
	ArrayList<TeeProov> teeProovs;

	TproovListAdapter employeeListAdapter;
	TeeProovDAO employeeDAO;

	private GetEmpTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		employeeDAO = new TeeProovDAO(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_emp_list, container,
				false);
		findViewsById(view);

		task = new GetEmpTask(activity);
		task.execute((Void) null);

		employeeListView.setOnItemClickListener(this);
		employeeListView.setOnItemLongClickListener(this);
		return view;
	}

	private void findViewsById(View view) {
		employeeListView = (ListView) view.findViewById(R.id.list_emp);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		TeeProov teeProov = (TeeProov) list.getItemAtPosition(position);

		if (teeProov != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("valitudTeeProov", teeProov);
			CustomTprDialogFragment customTprDialogFragment = new CustomTprDialogFragment();
			customTprDialogFragment.setArguments(arguments);
			customTprDialogFragment.show(getFragmentManager(),
					CustomTprDialogFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		TeeProov teeProov = (TeeProov) parent.getItemAtPosition(position);
		// Use AsyncTask to delete from database
		employeeDAO.deleteTeeProov(teeProov);
		employeeListAdapter.remove(teeProov);

		return true;
	}

	public class GetEmpTask extends AsyncTask<Void, Void, ArrayList<TeeProov>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetEmpTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<TeeProov> doInBackground(Void... arg0) {
			ArrayList<TeeProov> teeProovList = employeeDAO.getTeeProovid();
			return teeProovList;
		}

		@Override
		protected void onPostExecute(ArrayList<TeeProov> empList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				teeProovs = empList;
				if (empList != null) {
					if (empList.size() != 0) {
						employeeListAdapter = new TproovListAdapter(activity,
								empList);
						employeeListView.setAdapter(employeeListAdapter);
					} else {
						Toast.makeText(activity, "No TeeProov Records",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}

	/*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from CustomTprDialogFragment when an teeProov record is updated.
	 * This is used for communicating between fragments.
	 */
	public void updateView() {
		task = new GetEmpTask(activity);
		task.execute((Void) null);
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.app_name);
		getActivity().getActionBar().setTitle(R.string.app_name);
		super.onResume();
	}
}
