package com.supinfo.fitnessapplication.CustomAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.supinfo.fitnessapplication.Models.Score;
import com.supinfo.fitnessapplication.R;

import java.util.List;

/**
 * Created by digitalezard on 27/02/2017.
 */

public class WeightListAdapter extends ArrayAdapter<Score> {

    private List<Score> Items;
    private int layoutRessourceId;
    private Context context;

    public WeightListAdapter(Context context, int resource, List<Score> objects) {
        super(context, resource, objects);
        this.layoutRessourceId = resource;
        this.context = context;
        this.Items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        WeightItem weight = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutRessourceId, parent, false);

        weight = new WeightItem();
        weight.myScore = Items.get(position);
        weight.Remove = (Button) row.findViewById(R.id.RemoveItemButton);
        weight.Remove.setTag(weight.myScore);

        weight.WeightLabel = (TextView)row.findViewById(R.id.WeightLabel);
        weight.WeightMeasure = (TextView)row.findViewById(R.id.WeightMeasure);

        row.setTag(weight);
        weight.WeightLabel.setText(String.valueOf(weight.myScore.getScore_value()));
        return row;
    }
}
