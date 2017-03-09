package com.phrase.my.myphrase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vignesh on 9/3/17.
 */

public class DetailListAdapter extends ArrayAdapter<Detail> {

    public DetailListAdapter(Context context, List<Detail> details){
        super(context, 0, details);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Detail detail = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.password_list, parent, false);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.passwordTitleText);
        titleText.setText(detail.title);

        return convertView;
    }
}
