package com.androidopentutorials.sqlite.excel;

import android.database.Cursor;
import android.os.Environment;

import com.androidopentutorials.sqlite.db.DataBaseHelper;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by yogi on 28.01.2016.
 */
public class ExportToExcel {

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
                sheet.addCell(new Label(0, 0, "Subject")); // column and row
                sheet.addCell(new Label(1, 0, "Description"));
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex(DataBaseHelper.NIMETUS));
                        String desc = cursor.getString(cursor.getColumnIndex(DataBaseHelper.OBJEKTI_NIMETUS));

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
