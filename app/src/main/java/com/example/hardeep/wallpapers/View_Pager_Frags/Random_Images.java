package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hardeep.wallpapers.Image_Download;
import com.example.hardeep.wallpapers.R;
import com.example.hardeep.wallpapers.getimage;
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
            }

            @Override
            protected void populateViewHolder(ImgViewHolder viewHolder, getimage model, final int position) {
                viewHolder.setImage(getActivity(), model.getImage());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        url = getItem(position).getImage();


                        Intent intent = new Intent(getActivity(), Image_Download.class);
                        intent.putExtra("url", url);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
