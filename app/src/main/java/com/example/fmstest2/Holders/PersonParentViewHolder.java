package com.example.fmstest2.Holders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.fmstest2.R;

public class PersonParentViewHolder extends ParentViewHolder {

    public ImageButton parentDropDownArrow;
    public TextView topText;

    public PersonParentViewHolder(View itemView) {
        super(itemView);

        parentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parentEventListArrow);
        topText = (TextView) itemView.findViewById(R.id.topExpandable);
    }
}
