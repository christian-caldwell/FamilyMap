package com.example.fmstest2.Person;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.fmstest2.Map.MapActivity;
import com.example.fmstest2.R;

import Model.Event;
import Model.Person;
import Person.PersonListEventChild;

public class PersonListChildViewHolder extends ChildViewHolder {

    private TextView birthText;
    private TextView personNameText;
    public ImageView image;
    private LinearLayout container;
    public Context context;
    public String type;
    public Person person;
    public Event event;


    public PersonListChildViewHolder(View itemView, final Context context) {
        super(itemView);
        this.context = context;
        birthText = (TextView) itemView.findViewById(R.id.birthLifeEventText);
        personNameText = (TextView) itemView.findViewById(R.id.lifeEventPersonText);
        image = (ImageView) itemView.findViewById(R.id.child_image);
        container = (LinearLayout) itemView.findViewById(R.id.person_child_layout);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("male") || type.equals("female")) {
                    Intent intent = new Intent(context, PersonActivity.class);
                    //intent.putExtra("person", person);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, MapActivity.class);
                    //intent.putExtra("event", event);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    public void bind(PersonListEventChild data) {
        this.type = data.getType();
        if (type.equals("event")) {
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

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getBirthText() {
        return birthText;
    }

    public void setBirthText(TextView birthText) {
        this.birthText = birthText;
    }

    public TextView getPersonNameText() {
        return personNameText;
    }

    public void setPersonNameText(TextView personNameText) {
        this.personNameText = personNameText;
    }

}
