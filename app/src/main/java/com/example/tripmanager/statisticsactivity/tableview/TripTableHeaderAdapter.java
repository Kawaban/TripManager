package com.example.tripmanager.statisticsactivity.tableview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.codecrafters.tableview.TableHeaderAdapter;

public class TripTableHeaderAdapter extends TableHeaderAdapter {
    public TripTableHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView(int columnIndex, ViewGroup parentView) {
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderDefaultHeader("Location");
                break;
            case 1:
                renderedView = renderDefaultHeader("Expenses");
                break;
            case 2:
                renderedView = renderDefaultHeader("Start Date");
                break;
            case 3:
                renderedView = renderDefaultHeader("End Date");
                break;
            case 4:
                renderedView = renderDefaultHeader("Images");
                break;
        }
        return renderedView;
    }

    private View renderDefaultHeader(String headerTitle) {
        final TextView textView = new TextView(getContext());
        textView.setText(headerTitle);
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }
}
