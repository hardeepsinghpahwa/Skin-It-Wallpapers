package com.example.hardeep.wallpapers.View_Pager_Frags;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hardeep.wallpapers.R;

import java.io.File;
import java.util.ArrayList;

public class Downloaded_Images extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    String path;
    TextView noimages;
    File[] files;
    Local_Images_Adapter adapter;

    public Downloaded_Images() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_downloaded__images, container, false);
        if(!checkWriteExternalPermission())
        {            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }
        else {


            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Downloaded Images";
            File directory = new File(path); //path is the string specifying your directory path.
            files = directory.listFiles();
            noimages=v.findViewById(R.id.noimagestext);

            adapter=new Local_Images_Adapter(getfiles(),getActivity());
            recyclerView = v.findViewById(R.id.downloadedimgsrecyclerview);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            recyclerView.setAdapter(adapter);

            if(adapter.getItemCount()!=0)
            {
                noimages.setVisibility(View.GONE);
            }

        }
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    private boolean checkWriteExternalPermission()
    {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public ArrayList<Uri> getfiles()
    {
        ArrayList<Uri> imgs=new ArrayList<>();

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Downloaded Images");

        if(storageDir.exists())
        {

            File[] files=storageDir.listFiles();

            for(int i=0;i<files.length;i++)
            {
                File file=files[i];

                imgs.add(Uri.fromFile(file));
            }
        }

        return  imgs;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
