package com.example.tripmanager.statisticsactivity.tableview;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tripmanager.infrastructure.database.TripEntity;
import com.example.tripmanager.statisticsactivity.StatisticImagesActivity;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

public class TripTableDataAdapter extends TableDataAdapter<TripEntity> {
    public TripTableDataAdapter(Context context, List<TripEntity> data) {
        super(context, data);
    }
    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        TripEntity response = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderLocation(response);
                break;
            case 1:
                renderedView = renderExpenses(response);
                break;
            case 2:
                renderedView = renderDuration(response);
                break;
            case 3:
                renderedView = renderRating(response);
                break;
            case 4:
                renderedView = renderImages(response);
                break;
        }

        return renderedView;
    }

    private View renderLocation(TripEntity trip) {
        final TextView textView = new TextView(getContext());
        textView.setText(trip.location);
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }

    private View renderExpenses(TripEntity trip) {
        final TextView textView = new TextView(getContext());
        textView.setText(trip.expenses);
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }

    private View renderDuration(TripEntity trip) {
        final TextView textView = new TextView(getContext());
        textView.setText(trip.startDate + " - " + trip.endDate);
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }

    private View renderRating(TripEntity trip) {
        final RatingBar ratingBar = new RatingBar(getContext());
        ratingBar.setRating(trip.rating);
        ratingBar.setPadding(20, 10, 20, 10);
        return ratingBar;
    }

    private View renderImages(TripEntity trip) {
        final Button button = new Button(getContext());
        button.setText("Show Images");
        button.setPadding(20, 10, 20, 10);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StatisticImagesActivity.class);
            intent.putParcelableArrayListExtra("images", trip.images);
            getContext().startActivity(intent);
        });
        return button;
    }
}
