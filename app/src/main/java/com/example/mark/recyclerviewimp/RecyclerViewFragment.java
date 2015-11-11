package com.example.mark.recyclerviewimp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 11/10/15.
 *
 * steps for the Android ViewHolder design pattern
 * 1 Inflate the layout, initialize the ViewHolder inside onCreateViewHolder
 * 2 Use the supplied ViewHolder to populate your current row inside the onBindViewHolder
 *
 *
 *
 *
 */
public class RecyclerViewFragment extends Fragment {
    private RecyclerView mRecyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.recycler_layout, container, false);

        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recycler_view);
        //create an recycler view adapter
        RecyclerAdapater adapater = new RecyclerAdapater(getActivity(), getData());

        mRecyclerView.setAdapter(adapater);
        //set the layout manager, that could be also StaggeredGridLayoutManager and
        //GridLayoutManager
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        return layout;
    }


    private List<DataInfo> getData(){
        List<DataInfo> infoList = new ArrayList<>();

        for( int i = 0; i < 100; i++ ){
            infoList.add(new DataInfo(R.mipmap.ic_row, "this is the text # " + i));
        }
        return infoList;

    }
}
