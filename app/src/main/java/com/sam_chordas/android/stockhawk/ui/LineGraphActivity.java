package com.sam_chordas.android.stockhawk.ui;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by Niharika Rastogi on 14-06-2016.
 */
public class LineGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        LineChartView mChart = (LineChartView) findViewById(R.id.chart1);
        Intent intent = getIntent();
        String Stock_Symbol = intent.getStringExtra("Stock_Symbol");
        Cursor c = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL}, QuoteColumns.SYMBOL + "= ?",
                new String[]{Stock_Symbol}, null);

// Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder
//        final Runnable mBaseAction;
//
        float[] mValues = new float[c.getCount()];
        String[] mLabels = new String[c.getCount()];


        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            mValues[i] = Float.parseFloat(c.getString(c.getColumnIndex(QuoteColumns.BIDPRICE)));
            mLabels[i] = "T" + (i + 1);
            c.moveToNext();

        }
        LineSet dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4);
        mChart.addData(dataset);
        mChart.show();


//        private final String[] mLabels= {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};
//        private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f},
//                {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f}};
//        LineSet dataset = new LineSet(mLabels, mValues[0]);
//        dataset.setColor(Color.parseColor("#758cbb"))
//                .setFill(Color.parseColor("#2d374c"))
//                .setDotsColor(Color.parseColor("#758cbb"))
//                .setThickness(4)
//                .setDashed(new float[]{10f, 10f})
//                .beginAt(5);
//
//        assert mChart != null;
//        mChart.addData(dataset);
//
//        dataset = new LineSet(mLabels, mValues[0]);
//        dataset.setColor(Color.parseColor("#b3b5bb"))
//                .setFill(Color.parseColor("#2d374c"))
//                .setDotsColor(Color.parseColor("#ffc755"))
//                .setThickness(4)
//                .endAt(6);
//        mChart.addData(dataset);
//
//        mChart.setBorderSpacing(Tools.fromDpToPx(15))
//                .setAxisBorderValues(0, 20)
//                .setYLabels(AxisController.LabelPosition.NONE)
//                .setLabelsColor(Color.parseColor("#6a84c3"))
//                .setXAxis(false)
//                .setYAxis(false);
//
//        mChart.show();

    }
}