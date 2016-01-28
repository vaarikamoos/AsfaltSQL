package com.androidopentutorials.sqlite.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.androidopentutorials.sqlite.to.TeeObjekt;
import com.androidopentutorials.sqlite.to.TeeProov;

public class TeeProovDAO extends TeeProovDBDAO {

	public static final String TEEPROOV_ID_WITH_PREFIX = "tpr.id";
	public static final String TEEPROOV_NAME_WITH_PREFIX = "tpr.name";
	public static final String TEEOBJEKT_NAME_WITH_PREFIX = "tobj.name";
	
	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN + " =?";
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public TeeProovDAO(Context context) {
		super(context);
	}

	public long save(TeeProov teeProov) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NIMETUS, teeProov.getName());
		values.put(DataBaseHelper.PROOVIVOTMISEAEG_KP, formatter.format(teeProov.getProoviKp()));
		values.put(DataBaseHelper.PROOVI_NR, teeProov.getProoviNr());
		values.put(DataBaseHelper.PROOV_OBJEKT_ID, teeProov.getTeeObjekt().getId());
		values.put(DataBaseHelper.PROOVIVOTUKOHT, teeProov.getPrVotuKoht());
		values.put(DataBaseHelper.PR_MATERJAL, teeProov.getPrMaterjal());
		values.put(DataBaseHelper.PR_TOOTE_KIRJELDUS, teeProov.getPrTooteKirjeldus());

		return database.insert(DataBaseHelper.TEEPROOV_TABLE, null, values);
	}

	public long update(TeeProov teeProov) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NIMETUS, teeProov.getName());
		values.put(DataBaseHelper.PROOVIVOTMISEAEG_KP, formatter.format(teeProov.getProoviKp()));
		values.put(DataBaseHelper.PROOVI_NR, teeProov.getProoviNr());
		values.put(DataBaseHelper.PROOV_OBJEKT_ID, teeProov.getTeeObjekt().getId());
		values.put(DataBaseHelper.PROOVIVOTUKOHT, teeProov.getPrVotuKoht());
		values.put(DataBaseHelper.PR_MATERJAL, teeProov.getPrMaterjal());
		values.put(DataBaseHelper.PR_TOOTE_KIRJELDUS, teeProov.getPrTooteKirjeldus());

		long result = database.update(DataBaseHelper.TEEPROOV_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(teeProov.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;
	}

	public int deleteTeeProov(TeeProov teeProov) {
		return database.delete(DataBaseHelper.TEEPROOV_TABLE, WHERE_ID_EQUALS,
				new String[] { teeProov.getId() + "" });
	}

	// METHOD 1
	// Uses rawQuery() to query multiple tables
	public ArrayList<TeeProov> getTeeProovid() {
		ArrayList<TeeProov> teeProovs = new ArrayList<TeeProov>();
		String query = "SELECT " + TEEPROOV_ID_WITH_PREFIX + ","
				+ TEEPROOV_NAME_WITH_PREFIX + ","
				+ DataBaseHelper.PROOVIVOTMISEAEG_KP + ","
				+ DataBaseHelper.PROOVI_NR + ","
				+ DataBaseHelper.PROOVIVOTUKOHT + ","
				+ DataBaseHelper.PR_MATERJAL + ","
				+ DataBaseHelper.PR_TOOTE_KIRJELDUS + ","
				+ DataBaseHelper.PR_KAS_PLAAN_KOOSTATI + ","

				+ DataBaseHelper.PROOV_OBJEKT_ID + ","

				+ TEEOBJEKT_NAME_WITH_PREFIX + " FROM "
				+ DataBaseHelper.TEEPROOV_TABLE + " tpr, "
				+ DataBaseHelper.OBJEKT_TABLE + " tobj WHERE tpr."
				+ DataBaseHelper.PROOV_OBJEKT_ID + " = tobj."
				+ DataBaseHelper.ID_COLUMN;

		// Building query using INNER JOIN keyword
		/*String query = "SELECT " + TEEPROOV_ID_WITH_PREFIX + ","
		+ TEEPROOV_NAME_WITH_PREFIX + "," + DataBaseHelper.PROOVIVOTMISEAEG_KP
		+ "," + DataBaseHelper.PROOVI_NR + ","
		+ DataBaseHelper.PROOV_OBJEKT_ID + ","
		+ TEEOBJEKT_NAME_WITH_PREFIX + " FROM "
		+ DataBaseHelper.TEEPROOV_TABLE + " emp INNER JOIN "
		+ DataBaseHelper.OBJEKT_TABLE + " dept ON emp."
		+ DataBaseHelper.PROOV_OBJEKT_ID + " = dept."
		+ DataBaseHelper.ID_COLUMN;*/


		// // TODO: 27.01.2016
		// see osa on kahtlane, peab üle vaatama

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			TeeProov teeProov = new TeeProov();
			teeProov.setId(cursor.getInt(0));
			teeProov.setName(cursor.getString(1));
			try {
				teeProov.setProoviKp(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				teeProov.setProoviKp(null);
			}
			teeProov.setProoviNr(cursor.getDouble(3));
			//region minu tehtud
			teeProov.setPrVotuKoht((cursor.getString(4)));
			teeProov.setPrMaterjal(cursor.getString(5));
			teeProov.setPrTooteKirjeldus(cursor.getString(6));
			teeProov.setPrKasPlaanKoostati(cursor.getInt(7));

			//endregion


			TeeObjekt teeObjekt = new TeeObjekt();
			teeObjekt.setId(cursor.getInt(8));
			teeObjekt.setName(cursor.getString(9));

			teeProov.setTeeObjekt(teeObjekt);

			teeProovs.add(teeProov);
		}
		return teeProovs;
	}
	
	// METHOD 2
	// Uses SQLiteQueryBuilder to query multiple tables
	/*public ArrayList<TeeProov> getTeeProovid() {
		ArrayList<TeeProov> employees = new ArrayList<TeeProov>();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder
				.setTables(DataBaseHelper.TEEPROOV_TABLE
						+ " INNER JOIN "
						+ DataBaseHelper.OBJEKT_TABLE
						+ " ON "
						+ DataBaseHelper.PROOV_OBJEKT_ID
						+ " = "
						+ (DataBaseHelper.OBJEKT_TABLE + "." + DataBaseHelper.ID_COLUMN));

		// Get cursor
		Cursor cursor = queryBuilder.query(database, new String[] {
				TEEPROOV_ID_WITH_PREFIX,
				DataBaseHelper.TEEPROOV_TABLE + "."
						+ DataBaseHelper.NIMETUS,
				DataBaseHelper.PROOVIVOTMISEAEG_KP,
				DataBaseHelper.PROOVI_NR,
				DataBaseHelper.PROOV_OBJEKT_ID,
				DataBaseHelper.OBJEKT_TABLE + "."
						+ DataBaseHelper.NIMETUS }, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			TeeProov employee = new TeeProov();
			employee.setId(cursor.getInt(0));
			employee.setName(cursor.getString(1));
			try {
				employee.setProoviKp(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				employee.setProoviKp(null);
			}
			employee.setProoviNr(cursor.getDouble(3));

			TeeObjekt department = new TeeObjekt();
			department.setId(cursor.getInt(4));
			department.setName(cursor.getString(5));

			employee.setTeeObjekt(department);

			employees.add(employee);
		}
		return employees;
	}*/
}
