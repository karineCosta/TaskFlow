package com.example.taskflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private Context context;

    private int[] images = {
            R.drawable.ic_tasks,
            R.drawable.ic_calendar,
            R.drawable.ic_reminder,
            R.drawable.ic_announcement
    };

    private String[] titles = {
            "Tarefas",
            "Eventos",
            "Lembretes",
            "Avisos"
    };

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.gridImage);
        TextView textView = convertView.findViewById(R.id.gridTitle);

        imageView.setImageResource(images[position]);
        textView.setText(titles[position]);

        return convertView;
    }
}