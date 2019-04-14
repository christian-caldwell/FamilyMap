package com.example.fmstest2.Search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdevelopers.seanwade.familymap.R;
import com.appdevelopers.seanwade.familymap.map.MapActivity;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.person_info.PersonActivity;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

/**
 * Created by seanwade on 3/28/16.
 */
public class SearchRowViewHolder extends RecyclerView.ViewHolder {

    public TextView mainTextView;
    public TextView secondaryTextView;
    public ImageView image;
    public LinearLayout rowView;
    public Context context;
    public String type;
    public Person person;
    public Event event;

    public SearchRowViewHolder(View itemView, final Context context) {
        super(itemView);
        this.context = context;
        mainTextView = (TextView) itemView.findViewById(R.id.search_row_main_text);
        secondaryTextView = (TextView) itemView.findViewById(R.id.search_row_secondary_text);
        image = (ImageView) itemView.findViewById(R.id.search_row_image);
        rowView = (LinearLayout) itemView.findViewById(R.id.search_row_view);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("male") || type.equals("female")) {
                    Intent intent = new Intent(context, PersonActivity.class);
                    intent.putExtra("person", person);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("event", event);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }



    public void bind(SearchRowData data) {
        this.type = data.getType();
        mainTextView.setText(data.getMainText());
        secondaryTextView.setText(data.getSecondaryText());
        if (type.equals("location")) {
            image.setImageResource(R.drawable.map_marker_ic);
            this.event = data.event;
        }
        else if (type.equals("male")) {
            image.setImageResource(R.drawable.male_ic);
            this.person = data.person;
        }
        else {
            image.setImageResource(R.drawable.female_ic);
            this.person = data.person;
        }
    }


}
