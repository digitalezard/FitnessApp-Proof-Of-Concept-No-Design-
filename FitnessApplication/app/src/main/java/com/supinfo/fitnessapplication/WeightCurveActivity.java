package com.supinfo.fitnessapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.supinfo.fitnessapplication.DAO.ScoreDao;
import com.supinfo.fitnessapplication.Models.Score;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WeightCurveActivity extends AppCompatActivity {

    private Button Return;
    private String EXTRA_ID = "userId";
    private int id;
    private ScoreDao sDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_curve);

        sDataSource = new ScoreDao(this);
        sDataSource.open();

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getIntExtra(EXTRA_ID, 0);
            List<Score> myScores;

            LineChart lineChart = (LineChart) findViewById(R.id.chart);
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            try{
                myScores = sDataSource.getAllSpecificScoreByUserId(id, "WEIGHT");
                int i=0;

                for(Score CurrentScore : myScores){
                    entries.add(new Entry(CurrentScore.getScore_value(), i));
                    labels.add(String.valueOf(CurrentScore.getId()));
                    i++;
                }

                LineDataSet dataset = new LineDataSet(entries, "# of Calls");
                LineData data = new LineData(labels, dataset);
                lineChart.setData(data);
                lineChart.setDescription("Weight");

                dataset.setDrawCubic(true);
                dataset.setDrawFilled(true);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                lineChart.animateY(5000);
            }catch (ParseException e) {
                e.printStackTrace();
            }

        }

        Return = (Button) findViewById(R.id.ReturnWC);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WeightCurveActivity.this, WelcomeActivity.class);
                myIntent.putExtra(EXTRA_ID, id);
                sDataSource.close();
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();

        sDataSource.close();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!sDataSource.myDb.isOpen()) {
            sDataSource.open();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(sDataSource.myDb.isOpen()) {
            sDataSource.close();
        }
    }
}
