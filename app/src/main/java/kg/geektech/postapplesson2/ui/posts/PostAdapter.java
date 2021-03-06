package kg.geektech.postapplesson2.ui.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.postapplesson2.data.models.Post;
import kg.geektech.postapplesson2.databinding.ItemPostBinding;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<Post> posts = new ArrayList<>();

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));

        holder.itemView.setOnClickListener(v -> listener.onClick(posts.get(position)));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(posts.get(position),position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected static class PostViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;
        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post) {
            binding.tvUserId.setText(post.getUserId());
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }
    }
}