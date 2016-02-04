package com.androidopentutorials.sqlite.db;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//see oli esialgu, aga igaks juhuks muudan ära, ei tea kas on vajalik
//public class TeeObjektDAO extends TeeProovDBDAO {
public class TeeObjektDAO extends TeeObjektDBDAO {

    //// TODO: 04.02.2016  siin klassis on vaja meetodid üle vaadata
    // need on arvatavasti tehtud arvestusega, et teeobjekte pole baasi vaja lisada
    // selle klassiga peab vist ettevaatlik olema, et lisada vajalikke meetodeid
    // teeobjekti baasi lisamiseks, aga et ei lõhuks samas ära olemasolevat funktsionaalsus
    // päris 1:1-le ei tea, kas saab kopeerida teeproovDAO-d

    public static final String TEEOBJEKT_ID_WITH_PREFIX = "tobj.id";
    public static final String TEEPROOV_NAME_WITH_PREFIX = "tpr.name";
    public static final String TEEOBJEKT_NAME_WITH_PREFIX = "tobj.name";

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);


    // ok
    private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
            + " =?";

    //ok
    public TeeObjektDAO(Context context) {
        super(context);
    }

    //vana versioon
//    public long save(TeeObjekt teeObjekt) {
//        ContentValues values = new ContentValues();
//        values.put(DataBaseHelper.NIMETUS, teeObjekt.getName());
//
//        return database.insert(DataBaseHelper.OBJEKT_TABLE, null, values);
//    }


    //uus versioon
    public long save(TeeObjekt teeObjekt) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.NIMETUS, teeObjekt.getName());
        values.put(DataBaseHelper.OBJEKTI_NIMETUS, teeObjekt.getObjektiNimetus());
        values.put(DataBaseHelper.TEE_NIMETUS, teeObjekt.getTeeNimetus());
        values.put(DataBaseHelper.TEE_NR, teeObjekt.getTeeNr());
        values.put(DataBaseHelper.ALGUS_KM, teeObjekt.getAlgusKm());
        values.put(DataBaseHelper.LOPP_KM, teeObjekt.getLoppKm());

        return database.insert(DataBaseHelper.OBJEKT_TABLE, null, values);
    }

    // vana versioon
//    public long update(TeeObjekt teeObjekt) {
//        ContentValues values = new ContentValues();
//        values.put(DataBaseHelper.NIMETUS, teeObjekt.getName());
//
//        long result = database.update(DataBaseHelper.OBJEKT_TABLE, values,
//                WHERE_ID_EQUALS,
//                new String[]{String.valueOf(teeObjekt.getId())});
//        Log.d("Update Result:", "=" + result);
//        return result;
//
//    }

    // uus versioon
    public long update(TeeObjekt teeObjekt) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.NIMETUS, teeObjekt.getName());
        values.put(DataBaseHelper.OBJEKTI_NIMETUS, teeObjekt.getObjektiNimetus());
        values.put(DataBaseHelper.TEE_NIMETUS, teeObjekt.getTeeNimetus());
        values.put(DataBaseHelper.TEE_NR, teeObjekt.getTeeNr());
        values.put(DataBaseHelper.ALGUS_KM, teeObjekt.getAlgusKm());
        values.put(DataBaseHelper.LOPP_KM, teeObjekt.getLoppKm());

        long result = database.update(DataBaseHelper.OBJEKT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[]{String.valueOf(teeObjekt.getId())});
        Log.d("Update Result:", "=" + result);
        return result;

    }

    //ok
    public int deleteTeeObjekt(TeeObjekt teeObjekt) {
        return database.delete(DataBaseHelper.OBJEKT_TABLE,
                WHERE_ID_EQUALS, new String[]{teeObjekt.getId() + ""});
    }

    // vana versioon, kasutusel oli tavaline list
//    public List<TeeObjekt> getTeeObjektid() {
//        List<TeeObjekt> teeObjekts = new ArrayList<TeeObjekt>();
//        Cursor cursor = database.query(DataBaseHelper.OBJEKT_TABLE,
//                new String[]{DataBaseHelper.ID_COLUMN,
//                        DataBaseHelper.NIMETUS}, null, null, null, null,
//                null);
//
//        while (cursor.moveToNext()) {
//            TeeObjekt teeObjekt = new TeeObjekt();
//            teeObjekt.setId(cursor.getInt(0));
//            teeObjekt.setName(cursor.getString(1));
//            teeObjekts.add(teeObjekt);
//        }
//        return teeObjekts;
//    }

    //uus versioon, arraylist teeproovi järgi
    public ArrayList<TeeObjekt> getTeeObjektid() {
        ArrayList<TeeObjekt> teeObjekts = new ArrayList<TeeObjekt>();
        String query = "SELECT " + TEEOBJEKT_ID_WITH_PREFIX + ","

                + TEEOBJEKT_NAME_WITH_PREFIX + ","

                + DataBaseHelper.OBJEKTI_NIMETUS + ","
                + DataBaseHelper.TEE_NIMETUS + ","
                + DataBaseHelper.TEE_NR + ","
                + DataBaseHelper.ALGUS_KM + ","
                + DataBaseHelper.LOPP_KM + ",";

        //// TODO: 04.02.2016
        // siit edasi on kahtlane, kas on vaja, teeproovis on see vajalik
        //et luua objektiga seos, aga siin vist mitte
        // kommenteerin selle välja, aga kui siiski on vaja kasutusele võtta
        // siis on vaja nimetused ka ära muuta, praegu pole midagi teinud
//                + DataBaseHelper.PROOV_OBJEKT_ID + ","
//
//                + TEEOBJEKT_NAME_WITH_PREFIX + " FROM "
//                + DataBaseHelper.TEEPROOV_TABLE + " tpr, "
//                + DataBaseHelper.OBJEKT_TABLE + " tobj WHERE tpr."
//                + DataBaseHelper.PROOV_OBJEKT_ID + " = tobj."
//                + DataBaseHelper.ID_COLUMN;


        // vana versioon
//        Cursor cursor = database.query(DataBaseHelper.OBJEKT_TABLE,
//                new String[]{DataBaseHelper.ID_COLUMN,
//                        DataBaseHelper.NIMETUS}, null, null, null, null,
//                null);
//
//        while (cursor.moveToNext()) {
//            TeeObjekt teeObjekt = new TeeObjekt();
//            teeObjekt.setId(cursor.getInt(0));
//            teeObjekt.setName(cursor.getString(1));
//            teeObjekts.add(teeObjekt);
//        }


        // ei tea, kas järjekord on siin tähtis
        Log.d("query", query);
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            TeeObjekt teeObjekt = new TeeObjekt();
            teeObjekt.setId(cursor.getInt(0));
            teeObjekt.setName(cursor.getString(1));
            teeObjekt.setObjektiNimetus(cursor.getString(2));
            teeObjekt.setTeeNimetus((cursor.getString(3)));
            teeObjekt.setTeeNr(cursor.getInt(4));
            teeObjekt.setAlgusKm(cursor.getInt(5));
            teeObjekt.setLoppKm(cursor.getInt(6));

            // teeproovi näites tehakse siin teeobjekt ka valmis, aga praegu
            // pole see arvatavasti oluline

            // selles reas pole ka päris kindel
            teeObjekts.add(teeObjekt);
        }
        return teeObjekts;

    }


    //// TODO: 04.02.2016
    // selle meetodi võib ära kustutada, aga esialgu veel jätan
    // võibolla on nii, et programm ei jookse, kui ei ole vähemalt ühte näidisobjekti

//    public void loadTeeObjektid() {
//        TeeObjekt teeObjekt = new TeeObjekt("Tallinn-Pärnu");
//        TeeObjekt teeObjekt1 = new TeeObjekt("Jäniskoi");
//        TeeObjekt teeObjekt2 = new TeeObjekt("Uhhuu");
//
//
//        List<TeeObjekt> teeObjekts = new ArrayList<TeeObjekt>();
//        teeObjekts.add(teeObjekt);
//        teeObjekts.add(teeObjekt1);
//        teeObjekts.add(teeObjekt2);
//
//        for (TeeObjekt dept : teeObjekts) {
//            ContentValues values = new ContentValues();
//            values.put(DataBaseHelper.NIMETUS, dept.getName());
//            database.insert(DataBaseHelper.OBJEKT_TABLE, null, values);
//        }
//    }


    //// TODO: 04.02.2016 Excel
    // siit alates proovin exceli värki
    public void salvestaXlsMalukaardile() {
        Cursor cursor = database.query(DataBaseHelper.TEEPROOV_TABLE,
                new String[]{DataBaseHelper.ID_COLUMN,
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
        if (!directory.isDirectory()) {
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
