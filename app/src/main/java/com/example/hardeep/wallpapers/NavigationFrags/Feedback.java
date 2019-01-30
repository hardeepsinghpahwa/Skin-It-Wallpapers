package com.example.hardeep.wallpapers.NavigationFrags;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hardeep.wallpapers.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feedback extends Fragment {

    EditText email,feedback;
    DatabaseReference databaseReference;
    Button submit;
    String mail,feed;

    public Feedback() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        email=v.findViewById(R.id.emailtext);
        submit=v.findViewById(R.id.submitbutton);
        feedback=v.findViewById(R.id.feedbacktext);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Validate_email(mail))
                {
                    email.setError("Incorrect email");
                    email.requestFocus();
                }
                else
                {
                    databaseReference.child("feedback").child(UUID.randomUUID().toString()).child("email").setValue(mail);
                    databaseReference.child("feedback").child(UUID.randomUUID().toString()).child("feedback").setValue(feed);
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
