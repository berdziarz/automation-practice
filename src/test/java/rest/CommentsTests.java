package rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.berdzik.rest.Comment;
import org.berdzik.rest.ResourcePath;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsTests extends RestApiTests {

    @Test
    public void getAllComments() {
        Response response = getAllResources(ResourcePath.COMMENTS.getPath());
        Comment[] comments = response.getBody().as(Comment[].class);

        assertThat(comments).hasSize(500);
        assertThat(comments).allSatisfy(e ->
                assertThat(e.getEmail()).as("Comment with id=" + e.getId() + " email assertion").matches(EMAIL_REGEX));
    }

    @Test
    public void getCommentById() {
        Comment expectedComment = Comment.builder()
                .id(5)
                .postId(1)
                .email("Hayden@althea.biz")
                .build();

        Response response = getResourcesById(5, ResourcePath.COMMENTS.getPath());
        Comment comment = response.getBody().as(Comment.class);

        assertThat(expectedComment)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(comment);
    }

    @Test
    public void getNonExistComment() {
        checkGetNonExistResource(999, ResourcePath.COMMENTS.getPath());
    }

    @Test
    public void getCommentByPostId() {
        long postId = 10;

        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.COMMENTS.getPath())
                .queryParam("postId", postId)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        Comment[] comments = response.getBody().as(Comment[].class);

        assertThat(comments).hasSize(5);
        assertThat(comments).allSatisfy(e -> assertThat(e.getPostId()).isEqualTo(postId));
    }

    @Test
    public void createNewComment() {
        Comment newComment = Comment.builder()
                .postId(50)
                .email("test@email.com")
                .name("name")
                .body("body").build();

        Response response = createNewResource(newComment, ResourcePath.COMMENTS.getPath());
        Comment comment = response.getBody().as(Comment.class);

        assertThat(comment.getId()).isEqualTo(501L);
    }


    @Test
    public void updateComment() {
        Comment updatedComment = Comment.builder()
                .id(10)
                .postId(50)
                .email("test@email.com")
                .name("name")
                .body("body").build();

        Response response = updateResource(updatedComment, ResourcePath.COMMENTS.getPath());
        Comment comment = response.getBody().as(Comment.class);

        assertThat(comment.getId()).isEqualTo(updatedComment.getId());
    }

    @Test
    public void patchComment() {
        Comment updatedComment = Comment.builder()
                .id(10)
                .email("zmiana@emaila.com")
                .build();

        Response response = patchResource(updatedComment, ResourcePath.COMMENTS.getPath());
        Comment comment = response.getBody().as(Comment.class);

        assertThat(comment.getId()).isEqualTo(updatedComment.getId());
    }

    @Test
    public void deleteComment() {
        checkDeleteResource(1, ResourcePath.COMMENTS.getPath());
    }
}
