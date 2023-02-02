package com.example.agroobot_fms;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agroobot_fms.model.dropdown_kondisi_daun.Datum;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Datum> listData;
    LayoutInflater inflter;

    public CustomAdapter(Context context, List<Datum> listData) {
      this.context = context;
      this.listData = listData;
      inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setBackgroundColor(Color.parseColor(listData.get(i).getColorCodeVar()));
        names.setText(listData.get(i).getLeafConditionVar());
        return view;
    }
}
