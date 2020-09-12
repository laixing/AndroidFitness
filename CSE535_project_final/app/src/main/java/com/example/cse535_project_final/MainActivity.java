package com.example.cse535_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cse535_project_final.R;


public class MainActivity extends AppCompatActivity {

    EditText inputName, MyWeight, MyHeight, MyAge, ExpectedCalorie;
    TextView MyName, MyBmi;
    Button BMI, next, SCase;
    RadioGroup rg1;

    public static String  TABLE_NAME = "ProjectData";
    private String radioValue = "male";
    public static String suggest;
    private static final int running = 10;
    private static final int cycling = 8;
    private static final int walking = 4;
    float BMR, RunTime = 1, WalkTime = 1, CycleTime = 1, PAL, CaloriesBurnt, ExpectedCalorieBurn;
    float weight = 0, height = 0, desiredcalories = 0, bmiValue = 0;
    int age;
    Boolean sex = true;
    String a,w,h,cal,username;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text=getIntent().getStringExtra("001");
        MyName = (TextView)findViewById(R.id.MyName);
        inputName = (EditText)findViewById(R.id.inputName);
//        Log.d("TAG",text );
        if (text!=null){
            inputName.setVisibility(View.GONE);
            MyName.setVisibility(View.VISIBLE);
            MyName.setText(text);
        }else{
            inputName.setVisibility(View.VISIBLE);
            MyName.setVisibility(View.GONE);
        }


        MyWeight = (EditText)findViewById(R.id.MyWeight);
        MyHeight = (EditText)findViewById(R.id.MyHeight);
        MyAge = (EditText)findViewById(R.id.MyAge);
        MyBmi = (TextView)findViewById(R.id.MyBmi);
        BMI = (Button)findViewById(R.id.BmiButton);
        next = (Button)findViewById(R.id.NextButton);
        ExpectedCalorie = (EditText)findViewById(R.id.ExpectedCalorie);
        rg1 = (RadioGroup)findViewById(R.id.radioGrp);

        BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    username = MyName.getText().toString();
                    w = MyWeight.getText().toString();
                    h = MyHeight.getText().toString();
                    a = MyAge.getText().toString();
                    cal = ExpectedCalorie.getText().toString();
                    radioValue = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString();

                    weight = Float.parseFloat(w);
                    height = Float.parseFloat(h);
                    age = Integer.parseInt(a);
                    desiredcalories = Float.parseFloat(cal);

                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Please fill all empty input fields including the radio button to get your BMI", Toast.LENGTH_LONG).show();//report problem
                    return;
//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
//                    age=0;
                }

                System.out.println("the value of height is"+height);

                bmiValue = calculateBMI(weight, height);
                String result = interpretBMI(bmiValue);

                //MyBmi.setText(String.valueOf(bmiValue+" "+result));

                if(radioValue.equals("Male")){

                    BMR = (float) (66.47 + (13.75*weight)+(5.003*height*100.0)-(6.755*age));
//                    System.out.println("BMR value is "+BMR);
//                    System.out.println("weight value is "+weight);
//                    System.out.println("height value is "+height);
//                    System.out.println("age value is "+age);
//                    System.out.println("male");
                }else{
                    BMR = (float) (655.1 + (9.563*weight)+(1.85*height*100.0)-(4.676*age));
//                    System.out.println("BMR value is "+BMR);
//                    System.out.println("female");
//                    System.out.println("weight value is "+weight);
//                    System.out.println("height value is "+height);
//                    System.out.println("age value is "+age);
                }

                PAL = ((running*RunTime)+(walking*WalkTime)+(cycling*CycleTime))/(RunTime+WalkTime+CycleTime);
                CaloriesBurnt = BMR * PAL;
                MyBmi.setText(String.valueOf(bmiValue+" "+result));
                suggest = Planner(desiredcalories, weight);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = MyName.getText().toString()+inputName.getText().toString();
                    w = MyWeight.getText().toString();
                    h = MyHeight.getText().toString();
                    a = MyAge.getText().toString();
                    cal = ExpectedCalorie.getText().toString();
                    radioValue = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString();

                    weight = Float.parseFloat(w);
                    height = Float.parseFloat(h);
                    age = Integer.parseInt(a);
                    desiredcalories = Float.parseFloat(cal);

                    TABLE_NAME = username + "_" + age + "_" + radioValue;
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Some empty input fields left, will use default value instead", Toast.LENGTH_LONG).show();//report problem
                    username="deault_user";
                    radioValue="male";
                    weight=(float) 80;
                    height=(float) 1.80;
                    age=(int)25;
                    desiredcalories=(float) 500;
                    TABLE_NAME = username+"_"+age+"_"+radioValue;
//                    Toast.makeText(MainActivity.this, "Please fill all empty input fields including the radio button", Toast.LENGTH_LONG).show();//report problem
//                    return;

//                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();//report problem
//                    age=0;
//                    TABLE_NAME = "username_0_male";
                }

                bmiValue = calculateBMI(weight, height);
                String result = interpretBMI(bmiValue);

                //MyBmi.setText(String.valueOf(bmiValue+" "+result));
                System.out.println("radipValue is"+radioValue);

                if(radioValue.equals("Male")){
                    BMR = (float) (66.47 + (13.75*weight)+(5.003*height*100.0)-(6.755*age));
//                    System.out.println("BMR value is "+BMR);
//                    System.out.println("male");
//                    System.out.println("weight value is "+weight);
//                    System.out.println("height value is "+height);
//                    System.out.println("age value is "+age);
                }else{
                    BMR = (float) (655.1 + (9.563*weight)+(1.85*height*100.0)-(4.676*age));
//                    System.out.println("BMR value is "+BMR);
//                    System.out.println("weight value is "+weight);
//                    System.out.println("height value is "+height);
//                    System.out.println("age value is "+age);
//                    System.out.println("female");
                }

                PAL = ((running*RunTime)+(walking*WalkTime)+(cycling*CycleTime))/(RunTime+WalkTime+CycleTime);
                CaloriesBurnt = BMR * PAL;
                MyBmi.setText(String.valueOf(bmiValue+" "+result));
                suggest = Planner(desiredcalories, weight);

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                //pass the table name from current activity to next activity
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                intent.putExtra("table_name",TABLE_NAME);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);
                System.out.println("Desired calorie burn = " + desiredcalories);//omi
                //Start the new activity
                String t2=""+weight;
                intent.putExtra("weight",t2);
                intent.putExtra("suggestion",suggest);
                startActivity(intent);;
            }
        });

        Button nextPage = (Button)findViewById(R.id.b1);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Weather.class);
                startActivity(i);
                finish();
            }
        });

        SCase = (Button)findViewById(R.id.scase);
        SCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SCasePage = new Intent(MainActivity.this, SuccessfulCases.class);
                startActivity(SCasePage);
                finish();
            }
        });
    }

    //Calculate BMI
    private float calculateBMI (float weight, float height) {
        return (float) (weight / (height * height));
    }

    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private String Planner(float desiredCalories, float myWeight){
        float RunningTime = desiredCalories/(myWeight*running);
        float WalkingTime = desiredCalories/(myWeight*walking);
        float CyclingTime = desiredCalories/(myWeight*cycling);
        String suggestion = "Today's suggestion : \nRun for: "+ (Math.round(RunningTime*60))+"min\n"+ "Walk for: "+ (Math.round(WalkingTime*60)) + "min\n" +"Cycle for: "+ (Math.round(CyclingTime*60))+"min";
        return suggestion;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            try {
                Intent settings_intent = new Intent(this, MySettings.class);
                startActivity(settings_intent);
                finish();
            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, "Error is: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
