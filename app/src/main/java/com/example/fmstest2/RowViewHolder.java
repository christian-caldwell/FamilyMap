package com.example.fmstest2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import Filter.FilterData;
import Filter.RowData;

public class RowViewHolder extends RecyclerView.ViewHolder {

    public TextView filterText;
    public Switch filterSwitch;

    public RowViewHolder(View itemView) {
        super(itemView);
        final FilterData filterData = FilterData.getInstance();
        filterText = (TextView) itemView.findViewById(R.id.filter_row_text);
        filterSwitch = (Switch) itemView.findViewById(R.id.filter_switch);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterData.toggleMapBool(String.valueOf(filterText.getText()), isChecked);
            }
        });
    }

    public TextView getFilterText() {
        return filterText;
    }

    public void setFilterText(TextView filterText) {
        this.filterText = filterText;
    }

    public Switch getFilterSwitch() {
        return filterSwitch;
    }

    public void setFilterSwitch(Switch filterSwitch) {
        this.filterSwitch = filterSwitch;
    }

    public void bind(RowData data) {
        filterText.setText(data.getFilterText());
    }



}
