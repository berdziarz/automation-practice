package rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.berdzik.rest.Comment;
import org.berdzik.rest.Post;
import org.berdzik.rest.ResourcePath;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostsTests extends RestApiTests {

    @Test
    public void getAllPosts() {
        Response response = getAllResources(ResourcePath.POSTS.getPath());
        Post[] posts = response.getBody().as(Post[].class);

        assertThat(posts).hasSize(100);
        assertThat(posts).allSatisfy(e ->
                assertThat(e.getTitle()).as("Post with id=" + e.getId() + " tile assertion").isNotEmpty());
    }

    @Test
    public void getPostById() {
        Post expectedPost = Post.builder()
                .id(5)
                .userId(1)
                .title("nesciunt quas odio")
                .build();

        Response response = getResourcesById(5, ResourcePath.POSTS.getPath());
        Post post = response.getBody().as(Post.class);

        assertThat(expectedPost)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(post);
    }

    @Test
    public void getNonExistPost() {
        checkGetNonExistResource(999, ResourcePath.POSTS.getPath());
    }

    @Test
    public void getPostByUserId() {
        long userId = 10;

        Response response = getResourceByUserId(userId, ResourcePath.POSTS.getPath());
        Post[] posts = response.getBody().as(Post[].class);

        assertThat(posts).hasSize(10);
        assertThat(posts).allSatisfy(e -> assertThat(e.getUserId()).isEqualTo(userId));
    }

    @Test
    public void createNewPost() {
        Post newPost = Post.builder()
                .userId(1)
                .title("test")
                .body("test")
                .build();

        Response response = createNewResource(newPost, ResourcePath.POSTS.getPath());
        Post post = response.getBody().as(Post.class);

        assertThat(post.getId()).isEqualTo(101L);
    }

    @Test
    public void updatePost() {
        Post updatedPost = Post.builder()
                .id(10)
                .userId(5)
                .title("test")
                .body("test")
                .build();

        Response response = updateResource(updatedPost, ResourcePath.POSTS.getPath());
        Post post = response.getBody().as(Post.class);

        assertThat(post.getId()).isEqualTo(updatedPost.getId());
    }

    @Test
    public void patchPost() {
        Post updatedPost = Post.builder()
                .id(10)
                .userId(5)
                .build();

        Response response = patchResource(updatedPost, ResourcePath.POSTS.getPath());
        Post post = response.getBody().as(Post.class);

        assertThat(post.getId()).isEqualTo(updatedPost.getId());
    }

    @Test
    public void deletePost() {
        checkDeleteResource(1, ResourcePath.POSTS.getPath());
    }

    @Test
    public void getPostComments() {
        long postId = 5;
        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.POSTS.getPath())
                .pathParam("id", postId)
                .when()
                .get("/{id}" + ResourcePath.COMMENTS.getPath())
                .then()
                .statusCode(200)
                .extract().response();
        Comment[] comments = response.getBody().as(Comment[].class);
        assertThat(comments).hasSize(5);
        assertThat(comments).allSatisfy(e -> assertThat(e.getPostId()).isEqualTo(postId));
    }
}
