package kg.geektech.postapplesson2.data.remote;

import java.util.List;

import kg.geektech.postapplesson2.data.models.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostApi {

    @GET("/posts")
    Call<List<Post>> getPosts();


    @POST("/posts")
    Call<Post> createPost(
            @Body Post post
    );
}
