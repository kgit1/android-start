package com.konggit.appdesignsupport;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextInputLayout mUsernameLayout;
    EditText mUsername;
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new ButtonListener());

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new FloatingButtonListener());


        mUsernameLayout = (TextInputLayout) findViewById(R.id.login_layout);
        mUsername = (EditText) findViewById(R.id.edit_text_email);
        mPassword = (EditText) findViewById(R.id.edit_text_password);
        mUsername.setOnFocusChangeListener(new EditFocusListener());
        mPassword.setOnFocusChangeListener(new EditFocusListener());
    }

    private void snack() {

        //simple snackBar
        //Snackbar.make(findViewById(R.id.root), "Button clicked", Snackbar.LENGTH_LONG).show();

        //with action
        //findViewById(R.id.root) - binds snackbar to our root layout
        //Snackbar.make(findViewById(R.id.root), "Button clicked", Snackbar.LENGTH_LONG).setAction("UNDO",

        //findViewById(R.id.main_content) - binds snackbar to our coordinator layout which has fab inside(fab - floating action button)
        //we bind snack to floating action buttons layout - to make button move when snack appears
        Snackbar.make(findViewById(R.id.coordinator), "Button clicked", Snackbar.LENGTH_LONG).setAction("UNDO",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "UNDO clicked", Toast.LENGTH_SHORT).show();
                        Log.i("Info", "UNDO clicked");
                    }
                }).show();

    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Log.i("Info", "Button");
            snack();

        }
    }

    private class FloatingButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Log.i("Info", "Floating button");
            snack();

        }
    }

    private class EditFocusListener implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (v != mUsername && mUsername.getText().toString().isEmpty()) {
                mUsernameLayout.setErrorEnabled(true);
                Toast.makeText(getApplicationContext(),"Error Login empty", Toast.LENGTH_SHORT).show();
                mUsernameLayout.setError(getResources().getString(R.string.login_error));
            }
            if (v != mPassword && mPassword.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(),"Error Password empty", Toast.LENGTH_SHORT).show();
                mUsernameLayout.setErrorEnabled(false);
            }

        }
    }


}
