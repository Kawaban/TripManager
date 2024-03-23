package com.example.tripmanager.flixbusactivity.tableview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tripmanager.flixbusactivity.domain.ResponseDTO;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

public class ResponceTableDataAdapter extends TableDataAdapter<ResponseDTO> {
    private ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
    public ResponceTableDataAdapter(Context context, List<ResponseDTO> data) {
        super(context, data);
    }
    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        ResponseDTO response = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderDepartureTime(response);
                break;
            case 1:
                renderedView = renderArrivalTime(response);
                break;
            case 2:
                renderedView = renderChangeovers(response);
                break;
            case 3:
                renderedView = renderPrice(response);
                break;
            case 4:
                renderedView = renderLink(response);
                break;
        }

        return renderedView;
    }

    private View renderDepartureTime(ResponseDTO response) {
        final TextView textView = new TextView(getContext());
        textView.setText(response.getDepartureTime());
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }

    private View renderArrivalTime(ResponseDTO response) {
        final TextView textView = new TextView(getContext());
        textView.setText(response.getArrivalTime());
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }

    private View renderPrice(ResponseDTO response) {
        final TextView textView = new TextView(getContext());
        textView.setText(String.valueOf(response.getPrice()));
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }

    private View renderChangeovers(ResponseDTO response) {
        final TextView textView = new TextView(getContext());
        textView.setText(String.valueOf(response.getChangeovers()));
        textView.setPadding(20, 10, 20, 10);
        return textView;
    }
    private View renderLink(ResponseDTO response) {
        final Button button = new Button(getContext());
        button.setText("Copy Link");
        button.setPadding(20, 10, 20, 10);
        button.setOnClickListener(v -> {
            ClipData clip = ClipData.newPlainText("link", response.getLink());
            clipboard.setPrimaryClip(clip);
        });
        return button;
    }
}

