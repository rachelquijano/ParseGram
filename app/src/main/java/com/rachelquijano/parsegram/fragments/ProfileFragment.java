package com.rachelquijano.parsegram.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
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
    private TextView tvNumPosts;

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
        tvNumPosts = view.findViewById(R.id.tvNumPosts);

        tvProfileUserName.setText(ParseUser.getCurrentUser().getUsername());
        Glide.with(getContext()).load(ParseUser.getCurrentUser().getParseFile("profilePhoto").getUrl()).into(ivProfilePhoto);
        tvProfileName.setText(ParseUser.getCurrentUser().get("name").toString());
        tvProfileDescription.setText(ParseUser.getCurrentUser().get("description").toString());
        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    return;
                }
                try {
                    tvNumPosts.setText(String.valueOf(query.count()));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });
    }
}

