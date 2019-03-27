package com.pahwa.hardeep.wallpapers.View_Pager_Frags;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pahwa.hardeep.wallpapers.Image_Download;
import com.pahwa.hardeep.wallpapers.R;
import com.pahwa.hardeep.wallpapers.getimage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Random_Images extends Fragment{

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<getimage,ImgViewHolder> firebaseRecyclerAdapter;
    DatabaseReference dataref;
    ProgressBar p;
    boolean alreadyExecuted=false;
    int pos=0;
    TextView loading;
    String url;

    public Random_Images() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_random__images, container, false);

        FirebaseApp.initializeApp(getActivity());

        recyclerView=v.findViewById(R.id.randomrecyclerview);
        dataref=FirebaseDatabase.getInstance().getReference().child("Random");
        p=v.findViewById(R.id.prbarrandom);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<getimage, ImgViewHolder>
                (getimage.class, R.layout.imageformat, ImgViewHolder.class, dataref) {


            @Override
            public void onViewAttachedToWindow(@NonNull ImgViewHolder holder) {
                super.onViewAttachedToWindow(holder);
                p.setVisibility(View.GONE);
                if(!alreadyExecuted) {
                    recyclerView.smoothScrollToPosition(pos);
                    alreadyExecuted = true;
                }
            }

            @Override
            protected void populateViewHolder(ImgViewHolder viewHolder, getimage model, final int position) {
                viewHolder.setImage(getActivity(), model.getImage());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        url = getItem(position).getImage();

                        Log.i("URL", url);
                        Intent intent = new Intent(getActivity(), Image_Download.class);
                        intent.putExtra("url", url);
                        pos=position;
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
            Log.i("po",Integer.toString(pos));
            alreadyExecuted=false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
