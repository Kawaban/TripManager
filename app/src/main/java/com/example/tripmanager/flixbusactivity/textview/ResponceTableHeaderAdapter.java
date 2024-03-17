package com.example.tripmanager.flixbusactivity.textview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.codecrafters.tableview.TableHeaderAdapter;

public class ResponceTableHeaderAdapter extends TableHeaderAdapter {

    public ResponceTableHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getHeaderView(int columnIndex, ViewGroup parentView) {
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderDefaultHeader("Departure Time");
                break;
            case 1:
                renderedView = renderDefaultHeader("Arrival Time");
                break;
            case 2:
                renderedView = renderDefaultHeader("Changeovers");
                break;
            case 3:
                renderedView = renderDefaultHeader("Price");
                break;
            case 4:
                renderedView = renderDefaultHeader("Link");
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
