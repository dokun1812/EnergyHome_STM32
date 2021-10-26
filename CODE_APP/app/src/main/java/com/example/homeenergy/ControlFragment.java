package com.example.homeenergy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ControlFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myData = database.getReference("Control");
    Switch swdev1,swdev2,swdev3,swAll;
    String a,b,c,d;
    String a1 = "1", b1 = "1", c1 = "1", d1 = "1";
    int state = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_control, container, false);
        swdev1 = view.findViewById(R.id.swdev1);
        swdev2 = view.findViewById(R.id.swdev2);
        swdev3 = view.findViewById(R.id.swdev3);
        swAll = view.findViewById(R.id.swAll);

        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a =snapshot.child("Device 1").getValue().toString();
                b =snapshot.child("Device 2").getValue().toString();
                c =snapshot.child("Device 3").getValue().toString();
                d =snapshot.child("All Device").getValue().toString();


                swdev1.setChecked(a1.equals(a));
                swdev2.setChecked(b1.equals(b));
                swdev3.setChecked(c1.equals(c));
                swAll.setChecked(d1.equals(d));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            swdev1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(state == 1) {
                        if (isChecked) {
                            myData.child("Device 1").setValue("1");
                            Toast.makeText(getContext(), "Device 1 is on !", Toast.LENGTH_LONG).show();
                        } else {
                            myData.child("Device 1").setValue("0");
                            Toast.makeText(getContext(), "Device 1 is off !", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        swdev1.setChecked(false);
                    }
                }
            });
        swdev2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(state == 1) {
                    if (isChecked) {
                        myData.child("Device 2").setValue("1");
                        Toast.makeText(getContext(), "Device 2 is on !", Toast.LENGTH_LONG).show();
                    } else {
                        myData.child("Device 2").setValue("0");
                        Toast.makeText(getContext(), "Device 2 is off !", Toast.LENGTH_LONG).show();
                    }
                }else {
                    swdev2.setChecked(false);
                }
            }
        });

        swdev3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(state == 1) {
                    if (isChecked){
                        myData.child("Device 3").setValue("1");
                        Toast.makeText(getContext(),"Device 3 is on !",Toast.LENGTH_LONG).show();
                    }
                    else{
                        myData.child("Device 3").setValue("0");
                        Toast.makeText(getContext(),"Device 3 is off !",Toast.LENGTH_LONG).show();
                    }
                }else {
                    swdev3.setChecked(false);
                }
            }
        });

        swAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    state = 1;
                    //Log.d("Gia tri State:",state.toString());
                    myData.child("All Device").setValue("1");
                    Toast.makeText(getContext(),"All device is on !",Toast.LENGTH_LONG).show();
                }
                else{
                    state = 0;
                    //Log.d("Gia tri State:",state);
                    myData.child("All Device").setValue("0");
                    Toast.makeText(getContext(),"All device is off !",Toast.LENGTH_LONG).show();
                    myData.child("Device 1").setValue("0");
                    myData.child("Device 2").setValue("0");
                    myData.child("Device 3").setValue("0");
                }
            }
        });
        return view;



    }
}