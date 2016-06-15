package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by Niharika Rastogi on 15-06-2016.
 */

//THIS IS ADAPTER TO SET CONTENT IN WIDGET
public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    Cursor mcursor;

    WidgetViewsFactory(Context context, Intent intent) {

        mContext = context;
    }

    @Override
    public void onCreate() {
        String[] projection = new String[]{QuoteColumns._ID, QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP};
        String selection = QuoteColumns.ISCURRENT + " = ?";
        String[] selectionArgs = new String[]{"1"};

        mcursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, projection, selection, selectionArgs, null);
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        if (mcursor != null)
            mcursor.close();
    }

    @Override
    public int getCount() {
        if (mcursor != null)
            return mcursor.getCount();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mcursor != null) {
            mcursor.moveToPosition(position);
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
            views.setTextViewText(R.id.stock_symbol, mcursor.getString(mcursor.getColumnIndex(QuoteColumns.SYMBOL)));
            views.setTextViewText(R.id.bid_price, mcursor.getString(mcursor.getColumnIndex(QuoteColumns.BIDPRICE)));
            views.setTextViewText(R.id.change, mcursor.getString(mcursor.getColumnIndex(QuoteColumns.CHANGE)));

            if (mcursor.getInt(mcursor.getColumnIndex(QuoteColumns.ISUP)) == 1) {
                views.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
            } else {
                views.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
            }
            return views;
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
