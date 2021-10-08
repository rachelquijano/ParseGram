package com.rachelquijano.parsegram;

import android.content.Context;
import android.text.Layout;
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

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    private TextView tvUserName;
    private ImageView ivPost;
    private TextView tvDescription;
    private TextView tvPostUserName;
    private ImageView ivProfilePhoto;

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
        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvPostUserName.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            Glide.with(context).load(post.getUser().getParseFile("profilePhoto").getUrl()).into(ivProfilePhoto);
            if(image != null)
                Glide.with(context).load(post.getImage().getUrl()).into(ivPost);
        }
    }
}
