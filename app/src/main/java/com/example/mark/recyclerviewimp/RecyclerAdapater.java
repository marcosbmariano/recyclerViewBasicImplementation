package com.example.mark.recyclerviewimp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by mark on 11/10/15.
 */
public class RecyclerAdapater extends RecyclerView.Adapter<RecyclerAdapater.MyVH> {
    private LayoutInflater mLayoutInflater;
    List<DataInfo> mInfoList = Collections.emptyList();


    public RecyclerAdapater(Context context, List<DataInfo> data ) {
        //create an layout inflater
        this.mLayoutInflater = LayoutInflater.from(context);
        mInfoList = data;


    }

    @Override
    public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        //create an object of the ViewHolder
        View view =  mLayoutInflater.inflate(R.layout.recycler_row, parent, false);

        //this other implementation where the whole row will answer the user actions
        //View view = setupView(parent);


        MyVH viewHolder = new MyVH(view);

        return viewHolder;
    }
    //this other implementation where the whole row will answer the user actions
    private View setupView(ViewGroup parent){
        View view = mLayoutInflater.inflate(R.layout.recycler_row, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "From view "  , Toast.LENGTH_LONG).show();
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Long Click From view "  , Toast.LENGTH_LONG).show();
                //return true so the system knows that the longClick was consumed
                return true;
            }
        });

        return view;
    }

    @Override
    public void onBindViewHolder(MyVH holder, int position) {
        DataInfo info = mInfoList.get(position);
        holder.setImageView(info.getIconId());
        holder.setTextView(info.getTitle());
    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }

    class MyVH extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;

        public MyVH(View itemView) {
            super(itemView);
            //use the view provided to the constructor to create a reference to the widgets
            //in the layout, so that way it cant be populated
            setupImageView(itemView);
            setupTextView(itemView);
        }

        private void setupImageView(final View view){
            imageView = (ImageView) view.findViewById(R.id.imageview);

            /*
            this is the easiest way to implement listeners on the views

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //to get the row position use getAdapterPosition()
                    String text = "AdapterPosition "+ getAdapterPosition() +
                            " LayoutPosition " + getLayoutPosition();
                    Toast.makeText(view.getContext(), "ImageView " + text, Toast.LENGTH_LONG).show();
                }
            });
            */
        }

        private void setupTextView(final View view){
            textView = (TextView)view.findViewById(R.id.textview);

            /*
            this is the easiest way to implement listeners on the views

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //to get the row position use getAdapterPosition()
                    String text = "AdapterPosition "+ getAdapterPosition() +
                            " LayoutPosition " + getLayoutPosition();
                    Toast.makeText(view.getContext(), "Text " + text, Toast.LENGTH_LONG).show();
                }
            });
            //delete row
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    mInfoList.remove(position);
                    notifyItemRemoved(position);
                    return true;
                }
            });

            */
        }

        public  void setTextView(String text){
            textView.setText(text);
        }

        public void setImageView( int iconId){
            imageView.setImageResource(iconId);
        }
    }






}
