package com.diev.aplicacion.diev;

<<<<<<< HEAD
=======
import android.graphics.drawable.Drawable;
>>>>>>> Luis
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.LayoutInflater;
import java.util.HashSet;
import android.content.Context;
import java.util.ArrayList;
import android.graphics.Typeface;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CalendarAdapter extends ArrayAdapter<Date>{
    // days with events
    private HashSet<Date> eventDays;
    private LayoutInflater inflater;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
    {
        super(context, R.layout.calendar_day, days);
        this.eventDays = eventDays;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        // day in question
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        // today
        Date today = new Date();

        // inflate item if it does not exist yet
        if (view == null) {
            view = inflater.inflate(R.layout.calendar_day, parent, false);
            holder = new ViewHolder();
            holder.imgEvento = (ImageView) view.findViewById(R.id.img_evento);
            holder.txt_day = (TextView) view.findViewById(R.id.txt_day);
            holder.txt_hide_date = (TextView) view.findViewById(R.id.txt_hide_date);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        //hide date
        holder.txt_hide_date.setVisibility(View.INVISIBLE);

        // if this day has an event, specify event image
        holder.imgEvento.setImageDrawable(null);
        if (eventDays != null)
        {
            for (Date eventDate : eventDays)
            {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year)
                {
                    // mark this day for event
                    holder.imgEvento.setImageDrawable(context.getResources().getDrawable (R.drawable.ic_clock));
                    break;
                }
            }
        }

        // clear styling
        holder.txt_day.setTypeface(null, Typeface.NORMAL);
        holder.txt_day.setTextColor(Color.BLACK);

        if (date.getMonth() != today.getMonth() ||
                date.getYear() != today.getYear()) {
            // if this day is outside current month, grey it out
            holder.txt_day.setTextColor(Color.GRAY);
        }
        else if (date.getDate() == today.getDate())
        {
            // if it is today, set it to blue/bold
            holder.txt_day.setTypeface(null, Typeface.BOLD);
<<<<<<< HEAD
            holder.txt_day.setTextColor(Color.RED);
=======
            holder.txt_day.setTextColor(Color.rgb(255, 87, 34));
>>>>>>> Luis
        }

        // set text
        holder.txt_day.setText(String.valueOf(date.getDate()));
        holder.txt_hide_date.setText(sdf.format(date.getTime()));

        return view;
    }

    static class ViewHolder {
        TextView txt_day;
        TextView txt_hide_date;
        ImageView imgEvento;
    }

}


