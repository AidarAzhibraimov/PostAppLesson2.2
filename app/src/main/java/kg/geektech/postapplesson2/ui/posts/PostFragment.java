package kg.geektech.postapplesson2.ui.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kg.geektech.postapplesson2.App;
import kg.geektech.postapplesson2.R;
import kg.geektech.postapplesson2.base.BaseFragment;
import kg.geektech.postapplesson2.data.models.Post;
import kg.geektech.postapplesson2.databinding.FragmentPostBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostFragment extends Fragment {
    private PostAdapter adapter;
    private FragmentPostBinding binding;
    private NavController controller;

    private List<Post> posts;

    public PostFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater,container,false);
        adapter = new PostAdapter();
        controller = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);

        binding.fab.setOnClickListener(v -> controller.navigate(R.id.action_postFragment_to_formFragment));

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onClick(Post post) {
                controller.navigate(PostFragmentDirections.actionPostFragmentToFormFragment(post));
            }

            @Override
            public void onLongClick(Post post, int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Удалить публикацию?");
                alert.setMessage("Вы действительное ходите удалить публикацию?");
                alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        posts.remove(post);
                        adapter.setPosts(posts);
                        App.api.deletePost(post.getId()).enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, Response<Post> response) {

                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {

                            }
                        });
                    }
                });
                alert.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null ){
                    posts = response.body();
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}