package com.androidopentutorials.sqlite.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.androidopentutorials.sqlite.to.TeeObjekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import com.androidopentutorials.sqlite.excel.ExportToExcel;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TeeObjektDAO extends TeeProovDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	public TeeObjektDAO(Context context) {
		super(context);
	}

	public long save(TeeObjekt teeObjekt) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NIMETUS, teeObjekt.getName());

		return database.insert(DataBaseHelper.OBJEKT_TABLE, null, values);
	}

	public long update(TeeObjekt teeObjekt) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NIMETUS, teeObjekt.getName());

		long result = database.update(DataBaseHelper.OBJEKT_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(teeObjekt.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;

	}

	public int deleteDept(TeeObjekt teeObjekt) {
		return database.delete(DataBaseHelper.OBJEKT_TABLE,
				WHERE_ID_EQUALS, new String[] { teeObjekt.getId() + "" });
	}

	public List<TeeObjekt> getTeeObjektid() {
		List<TeeObjekt> teeObjekts = new ArrayList<TeeObjekt>();
		Cursor cursor = database.query(DataBaseHelper.OBJEKT_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NIMETUS}, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			TeeObjekt teeObjekt = new TeeObjekt();
			teeObjekt.setId(cursor.getInt(0));
			teeObjekt.setName(cursor.getString(1));
			teeObjekts.add(teeObjekt);
		}
		return teeObjekts;
	}

	public void loadTeeObjektid() {
		TeeObjekt teeObjekt = new TeeObjekt("Tallinn-Pärnu");
		TeeObjekt teeObjekt1 = new TeeObjekt("Jäniskoi");
		TeeObjekt teeObjekt2 = new TeeObjekt("Uhhuu");


		List<TeeObjekt> teeObjekts = new ArrayList<TeeObjekt>();
		teeObjekts.add(teeObjekt);
		teeObjekts.add(teeObjekt1);
		teeObjekts.add(teeObjekt2);

		for (TeeObjekt dept : teeObjekts) {
			ContentValues values = new ContentValues();
			values.put(DataBaseHelper.NIMETUS, dept.getName());
			database.insert(DataBaseHelper.OBJEKT_TABLE, null, values);
		}
	}


	// siit alates proovin exceli värki
	public void salvestaXlsMalukaardile(){
		Cursor cursor = database.query(DataBaseHelper.TEEPROOV_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NIMETUS}, null, null, null, null,
				null);

		exportToExcel(cursor);
	}

	public void exportToExcel(Cursor cursor) {
		final String fileName = "TeeProov.xls";

		// salvestab faili external storage-sse
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/TeeProovid");

		//loob kausta, kui ei ole olemas
		if(!directory.isDirectory()){
			directory.mkdirs();
		}

		//file path
		File file = new File(directory, fileName);

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbook;

		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			//Excel sheet name. 0 represents first sheet
			WritableSheet sheet = workbook.createSheet("esimene tekst", 0);

			try {
				sheet.addCell(new Label(0, 0, "tekst1")); // column and row
				sheet.addCell(new Label(1, 0, "tekst2"));
				if (cursor.moveToFirst()) {
					do {
						String title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.ID_COLUMN));
						String desc = cursor.getString(cursor.getColumnIndex(DataBaseHelper.NIMETUS));

						int i = cursor.getPosition() + 1;
						sheet.addCell(new Label(0, i, title));
						sheet.addCell(new Label(1, i, desc));
					} while (cursor.moveToNext());
				}
				//closing cursor
				cursor.close();
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			workbook.write();
			try {
				workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
