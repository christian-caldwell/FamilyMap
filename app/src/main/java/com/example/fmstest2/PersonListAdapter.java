package com.example.fmstest2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;

import java.util.List;

import Person.TopEvent;

public class PersonListAdapter extends ExpandableRecyclerAdapter<PersonParentViewHolder, PersonListChildViewHolder> {

    private LayoutInflater mInflater;
    private Context context;

    public PersonListAdapter(Context context, List parentItemList) {
        super(context, parentItemList);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public PersonParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.life_events_parent, viewGroup, false);
        return new PersonParentViewHolder(view);

    }

    @Override
    public PersonListChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.life_events_child, viewGroup, false);
        return new PersonListChildViewHolder(view,context);
    }

    @Override
    public void onBindParentViewHolder(PersonParentViewHolder parentViewHolder, int i, Object o) {
        TopEvent topEvent = (TopEvent) o;
        parentViewHolder.topText.setText(topEvent.title);
    }


    @Override
    public void onBindChildViewHolder(PersonListChildViewHolder childViewHolder, int i, Object o) {
        PersonListEventChild personListEventChild = (PersonListEventChild) o;
        childViewHolder.getBirthText().setText(personListEventChild.getEventString());
        childViewHolder.getPersonNameText().setText(personListEventChild.getPersonName());
        childViewHolder.bind(personListEventChild);
    }








}
