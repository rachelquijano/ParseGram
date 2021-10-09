package com.rachelquijano.parsegram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.rachelquijano.parsegram.LoginActivity;
import com.rachelquijano.parsegram.R;

import java.io.File;
import java.net.URI;

public class SettingsFragment extends Fragment {

    private Button btnLogOut;
    private Button btnChangePicture;
    private File photoFile;
    public String fileName = "photoFile.jpg";

    private EditText etChangeUsername;
    private Button btnChangeUsername;
    private EditText etChangePassword;
    private Button btnChangePassword;
    private EditText etChangeName;
    private Button btnChangeName;
    private EditText etChangeDescription;
    private Button btnChangeDescription;
    private BottomNavigationView bottomNavigationView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode==getActivity().RESULT_OK){
                    String path = data.getData().getPath();
                    photoFile = new File(path);
                    ParseFile file = new ParseFile(photoFile);
                    ParseUser.getCurrentUser().put("profilePhoto", file);
                    ParseUser.getCurrentUser().saveInBackground();
                    goProfileFragment();
                }
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnChangePicture = view.findViewById(R.id.btnChangeProfilePicture);
        btnChangeUsername = view.findViewById(R.id.btnChangeUsername);
        etChangeUsername = view.findViewById(R.id.etChangeUsername);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        etChangePassword = view.findViewById(R.id.etChangePassword);
        btnChangeName = view.findViewById(R.id.btnChangeName);
        etChangeName = view.findViewById(R.id.etChangeName);
        btnChangeDescription = view.findViewById(R.id.btnChangeDescription);
        etChangeDescription = view.findViewById(R.id.etChangeDesciption);

        btnChangePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                startActivityForResult(fileIntent, 1);
            }
        });


        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etChangeUsername.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getContext(), "New Username cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.equals(ParseUser.getCurrentUser().get("name"))){
                    Toast.makeText(getContext(), "New Username cannot be the same name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser.getCurrentUser().setUsername(name);
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(getContext(), "Username updated successfully!", Toast.LENGTH_SHORT).show();
                goProfileFragment();
            }
        });
        
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etChangePassword.getText().toString();
                if(password.isEmpty()){
                    Toast.makeText(getContext(), "New Password cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals(ParseUser.getCurrentUser().get("password"))){
                    Toast.makeText(getContext(), "New Password cannot be the same password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser.getCurrentUser().setPassword(password);
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(getContext(), "Password updated successfully!", Toast.LENGTH_SHORT).show();
                goProfileFragment();
            }
        });

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etChangeName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getContext(), "New name cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.equals(ParseUser.getCurrentUser().get("name"))){
                    Toast.makeText(getContext(), "New name cannot be the same name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser.getCurrentUser().put("name", name);
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(getContext(), "Name updated successfully!", Toast.LENGTH_SHORT).show();
                goProfileFragment();
            }
        });

        btnChangeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etChangeDescription.getText().toString();
                ParseUser.getCurrentUser().put("description", description);
                ParseUser.getCurrentUser().saveInBackground();
                Toast.makeText(getContext(), "Description updated successfully!", Toast.LENGTH_SHORT).show();
                goProfileFragment();
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                goLoginActivity();

            }
        });
    }
    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
    private void goProfileFragment() {
        Fragment fragment = new ProfileFragment();
        getFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
    }
}
