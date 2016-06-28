package com.diev.aplicacion.diev;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.diev.aplicacion.diev.adapter.eventoAdapter;
import com.diev.aplicacion.diev.brl.EventoBrl;
import com.diev.aplicacion.diev.model.Evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static com.diev.aplicacion.diev.R.layout.fragment_month_content;

public class MonthContent extends Fragment {

    private ListView ciudadList;
    EventoBrl eventoBrl;
    ImageView img;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(fragment_month_content, null);


        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());


        CalendarView cv = ((CalendarView) view.findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        try{
        fab = ((FloatingActionButton) view.findViewById(R.id.btn_new_event));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "Se presiono el boton");
                switch (v.getId()) {
                    case R.id.btn_new_event:
                        //what to put here
                        Toast.makeText(getContext(), "Add an Event", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), CrearEvento.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        }catch (Exception ex){
            Log.e("Click", ex.toString());
        }

        return recyclerView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(fragment_month_content, parent, false));
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 1;

        public ContentAdapter(Context context) {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}
