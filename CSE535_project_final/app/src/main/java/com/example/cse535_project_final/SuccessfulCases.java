package com.example.cse535_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessfulCases extends AppCompatActivity {

    private ImageView storyImageView1,storyImageView2,storyImageView3;
    private TextView storyTextView1,storyTextView2,storyTextView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_cases);

        storyImageView1 = (ImageView)findViewById(R.id.storyImageView1);
        storyTextView1 = (TextView)findViewById(R.id.storyTextView1);

        storyImageView1.setImageResource(R.drawable.case1);
        storyTextView1.setText(R.string.case1);

        storyImageView2 = (ImageView)findViewById(R.id.storyImageView2);
        storyTextView2 = (TextView)findViewById(R.id.storyTextView2);

        storyImageView2.setImageResource(R.drawable.case2);
        storyTextView2.setText(R.string.case2);

        storyImageView3 = (ImageView)findViewById(R.id.storyImageView3);
        storyTextView3 = (TextView)findViewById(R.id.storyTextView3);

        storyImageView3.setImageResource(R.drawable.case3);
        storyTextView3.setText(R.string.case3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SuccessfulCases.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
