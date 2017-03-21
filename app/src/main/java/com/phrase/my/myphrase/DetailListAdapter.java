package com.phrase.my.myphrase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vignesh on 9/3/17.
 */

public class DetailListAdapter extends ArrayAdapter<Detail> implements Filterable {

    private SparseBooleanArray selectedDetailIds;
    private List<Detail> originalData;
    private List<Detail> filterData;
    private PasswordFilter passwordFilter = new PasswordFilter();

    public DetailListAdapter(Context context, List<Detail> details){
        super(context, 0, details);
        selectedDetailIds = new SparseBooleanArray();
        this.originalData = details;
        this.filterData = details;
    }

    @Override
    public int getCount() {
        return filterData.size();
    }

    @Nullable
    @Override
    public Detail getItem(int position) {
        return filterData.get(position);
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

    public void remove(Detail object){
        if(DbOperations.deleteDetail(Integer.parseInt(object.getId().toString()))){
            super.remove(object);
        }
    }

    public void toggleSelection(int position){
        selectView(position, !selectedDetailIds.get(position));
    }

    public void removeSelection(){
        selectedDetailIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    private void selectView(int position, boolean value){
        if(value){
            selectedDetailIds.put(position, value);
        } else {
            selectedDetailIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedDetailIds() {
        return  selectedDetailIds;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return passwordFilter;
    }

    private class PasswordFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            List<Detail> list = originalData;

            List<Detail> filteredList = new ArrayList<Detail>(originalData.size());

            Detail filterableDetail;
            for (int i=0; i<list.size(); i++){
                filterableDetail = list.get(i);
                if(filterableDetail.title.toLowerCase().contains(filterString)){
                    filteredList.add(filterableDetail);
                }
            }
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterData = (List<Detail>)results.values;
            notifyDataSetChanged();
        }
    }
}
