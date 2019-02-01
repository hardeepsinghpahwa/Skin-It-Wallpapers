package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.hardeep.wallpapers.R;
import com.example.hardeep.wallpapers.View_Category_Images;
import com.example.hardeep.wallpapers.categories_card_details;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Categories extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;
    RecyclerView recyclerview;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<categories_card_details,ViewHolderClass> adapter;


    public Categories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_categories, container, false);

        FirebaseApp.initializeApp(getActivity());

        progressBar=v.findViewById(R.id.categoriespbar);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Categories");
        recyclerview=v.findViewById(R.id.categoriesrecyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return v;
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
    public void onStart() {
        super.onStart();
        adapter = new FirebaseRecyclerAdapter<categories_card_details, ViewHolderClass>
                (categories_card_details.class, R.layout.card_view, ViewHolderClass.class, databaseReference) {

            @Override
            public void onViewAttachedToWindow(@NonNull ViewHolderClass holder) {
                super.onViewAttachedToWindow(holder);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            protected void populateViewHolder(ViewHolderClass viewHolder, final categories_card_details model, int position) {

                viewHolder.setText(model.getText());
                viewHolder.setImage(getActivity(), model.getImage());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        {
                            Intent i = new Intent(getActivity(), View_Category_Images.class);
                            i.putExtra("category", model.getText());
                            startActivity(i);
                        }
                    }
                });
            }
        };


        adapter.notifyDataSetChanged();
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
