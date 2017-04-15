package com.robpercival.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean imageClicked = false;

    //method for tosters - small pop-ups
    //takes values from textFields
    //invokes pop-ups with values from text fields added
    public void clickFunction(View view){
        EditText stringEnterName = (EditText) findViewById(R.id.myTextField);
        EditText stringEnterPassword = (EditText) findViewById(R.id.myPasswordField);

        Toast.makeText(MainActivity.this, "Login: "+stringEnterName.getText().toString(),
                Toast.LENGTH_SHORT).show();

        Toast.makeText(MainActivity.this, "Password: "+stringEnterPassword.getText().toString(),
                Toast.LENGTH_SHORT).show();

        Toast.makeText(MainActivity.this, "Hi there, Queen of Meereen, Queen of the Andals" +
                "(, the Rhoynar) and the First Men, Lady Regnant of the Seven Kingdoms, Khaleesi"+
                "of the Great Grass Sea, Mhysa, Breaker of Chains, the Unburnt, Mother of Dragons",
                Toast.LENGTH_LONG).show();

        System.out.println("Clicked");
        Log.i("Info", "Button pressed");
        Log.i("Name", stringEnterName.getText().toString());
        Log.i("Password",stringEnterPassword.getText().toString());
    }

    //method for changing image element source (update image with android code)
    public void clickFunctionChangeImage(View view){
        Log.i("Test","Button clicked");
       ImageView image2 = (ImageView) findViewById(R.id.catOrNot);
        if(!imageClicked){
        image2.setImageResource(R.drawable.dogy);
        imageClicked=true;
        }else{
            image2.setImageResource(R.drawable.cat1);
            imageClicked=false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //android studio error  No resource identifier found for attribute 'srcCompat' in package
    //change app:srcCompat="@android:drawable/ic_dialog_email" for
    //android:src="@android:drawable/ic_dialog_email"

   // findViewById(R.id.button).setOnClickListener(new OnClickListener() {
     //   @Override
      //  public void onClick(View v) {
      //      final ImageView imageView = (ImageView) findViewById(R.id.image_view);
     //       imageView.setImageResource(R.drawable.some_drawable);
     //   }
    //}




   // btn.setOnClickListener(new OnClickListener() {

       // @Override
       // public void onClick(View arg0) {
            // TODO Auto-generated method stub
         //   btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.locationbutton_on));
       // }
    //});
}
