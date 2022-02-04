package kg.geektech.postapplesson2.data.remote;

import java.util.List;

import kg.geektech.postapplesson2.data.models.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApi {

    @GET("/posts")
    Call<List<Post>> getPosts();


    @POST("/posts")
    Call<Post> createPost(
            @Body Post post
    );

    @PUT("/posts/{postId}")
    Call<Post> updatePost(
            @Path("postId") int postId,
            @Body Post post
    );
    @DELETE("/posts/{postId}")
    Call<Post> deletePost(
            @Path("postId") int postId
    );

}
