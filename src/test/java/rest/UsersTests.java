package rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.berdzik.rest.Album;
import org.berdzik.rest.Post;
import org.berdzik.rest.ResourcePath;
import org.berdzik.rest.Todo;
import org.berdzik.rest.user.Company;
import org.berdzik.rest.user.Geolocation;
import org.berdzik.rest.user.User;
import org.berdzik.rest.user.UserAddress;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTests extends RestApiTests {

    @Test
    public void getAllUsers() {
        Response response = getAllResources(ResourcePath.USERS.getPath());
        User[] users = response.getBody().as(User[].class);

        assertThat(users).hasSize(10);
        assertThat(users).allSatisfy(e ->
                assertThat(e.getAddress().getCity()).as("User with id=" + e.getId() + " address assertion").isNotEmpty());
        assertThat(users).allSatisfy(e ->
                assertThat(e.getEmail()).as("User with id=" + e.getId() + " email assertion").matches(EMAIL_REGEX));
    }

    @Test
    public void getUserById() {
        Geolocation geo = Geolocation.builder()
                .lat(-37.3159)
                .lng(81.1496).build();
        UserAddress address = UserAddress.builder()
                .street("Kulas Light")
                .city("Gwenborough")
                .geo(geo)
                .suite("Apt. 556")
                .zipcode("92998-3874").build();
        Company company = Company.builder()
                .name("Romaguera-Crona")
                .bs("harness real-time e-markets")
                .catchPhrase("Multi-layered client-server neural-net").build();
        User expectedUser = User.builder()
                .id(1)
                .address(address)
                .company(company)
                .email("Sincere@april.biz")
                .name("Leanne Graham")
                .username("Bret")
                .website("hildegard.org")
                .phone("1-770-736-8031 x56442").build();

        Response response = getResourcesById(1, ResourcePath.USERS.getPath());
        User user = response.getBody().as(User.class);

        assertThat(expectedUser)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(user);
    }

    @Test
    public void getUserAlbums() {
        long userId = 1;

        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.USERS.getPath())
                .pathParam("id", userId)
                .when()
                .get("/{id}" + ResourcePath.ALBUMS.getPath())
                .then()
                .statusCode(200)
                .extract().response();
        Album[] albums = response.getBody().as(Album[].class);

        assertThat(albums).hasSize(10);
        assertThat(albums).allSatisfy(e -> assertThat(e.getUserId()).isEqualTo(userId));
    }

    @Test
    public void getUserTodos() {
        long userId = 1;

        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.USERS.getPath())
                .pathParam("id", userId)
                .when()
                .get("/{id}" + ResourcePath.TODOS.getPath())
                .then()
                .statusCode(200)
                .extract().response();
        Todo[] todos = response.getBody().as(Todo[].class);

        assertThat(todos).hasSize(20);
        assertThat(todos).allSatisfy(e -> assertThat(e.getUserId()).isEqualTo(userId));
    }

    @Test
    public void getUserPosts() {
        long userId = 1;

        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.USERS.getPath())
                .pathParam("id", userId)
                .when()
                .get("/{id}" + ResourcePath.POSTS.getPath())
                .then()
                .statusCode(200)
                .extract().response();
        Post[] posts = response.getBody().as(Post[].class);

        assertThat(posts).hasSize(10);
        assertThat(posts).allSatisfy(e -> assertThat(e.getUserId()).isEqualTo(userId));
    }

    @Test
    public void getNonExistUser() {
        checkGetNonExistResource(999, ResourcePath.USERS.getPath());
    }

    @Test
    public void createNewUser() {
        Geolocation geo = Geolocation.builder()
                .lat(-37.3159)
                .lng(81.1496).build();
        UserAddress address = UserAddress.builder()
                .street("Kulas Light")
                .city("Gwenborough")
                .geo(geo)
                .suite("Apt. 556")
                .zipcode("92998-3874").build();
        Company company = Company.builder()
                .name("Romaguera-Crona")
                .bs("harness real-time e-markets")
                .catchPhrase("Multi-layered client-server neural-net").build();
        User newUser = User.builder()
                .address(address)
                .company(company)
                .email("Sincere@april.biz")
                .name("Leanne Graham")
                .username("Bret")
                .website("hildegard.org")
                .phone("1-770-736-8031 x56442").build();

        Response response = createNewResource(newUser, ResourcePath.USERS.getPath());
        User user = response.getBody().as(User.class);

        assertThat(user.getId()).isEqualTo(11L);
    }

    @Test
    public void updateUser() {
        Geolocation geo = Geolocation.builder()
                .lat(-37.3159)
                .lng(81.1496).build();
        UserAddress address = UserAddress.builder()
                .street("Kulas Light")
                .city("Gwenborough")
                .geo(geo)
                .suite("Apt. 556")
                .zipcode("92998-3874").build();
        Company company = Company.builder()
                .name("Romaguera-Crona")
                .bs("harness real-time e-markets")
                .catchPhrase("Multi-layered client-server neural-net").build();
        User updatedUser = User.builder()
                .id(1)
                .address(address)
                .company(company)
                .email("Sincere@april.biz")
                .name("Leanne Graham")
                .username("Bret")
                .website("hildegard.org")
                .phone("1-770-736-8031 x56442").build();


        Response response = updateResource(updatedUser, ResourcePath.USERS.getPath());
        User user = response.getBody().as(User.class);

        assertThat(user.getId()).isEqualTo(updatedUser.getId());
    }

    @Test
    public void patchUser() {
        User updatedUser = User.builder()
                .id(1)
                .username("Berdzik").build();

        Response response = patchResource(updatedUser, ResourcePath.USERS.getPath());
        User user = response.getBody().as(User.class);

        assertThat(user.getId()).isEqualTo(updatedUser.getId());
    }

    @Test
    public void deleteUser() {
        checkDeleteResource(1, ResourcePath.USERS.getPath());
    }
}
