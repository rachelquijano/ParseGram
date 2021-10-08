package com.rachelquijano.parsegram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rachelquijano.parsegram.Post;
import com.rachelquijano.parsegram.R;

import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView tvProfileUserName;
    private TextView tvProfileName;
    private TextView tvProfileDescription;
    private ImageView ivProfilePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment childFragment = new PostsFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flProfileContainer, childFragment).commit();

        tvProfileUserName = view.findViewById(R.id.tvProfileUserName);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileDescription = view.findViewById(R.id.tvProfileDescription);
        ivProfilePhoto = view.findViewById(R.id.ivProfileImage);

        tvProfileUserName.setText(ParseUser.getCurrentUser().getUsername());
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile("profilePhoto").getUrl()).into(ivProfilePhoto);
        tvProfileName.setText(ParseUser.getCurrentUser().get("name").toString());
        tvProfileDescription.setText(ParseUser.getCurrentUser().get("description").toString());
    }
}
