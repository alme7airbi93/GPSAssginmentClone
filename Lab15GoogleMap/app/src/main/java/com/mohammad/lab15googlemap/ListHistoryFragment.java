package com.mohammad.lab15googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 11/12/2015.
 */
public class ListHistoryFragment extends Fragment{
    private HistoryAdapter historyAdapter;

    MapsActivity mapsActivity;
    RecyclerView recyclerView;
    boolean track;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_location_history_list, container, false);


        track = getActivity().getIntent().getBooleanExtra("tracker",false);

        recyclerView = (RecyclerView)v.findViewById(R.id.his_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;


    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI()
    {
        Manager manager = Manager.getManager(getActivity());
        List<OurLocation> ourLocations = manager.getHistoryLocations();
        if(historyAdapter == null) {
            historyAdapter = new HistoryAdapter(ourLocations);
            recyclerView.setAdapter(historyAdapter);
        }
        else {
            historyAdapter.setHistory(ourLocations);
            historyAdapter.notifyDataSetChanged();
        }
    }
    private class HistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        // Variables
        private TextView tvHistory,tvHR;



        private OurLocation ourLocation;

        // Constracter
        public HistoryHolder(View view)
        {
            super(view);

            view.setOnClickListener(this);

            Log.d("tag", "holder contstructor");
            tvHistory = (TextView)view.findViewById(R.id.tvListHistory);
            tvHR = (TextView)view.findViewById(R.id.tvHR);

            Log.d("tag", "holder afterfindviewbtn");

        }

        //
        public void bindHistory(OurLocation ourLocation)
        {
            this.ourLocation = ourLocation;
            tvHistory.setText(ourLocation.getAddress() + "\n" +
                    ourLocation.getDate().toString() + "\n" +
                    "lat: " + ourLocation.getLat() + "\t lng: " + ourLocation.getLng());


            Log.d("tag", "holder set");

            Log.d("tag", "holder set 2");

        }


        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(),MapsActivity.class);

            i.putExtra("latTag", ourLocation.getLat());
            i.putExtra("lngTag", ourLocation.getLng());
            i.putExtra("addTag", ourLocation.getAddress());
            i.putExtra("yes", false);

            i.putExtra("tracker", track);



//            MapsActivity.addMarkerMap(, ourLocation.getLng(), ourLocation.getAddress());
//
//            MapsActivity.fromOtherPage = true;


            startActivity(i);
            Log.d("tagggg", ourLocation.getLat() + ourLocation.getLng() + ourLocation.getAddress());
            Log.d("tagggg",ourLocation.getLat()+ ourLocation.getLng()+ ourLocation.getAddress());

        }
    }
    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder>
    {
        private List<OurLocation> ourLocations;

        public HistoryAdapter (List<OurLocation> ourLocations)
        {
            Log.d("tag","adapter constructor");
            this.ourLocations = ourLocations;
            Log.d("tag", "after");
        }
        @Override
        public int getItemCount() {
            Log.d("tag","adapter itemcount");
            return ourLocations.size();
        }

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            Log.d("tag","adapter oncreateviewholder");
//            View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup,false);
            View v = inflater.inflate(R.layout.list_item_history, viewGroup, false);



            Log.d("tag","adapter oncreateviewholderreturn");

            return new HistoryHolder(v);
        }

        @Override
        public void onBindViewHolder(HistoryHolder historyHolder, int i)
        {
            Log.d("tag","adapter onbindviewholder");
            OurLocation ourLocation = ourLocations.get(i);
            historyHolder.bindHistory(ourLocation);
        }
        public void setHistory(List<OurLocation> ourLocations)
        {
            Log.d("tag","adapter setholder");
            this.ourLocations = ourLocations;
        }
    }
}
