package com.androidopentutorials.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "teeProovBaas";
	private static final int DATABASE_VERSION = 1;

	public static final String TEEPROOV_TABLE = "teeProov";
	public static final String OBJEKT_TABLE = "objekt";

	// ühised andmed
	public static final String ID_COLUMN = "id";
	public static final String NIMETUS = "name";

	// teeproovi andmed
	public static final String PROOVIVOTMISEAEG_KP = "dob";
	public static final String PROOVI_NR = "salary";
	public static final String PROOV_OBJEKT_ID = "dept_id";
	public static final String PROOVIVOTUKOHT = "proovi_koht";
	public static final String PR_MATERJAL = "pr_materjal";
	public static final String PR_TOOTE_KIRJELDUS = "pr_toote_kirjeldus";
	public static final String PR_KAS_PLAAN_KOOSTATI = "pr_kas_plaan_koostati";

	// teeobjektii andmed
	public static final String OBJEKTI_NIMETUS = "objekti_nimetus";
	public static final String TEE_NIMETUS = "tee_nimetus";
	public static final String TEE_NR = "tee_nr";
	public static final String ALGUS_KM = "algus_km";
	public static final String LOPP_KM = "lopp_km";


	// teeproovi tabel
	public static final String CREATE_TEEPROOV_TABLE = "CREATE TABLE "
			+ TEEPROOV_TABLE + "("
			+ ID_COLUMN + " INTEGER PRIMARY KEY, "
			+ NIMETUS + " TEXT, "
			+ PROOVI_NR + " DOUBLE, "
			+ PROOVIVOTUKOHT + " TEXT, "
			+ PR_MATERJAL + " TEXT, "
			+ PR_TOOTE_KIRJELDUS + " TEXT, "
			+ PR_KAS_PLAAN_KOOSTATI + " INTEGER, "

			+ PROOVIVOTMISEAEG_KP + " DATE, "
			+ PROOV_OBJEKT_ID + " INT, "
			+ "FOREIGN KEY(" + PROOV_OBJEKT_ID + ") REFERENCES "
			+ OBJEKT_TABLE + "(id) " + ")";

	// teeobjekti tabel
	public static final String CREATE_TEEOBJEKT_TABLE = "CREATE TABLE "
			+ OBJEKT_TABLE + "("
			+ ID_COLUMN + " INTEGER PRIMARY KEY,"
			+ OBJEKTI_NIMETUS + " TEXT, "
			+ TEE_NIMETUS + " TEXT, "
			+ TEE_NR + " DOUBLE, "
			+ ALGUS_KM + " DOUBLE, "
			+ LOPP_KM + " DOUBLE, "
			+ NIMETUS + ")";

	private static DataBaseHelper instance;

	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DataBaseHelper(context);
		return instance;
	}

	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TEEOBJEKT_TABLE);
		db.execSQL(CREATE_TEEPROOV_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
