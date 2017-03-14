package com.phrase.my.myphrase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vignesh on 14/3/17.
 */

public class DetailViewAdapter extends ArrayAdapter<Pair> {

    String maskedPassword;

    public DetailViewAdapter(Context context, ArrayList<Pair> details){
        super(context, 0, details);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pair value = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.password_view, parent, false);
        }

        TextView headingText = (TextView) convertView.findViewById(R.id.passwordViewHeading);
        final TextView valueText = (TextView) convertView.findViewById(R.id.passwordViewValue);
        ToggleButton toggleButton = (ToggleButton) convertView.findViewById(R.id.passwordViewToggle);

        String headingTextString = value.first.toString();
        String valueTextString = value.second.toString();

        headingText.setText(headingTextString);

        switch (headingTextString){
            case "User Name":
                valueText.setText(valueTextString);
                toggleButton.setVisibility(View.INVISIBLE);
                break;
            case "Password":
                final String password = valueTextString;
                String mask = "";
                for (int i=0; i<password.length(); i++){
                    mask += "*";
                }
                this.maskedPassword = mask;
                valueText.setText(this.maskedPassword);

                toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((ToggleButton) v).isChecked()){
                            valueText.setText(password);
                        } else {
                            valueText.setText(maskedPassword);
                        }
                    }
                });
                break;
            case "Comments":
                valueText.setText(valueTextString);
                toggleButton.setVisibility(View.INVISIBLE);
                if(value.second.toString().length() == 0){
                    convertView.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }

        return convertView;
    }
}
