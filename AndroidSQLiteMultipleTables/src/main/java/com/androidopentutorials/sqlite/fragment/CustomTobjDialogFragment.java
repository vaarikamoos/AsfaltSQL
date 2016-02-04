//package com.androidopentutorials.sqlite.fragment;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Locale;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.androidopentutorials.sqlite.db.TeeObjektDAO;
//import com.androidopentutorials.sqlite.to.TeeObjekt;
//
//// see on tehtud CustomTprDialogFragment järgi, et kuvada teeObjekti lisamist
//public class CustomTobjDialogFragment extends DialogFragment {
//
//    private EditText tobjObjNimetusEtxt;
//    private EditText tobjTeeNimetusEtxt;
//    private EditText tobjTeeNrEtxt;
//    private EditText tobjAlgusKmEtxt;
//    private EditText tobjLoppKmEtxt;
//
//    private LinearLayout sisestaObjektLayout;
//
//    private TeeObjekt teeObjekt;
//
//    TeeObjektDAO teeObjektDAO;
//
//    // siin kohas on näites mingi arrayAdapter, ma ei tea, kas peab lisama
//
//    public static final String ARG_ITEM_ID = "tobj_dialog_fragment";
//
//    private static final SimpleDateFormat formatter = new SimpleDateFormat(
//            "yyyy-MM-dd", Locale.ENGLISH);
//
//    /*
// * Callback used to communicate with TobjListFragment to notify the list adapter.
// * MainActivity implements this interface and communicates with TobjListFragment.
// */
//    public interface CustomTobjDialogFragmentListener {
//        void onFinishDialog();
//    }
//
//
//}

//// TODO: 04.02.2016 hetkel pooli
