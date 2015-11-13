package com.example.mark.recyclerviewimp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        setupRecyclerView(layout);

        return layout;
    }

    private void setupRecyclerView(View layout){
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recycler_view);

        //create an recycler view adapter
        mRecyclerView.setAdapter(new RecyclerAdapater(getActivity(), getData()));

        //set the layout manager, that could be also StaggeredGridLayoutManager and
        //GridLayoutManager
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        //this code is to intercept the touch event and to handle it
        mRecyclerView.addOnItemTouchListener(
                new MyRecyclerOnItemTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //here is where you handle the action activated by onClick
                Log.d("RECYCLER_VIEW", " onClick " + position );
                Toast.makeText(getActivity(), " onClick " + position, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                //here is where you handle the action activated by onLongClick
                Log.d("RECYCLER_VIEW", " onLongClick " + position );
                Toast.makeText(getActivity(), " onLongClick " + position, Toast.LENGTH_SHORT ).show();
            }
        }));
    }


    private List<DataInfo> getData(){
        List<DataInfo> infoList = new ArrayList<>();

        for( int i = 0; i < 100; i++ ){
            infoList.add(new DataInfo(R.mipmap.ic_row, "this is the text # " + i));
        }
        return infoList;

    }


    //this is another method to handle touch events in a RecyclerView
    class MyRecyclerOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        private final String LISTENER_TAG = "MyRecyclerTouchListener";
        private ClickListener clickListener;
        private RecyclerView recyclerView;
        /*
        Detects various gestures and events using the supplied MotionEvents.
        The GestureDetector.OnGestureListener callback will notify users when a
        particular motion event has occurred.
         */
        private GestureDetector gestureDetector;

        public MyRecyclerOnItemTouchListener(
                Context context, RecyclerView recyclerView, ClickListener clickListener){
            this.clickListener = clickListener;
            this.recyclerView = recyclerView;
            gestureDetector = setupGestureDetector(context);
        }


        /*
        The onInterceptTouchEvent methods of each attached OnItemTouchListener will be run
        in the order in which each listener was added, before any other touch processing by
        the RecyclerView itself or child views occurs.

        Returns
        true if this OnItemTouchListener wishes to begin intercepting touch events,
        false to continue with the current behavior and continue observing future events in the gesture.

        ******** So, if this method returns false, the onTouchEvent won't be called!!!********
        *
        * this is the first method to be called
         */
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            //the gesture detector decides which method it will call when the event is
            //passed to its method onTouchEvent
            boolean result = gestureDetector.onTouchEvent(e);

            Log.d(LISTENER_TAG, " onInterceptTouchEvent " + result + " " + e);
            return result;
        }

        //handles the motionEvent
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d(LISTENER_TAG, " onTouchEvent " +  e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        private GestureDetector setupGestureDetector(Context context){
            GestureDetector gestureDetector =
                    new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

                        //before this being called, you must call onTouchEvent within the gestureDetector
                        // passing the MotionEvent inside InterceptTouchEvent from MyRecyclerOnItemTouchListener

                        //any boolean value returned by this subsequent methods it will be the value returned
                        //by the gestureDetector.onTouchEvent, in this case, by the
                        //MyRecyclerOnItemTouchListener.onInterceptTouchEvent
                        @Override
                        public boolean onDown(MotionEvent e) {
                            boolean result = false;
                            Log.d("GESTUREDETECTOR", " GestureDetector onDown " + e);

                            //****** It is better to handle onClick on onSingleTapUp or otherwise
                            //when it is only need the onLongPress onClick it will also be called

//
//                            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                            if( null != view && null != clickListener){
//                                clickListener.onClick(view, recyclerView.getChildAdapterPosition(view));
//                                result = true;
//                            }
                            //return super.onDown(e);
                            return result;
                        }

                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            boolean result = false;
                            Log.d("GESTUREDETECTOR", " GestureDetector onDown " + e);

                            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if( null != view && null != clickListener){
                                clickListener.onClick(view, recyclerView.getChildAdapterPosition(view));
                                result = true;
                            }
                            //return super.onDown(e);
                            return result;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {

                            Log.d("GESTUREDETECTOR", " GestureDetector onLongPress " + e);
                            //Now is the ugly part, to get the child view you need to do this mess?
                            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if( null != view && null != clickListener){
                                clickListener.onLongClick(view, recyclerView.getChildAdapterPosition(view));

                            }


                            //super.onLongPress(e);

                        }


                    });
            return gestureDetector;
        }




    }

    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }


}
