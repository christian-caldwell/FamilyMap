package com.example.fmstest2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fmstest2.R;
import com.example.fmstest2.Holders.SearchRowViewHolder;

import java.util.Collections;
import java.util.List;

import Search.SearchRowData;

public class SearchViewAdapter extends RecyclerView.Adapter<SearchRowViewHolder> {

    private List<SearchRowData> list = Collections.emptyList();
    private Context context;

    public SearchViewAdapter(List<SearchRowData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        SearchRowViewHolder holder = new SearchRowViewHolder(v, context);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(SearchRowViewHolder holder, int position) {
        SearchRowData rowData = list.get(position);
        holder.bind(rowData);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
