package com.example.fmstest2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.Collections;
import java.util.List;

import Filter.RowData;

public class FilterViewAdapter extends RecyclerView.Adapter<RowViewHolder> {

    private List<RowData> list = Collections.emptyList();
    private Context context;

    public FilterViewAdapter(List<RowData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_row, parent, false);
        RowViewHolder holder = new RowViewHolder(v);
        return holder;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(RowViewHolder holder, int position) {
        RowData rowData = list.get(position);
        holder.bind(rowData);
        holder.filterSwitch.setChecked(FilterState.getInstance().getShowEventMap().get(rowData.getFilterText()));
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }





}
