package com.example.homeenergy;

import static java.lang.Integer.parseInt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ValueFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myData = database.getReference("Value");
    DatabaseReference myControl = database.getReference("Control");
    TextView ampere, voltage, power, energy, frenquency;
    Button saveBtn;
    EditText threshold;
    String amp, vol, pow, ene, fre,getThreshold;
    int thr = 0;
    int thrHasSet = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_value, container, false);
        ampere = view.findViewById(R.id.valueAmpere);
        voltage = view.findViewById(R.id.valueVol);
        power = view.findViewById(R.id.valuePow);
        energy = view.findViewById(R.id.valueEng);
        frenquency = view.findViewById(R.id.valueFre);
        threshold = view.findViewById(R.id.editThreshold);
        saveBtn = view.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thr = parseInt(threshold.getText().toString());
                if(thr<10){
                    myControl.child("Threshold").setValue("000"+thr);
                }else if(thr<100){
                    myControl.child("Threshold").setValue("00"+thr);
                }
                else if(thr<1000){
                    myControl.child("Threshold").setValue("0"+thr);
                }
                else {
                    myControl.child("Threshold").setValue(""+thr);
                }

                Toast.makeText(getContext(), "Threshold is set by " + thr, Toast.LENGTH_LONG).show();
            }
        });

        myControl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getThreshold = snapshot.child("Threshold").getValue().toString();
                thrHasSet = parseInt(getThreshold);
                threshold.setHint("Has Set: " + thrHasSet + "W");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                amp =snapshot.child("ampere").getValue().toString();
                vol =snapshot.child("voltage").getValue().toString();
                pow =snapshot.child("power").getValue().toString();
                ene =snapshot.child("energy").getValue().toString();
                fre =snapshot.child("frequency").getValue().toString();

                ampere.setText(amp + " A");
                voltage.setText(vol + " V");
                power.setText(pow + " W");
                energy.setText(ene + " Wh");
                frenquency.setText(fre + " Hz");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}