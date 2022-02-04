package kg.geektech.postapplesson2.ui.form;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import kg.geektech.postapplesson2.App;
import kg.geektech.postapplesson2.data.models.Post;
import kg.geektech.postapplesson2.databinding.FragmentFormBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private static final int groupId = 38;
    private static final String userId = "Azhibraimov";


    private Post post;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = (Post) getArguments().getSerializable("post");

        if (post != null) {
            binding.etTitle.setText(post.getTitle());
            binding.etContent.setText(post.getContent());
            binding.btnSend.setText("Upgrade");
        }

        binding.btnSend.setOnClickListener(v -> {
            if (post == null) {
                String title = binding.etTitle.getText().toString();
                String content = binding.etContent.getText().toString();
                Post post = new Post(
                        title,
                        content,
                        userId,
                        groupId
                );
                App.api.createPost(post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            requireActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            } else {
                String title = binding.etTitle.getText().toString();
                String content = binding.etContent.getText().toString();
                Post post = new Post(
                        title,
                        content,
                        userId,
                        groupId
                );
                post.setId(this.post.getId());
                App.api.updatePost(post.getId(),post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            requireActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            }
        });
    }
}