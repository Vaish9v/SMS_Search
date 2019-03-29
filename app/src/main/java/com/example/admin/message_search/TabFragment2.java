package com.example.admin.message_search;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import static com.example.admin.message_search.MainActivity.recieved;
import static com.example.admin.message_search.MainActivity.sent;

public class TabFragment2 extends Fragment{
    PieChart chart;

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        TabFragment1 tabFragment = new TabFragment1();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View v=inflater.inflate(R.layout.stats, container, false);
            chart=v.findViewById(R.id.chart);
            chart.getDescription().setText("Message Frequency");
            chart.getDescription().setTextColor(Color.WHITE);
            chart.getDescription().setTextSize(12);
            chart.getDescription().setPosition(1000,1450);
            chart.getDescription().setTextAlign(Paint.Align.RIGHT);
            chart.setCenterText("Messages");
            chart.setTransparentCircleAlpha(0);
            chart.setRotationEnabled(true);
            chart.setHoleRadius(25);
            chart.setCenterTextSize(15);

            ArrayList<PieEntry> data=new ArrayList<>();
            ArrayList<String> name=new ArrayList<>();
            data.add(new PieEntry(sent));
            data.add(new PieEntry(recieved));
            name.add("Sent");
            name.add("Recieved");

            PieDataSet pdata=new PieDataSet(data,"");
            pdata.setSliceSpace(2);
            pdata.setForm(Legend.LegendForm.NONE);
            pdata.setValueTextSize(15);

            ArrayList<Integer> color=new ArrayList<>();
            color.add(Color.argb(255,25,115,34));
            color.add(Color.argb(255,207,109,10));
            pdata.setColors(color);

            Legend legend=chart.getLegend();
            legend.setForm(Legend.LegendForm.SQUARE);
            legend.setTextColor(Color.WHITE);
            legend.setExtra(new int[]{color.get(0),color.get(1)},new String[]{"Sent","Recieved"});
            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legend.setTextSize(11);

            PieData pieData=new PieData(pdata);
            chart.setData(pieData);
            chart.invalidate();
            return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
