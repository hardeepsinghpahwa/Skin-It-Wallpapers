package com.pahwa.hardeep.wallpapers.NavigationFrags;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pahwa.hardeep.wallpapers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Feedback extends Fragment {

    EditText email,feedback;
    DatabaseReference databaseReference;
    Button submit;

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
                if(!Validate_email(email.getText().toString()))
                {
                    email.setError("Incorrect email");
                    email.requestFocus();
                }
                else
                {
                    final ProgressDialog p=new ProgressDialog(getActivity());
                    p.setTitle("Sending Feedback");
                    p.setCancelable(false);
                    p.show();
                    String a=UUID.randomUUID().toString();
                    databaseReference.child("Feedback").child(a).child("email").setValue(email.getText().toString());
                    databaseReference.child("Feedback").child(a).child("feedback").setValue(feedback.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            p.dismiss();
                            email.setText("");
                            feedback.setText("");
                            Toast.makeText(getActivity(),"Thank You For the Feedback",Toast.LENGTH_SHORT).show();
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
