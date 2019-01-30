package com.example.hardeep.wallpapers.NavigationFrags;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hardeep.wallpapers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Upload_Images extends Fragment {

    Button uploadimage, selectimage;
    private static final int PICK_IMAGE_MULTIPLE = 1;
    Uri fileuri;
    int selecteditems;
    TextView selimgs;
    ProgressDialog progressDialog;
    StorageReference reference;
    int PICK_IMG = 1;
    ArrayList<Uri> uris;
    ArrayList<String> urls;
    int j;
    EditText email;

    public Upload_Images() {
        // Required empty public constructor
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (Objects.equals(uri.getScheme(), "content")) {
            Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                assert cursor != null;
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            assert result != null;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                selecteditems = data.getClipData().getItemCount();
                selimgs.setText("Selected Images : "+Integer.toString(selecteditems));
                for (int k = 0; k < selecteditems; k++) {
                    fileuri = data.getClipData().getItemAt(k).getUri();
                    uris.add(fileuri);

                }

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_upload__images, container, false);
        uris = new ArrayList<>();
        uris.clear();
        email=v.findViewById(R.id.enteremail);
        urls = new ArrayList<>();
        urls.clear();
        selectimage = v.findViewById(R.id.selectbutton);
        uploadimage = v.findViewById(R.id.uploadbutton);
        selimgs=v.findViewById(R.id.imagesnumber);


        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Validate_email(email.getText().toString()))
                {
                    email.setError("Incorrect email");
                    email.requestFocus();
                }
                else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMG);
                }
            }
        });

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Sending Images");
                progressDialog.show();

                for (j = 0; j < uris.size(); j++) {

                    reference = FirebaseStorage.getInstance().getReference().child("UserImages" + "/" + email+"/"+UUID.randomUUID().toString());
                    reference.putFile(uris.get(j)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(j==uris.size())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Sent!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Some Error occured : Sending Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        return v;

    }

    private boolean Validate_email(String s) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email.getText().toString());

        return matcher.matches();
    }

}
