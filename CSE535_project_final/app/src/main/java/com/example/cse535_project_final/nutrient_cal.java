package com.example.cse535_project_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class nutrient_cal extends AppCompatActivity{
    CheckBox rice,milk,pizza,hamburger,nuddle,banana,beef,egg;
    Button buttonOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrient_cal);
        addListenerOnButtonClick();
    }
    public void addListenerOnButtonClick() {
        //Getting instance of CheckBoxes and Button from the activty_main.xml file
        rice = (CheckBox) findViewById(R.id.checkBox);
        milk = (CheckBox) findViewById(R.id.checkBox2);
        pizza = (CheckBox) findViewById(R.id.checkBox3);
        hamburger=(CheckBox) findViewById(R.id.checkBox4);
        nuddle=(CheckBox) findViewById(R.id.checkBox5);
        banana=(CheckBox) findViewById(R.id.checkBox6);
        beef=(CheckBox) findViewById(R.id.checkBox7);
        egg=(CheckBox) findViewById(R.id.checkBox8);


        buttonOrder = (Button) findViewById(R.id.button);


        //Applying the Listener on the Button click
        buttonOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int totalamount = 0;
                StringBuilder result = new StringBuilder();
//                result.append("Selected Items:");
                if (rice.isChecked()) {
//                    result.append("\nPizza 100Rs");
                    totalamount += 206;
                }
                if (milk.isChecked()) {
//                    result.append("\nCoffe 50Rs");
                    totalamount += 103;
                }
                if (pizza.isChecked()) {
//                    result.append("\nBurger 120Rs");
                    totalamount += 285;
                }
                if (hamburger.isChecked()) {
//                    result.append("\nBurger 120Rs");
                    totalamount += 354;
                }
                if (nuddle.isChecked()) {
//                    result.append("\nBurger 120Rs");
                    totalamount += 221;
                }
                if (banana.isChecked()) {
//                    result.append("\nBurger 120Rs");
                    totalamount += 105;
                }
                if (beef.isChecked()) {
//                    result.append("\nBurger 120Rs");
                    totalamount += 213;
                }
                if (egg.isChecked()) {
//                    result.append("\nBurger 120Rs");
                    totalamount += 78;
                }

                result.append("Total calories is:  " + totalamount);
                //Displaying the message on the toast
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
