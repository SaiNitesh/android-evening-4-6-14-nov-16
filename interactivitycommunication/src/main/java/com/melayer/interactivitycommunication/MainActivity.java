package com.melayer.interactivitycommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_MY_OS = "myOs";
    public static final int REQ_NEW_ACTIVITY = 1345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRbs();

        findViewById(R.id.btnNew).setOnClickListener(this::clicked);
    }

    private void clicked(View view) {
        if(view.getId() == R.id.btnNew) openNewActivity();
    }

    private void openNewActivity() {
        Intent intent = new Intent(this, NewActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MY_OS,getOs());

        intent.putExtras(bundle);

        //startActivity(intent);

        startActivityForResult(intent, REQ_NEW_ACTIVITY);
    }

    private void initRbs() {
        ((RadioButton)findViewById(R.id.rbAndroid))
                .setOnCheckedChangeListener(this::checkChanged);

        ((RadioButton)findViewById(R.id.rbApple))
                .setOnCheckedChangeListener(this::checkChanged);

        ((RadioButton)findViewById(R.id.rbRim))
                .setOnCheckedChangeListener(this::checkChanged);

        /*((RadioButton)findViewById(R.id.rbRim))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });

        ((RadioButton)findViewById(R.id.rbRim))
                .setOnCheckedChangeListener((buttonView, isChecked) -> {

                });*/
    }

    private void checkChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked) {
            if (compoundButton instanceof RadioButton) { //RTTI
                RadioButton rb = (RadioButton) compoundButton; // casting
                setOs(rb.getText().toString());
            }
        }
    }

    private void setOs(String os){
        ((EditText)findViewById(R.id.edtOs)).setText(os);
    }

    private String getOs(){
        return ((EditText)findViewById(R.id.edtOs)).getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_NEW_ACTIVITY){
            if(resultCode == RESULT_OK){
                setOs(getMobile(data));
            }
        }
    }

    private String getMobile(Intent data) {
        if(data == null) throw new RuntimeException();

        return  data.getStringExtra(NewActivity.KEY_MY_MOBILE);
    }
}
