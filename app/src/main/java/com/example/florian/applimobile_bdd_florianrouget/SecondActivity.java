package com.example.florian.applimobile_bdd_florianrouget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by florian on 15/03/2017.
 */

public class SecondActivity extends AppCompatActivity {

    Button RETURN_BUTTON;
    EditText EDIT_NAME;
    EditText EDIT_DESC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        RETURN_BUTTON = (Button) findViewById(R.id.button);
        EDIT_NAME = (EditText) findViewById(R.id.editName);
        EDIT_DESC = (EditText) findViewById(R.id.editDesc);

        RETURN_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("Nom", EDIT_NAME.getText().toString());
                data.putExtra("Desc", EDIT_DESC.getText().toString());
                setResult(1, data);
                finish();
            }
        });

    }

}
