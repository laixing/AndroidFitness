package com.example.cse535_project_final;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import java.util.Collections;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;

public class BarGraph extends AppCompatActivity {
    SQLiteDatabase db;
    public static final String  DATABASE_NAME = "Team09.db";
    public static String  TABLE_NAME = "ProjectData";
    public static float BMR = 0, desiredcalories = 0;//new omkar


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);
        //Implement to pull values from database
        Intent i=getIntent();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> target = new ArrayList<>();

        try {
            TABLE_NAME=getIntent().getStringExtra("table_name");
            String s = getIntent().getStringExtra("BMR");
            BMR = Float.parseFloat(s);
            System.out.println("pushups bmr = "+BMR);
            String r = getIntent().getStringExtra("des");
            desiredcalories = Float.parseFloat(r);
            System.out.println("bar des cal : "+ desiredcalories);



            db = SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory() + File.separator + DATABASE_NAME, null);
            db.beginTransaction();
            Cursor cursor = null, cursor1 = null, cursor2 = null, cursor3 = null;
            String query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY timestamp DESC";

            try {
                cursor = db.rawQuery(query, null);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e("DBS", e.getMessage());

            }
            if (cursor.moveToFirst()) {
                for (int j = 0; j < 7; j++) {

                    target.add(new BarEntry(cursor.getFloat(cursor.getColumnIndex("calories")), j));
                    entries.add(new BarEntry(cursor.getFloat(cursor.getColumnIndex("targetcalories")), j));//Need to change
                    cursor.moveToNext();
                }

            }
        }catch(Exception e){
            Log.e("DBS", e.getMessage());

        }finally {
            db.endTransaction();
        }
//        System.out.println("the order of entries: "+entries);
//        System.out.println("the order of target: "+target);
//        Collections.reverse(entries);
//        Collections.reverse(target);
//        System.out.println("after reverse the order of entries: "+entries);
//        System.out.println("after reverse the order of target: "+target);
        BarDataSet dataset1 = new BarDataSet(entries, "Target");
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarDataSet dataset2 = new BarDataSet(target, "Achieved");
        dataset2.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("Sun");
//        labels.add("Mon");
//        labels.add("Tues");
//        labels.add("Wed");
//        labels.add("Thurs");
//        labels.add("Fri");
//        labels.add("Sat");
        labels.add("current day");
        labels.add("1 day before");
        labels.add("2 days before");
        labels.add("3 days before");
        labels.add("4 days before");
        labels.add("5 days before");
        labels.add("6 days before");


        BarChart chart = new BarChart(this);
        setContentView(chart);
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);
        BarData data = new BarData(labels, dataSets);
        chart.setData(data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        chart.setDescription("Weekly Calorie Chart");
        chart.setClickable(true);
        chart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), BarGraph.class);
                //pass the table name from current activity to next activity
                intent.putExtra("table_name", TABLE_NAME);
                String t = ""+desiredcalories;
                intent.putExtra("des",t);
                String t1 = ""+BMR;
                intent.putExtra("BMR",t1);

                startActivity(intent);
                finish();
            }
        });
    }

}
