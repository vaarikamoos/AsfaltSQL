package com.androidopentutorials.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EsilehtActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esileht);
    }

    // kui vajutatakse "uus objekt" nupule
    public void nupuvajutusUusObjekt(View view){
        Intent intent = new Intent(this, UusObjektActivity.class);
        startActivity(intent);
    }



}
