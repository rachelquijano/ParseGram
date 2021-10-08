package com.rachelquijano.parsegram;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    private TextView tvUserName;
    private ImageView ivPost;
    private TextView tvDescription;
    private TextView tvPostUserName;
    private ImageView ivProfilePhoto;
    private TextView tvCreatedAt;

    //Date variables
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUsername);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPostUserName = itemView.findViewById(R.id.tvUserNamePost);
            ivProfilePhoto = itemView.findViewById(R.id.ivProfilePhoto);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvPostUserName.setText(post.getUser().getUsername());
            tvCreatedAt.setText(getRelativeTimeAgo(post.getCreatedAt().toString()) + " ago");
            ParseFile image = post.getImage();
            Glide.with(context).load(post.getUser().getParseFile("profilePhoto").getUrl()).into(ivProfilePhoto);
            if(image != null)
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
        }
    }


    public String getRelativeTimeAgo(String rawJsonDate) {
        String postFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(postFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "1 minute";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " minutes";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "1 hour";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " hours";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "24 hours ago";
            } else {
                return diff / DAY_MILLIS + "d";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
