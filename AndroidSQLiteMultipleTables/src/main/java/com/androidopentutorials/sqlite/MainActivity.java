package com.androidopentutorials.sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.androidopentutorials.sqlite.db.TeeObjektDAO;
import com.androidopentutorials.sqlite.fragment.CustomTprDialogFragment.CustomEmpDialogFragmentListener;
import com.androidopentutorials.sqlite.fragment.TprAddFragment;
import com.androidopentutorials.sqlite.fragment.TprListFragment;

public class MainActivity extends FragmentActivity implements
		CustomEmpDialogFragmentListener {

	private Fragment contentFragment;
	private TprListFragment teeProovListFragment;
	private TprAddFragment teeProovAddFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fragmentManager = getSupportFragmentManager();

        // see ei ole ajutine
		TeeObjektDAO tobjDAO = new TeeObjektDAO(this);

		// TODO ajutine, salvestamise jaoks tuleks teha eraldi nupp
        // või siis kutsuda rakendusest väljumisel
		tobjDAO.salvestaXlsMalukaardile();

		//Initially loads departments
		if(tobjDAO.getTeeObjektid().size() <= 0)
			tobjDAO.loadTeeObjektid();

		// region ekraani keeramine
		/*
		 * This is called when orientation is changed.
		 */
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("content")) {
				String content = savedInstanceState.getString("content");
				if (content.equals(TprAddFragment.ARG_ITEM_ID)) {
					if (fragmentManager
							.findFragmentByTag(TprAddFragment.ARG_ITEM_ID) != null) {
						setFragmentTitle(R.string.add_emp);
						contentFragment = fragmentManager
								.findFragmentByTag(TprAddFragment.ARG_ITEM_ID);
					}
				}
			}
			if (fragmentManager.findFragmentByTag(TprListFragment.ARG_ITEM_ID) != null) {
				teeProovListFragment = (TprListFragment) fragmentManager
						.findFragmentByTag(TprListFragment.ARG_ITEM_ID);
				contentFragment = teeProovListFragment;
			}
		} else {
			teeProovListFragment = new TprListFragment();
			setFragmentTitle(R.string.app_name);
			switchContent(teeProovListFragment, TprListFragment.ARG_ITEM_ID);
		}
        // endregion
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			setFragmentTitle(R.string.add_emp);
			teeProovAddFragment = new TprAddFragment();
			switchContent(teeProovAddFragment, TprAddFragment.ARG_ITEM_ID);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (contentFragment instanceof TprAddFragment) {
			outState.putString("content", TprAddFragment.ARG_ITEM_ID);
		} else {
			outState.putString("content", TprListFragment.ARG_ITEM_ID);
		}
		super.onSaveInstanceState(outState);
	}

	/*
	 * We consider TprListFragment as the home fragment and it is not added to
	 * the back stack.
	 */
	public void switchContent(Fragment fragment, String tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		while (fragmentManager.popBackStackImmediate())
			;

		if (fragment != null) {
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.content_frame, fragment, tag);
			// Only TprAddFragment is added to the back stack.
			if (!(fragment instanceof TprListFragment)) {
				transaction.addToBackStack(tag);
			}
			transaction.commit();
			contentFragment = fragment;
		}
	}

	protected void setFragmentTitle(int resourseId) {
		setTitle(resourseId);
		getActionBar().setTitle(resourseId);

	}

	/*
	 * We call super.onBackPressed(); when the stack entry count is > 0. if it
	 * is instanceof TprListFragment or if the stack entry count is == 0, then
	 * we prompt the user whether to quit the app or not by displaying dialog.
	 * In other words, from TprListFragment on back press it quits the app.
	 */
	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			super.onBackPressed();
		} else if (contentFragment instanceof TprListFragment
				|| fm.getBackStackEntryCount() == 0) {
			//finish();
			//Shows an alert dialog on quit
			onShowQuitDialog();
		}
	}
	
	public void onShowQuitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);


		builder.setMessage("Kas soovid väljuda?");
		builder.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				});
		builder.setNegativeButton(android.R.string.no,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		builder.create().show();
	}

	/*
	 * Callback used to communicate with TprListFragment to notify the list adapter.
	 * Communication between fragments goes via their Activity class.
	 */
	@Override
	public void onFinishDialog() {
		if (teeProovListFragment != null) {
			teeProovListFragment.updateView();
		}
	}
}
