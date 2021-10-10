package com.rachelquijano.parsegram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.rachelquijano.parsegram.Post;
import com.rachelquijano.parsegram.PostsAdapter;
import com.rachelquijano.parsegram.PostsProfileAdapter;
import com.rachelquijano.parsegram.R;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    protected RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected PostsProfileAdapter profileAdapter;
    protected List<Post> allPosts;
    private TextView tvNoPosts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNoPosts = view.findViewById(R.id.tvNoPosts);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();


        if(getParentFragment() != null){
            profileAdapter = new PostsProfileAdapter(getContext(), allPosts);
            rvPosts.setAdapter(profileAdapter);
            rvPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }else{
            adapter = new PostsAdapter(getContext(), allPosts);
            rvPosts.setAdapter(adapter);
            rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        queryPosts();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        if(getParentFragment() != null)
            query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        try {
            if(query.count() != 0){
                ViewGroup parent = (ViewGroup) tvNoPosts.getParent();
                parent.removeView(tvNoPosts);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                allPosts.addAll(posts);
                if(getParentFragment() != null){
                    profileAdapter.notifyDataSetChanged();
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}