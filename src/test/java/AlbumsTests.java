import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.berdzik.rest.Album;
import org.berdzik.rest.Photo;
import org.berdzik.rest.ResourcePath;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlbumsTests extends RestApiTests {

    @Test
    public void getAllAlbums() {
        Response response = getAllResources(ResourcePath.ALBUMS.getPath());
        Album[] albums = response.getBody().as(Album[].class);

        assertThat(albums).hasSize(100);
        assertThat(albums).allSatisfy(e -> assertThat(e.getTitle()).as("Album with id=" + e.getId() + " title assertion").isNotEmpty());
    }

    @Test
    public void getAlbumById() {
        Album expectedAlbum = Album.builder()
                .id(1)
                .userId(1)
                .title("quidem molestiae enim").build();

        Response response = getResourcesById(1, ResourcePath.ALBUMS.getPath());
        Album album = response.getBody().as(Album.class);

        assertThat(album)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(expectedAlbum);
    }

    @Test
    public void getNonExistAlbum() {
        checkGetNonExistResource(999, ResourcePath.ALBUMS.getPath());
    }

    @Test
    public void geAlbumsByUserId() {
        long userId = 1;

        Response response = getResourceByUserId(userId, ResourcePath.ALBUMS.getPath());
        Album[] albums = response.getBody().as(Album[].class);

        assertThat(albums).hasSize(10);
        assertThat(albums).allSatisfy(e -> assertThat(e.getUserId()).isEqualTo(userId));
    }

    @Test
    public void getAlbumPhotos() {
        long albumId = 1;

        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.ALBUMS.getPath())
                .pathParam("id", albumId)
                .when()
                .get("/{id}" + ResourcePath.PHOTOS.getPath())
                .then()
                .statusCode(200)
                .extract().response();
        Photo[] photos = response.getBody().as(Photo[].class);

        assertThat(photos).hasSize(50);
        assertThat(photos).allSatisfy(e -> assertThat(e.getAlbumId()).isEqualTo(albumId));
    }

    @Test
    public void createNewAlbum() {
        Album newAlbum = Album.builder()
                .userId(2)
                .title("album")
                .build();

        Response response = createNewResource(newAlbum, ResourcePath.ALBUMS.getPath());
        Album album = response.getBody().as(Album.class);

        assertThat(album.getId()).isEqualTo(101L);
    }


    @Test
    public void updateAlbum() {
        Album updatedAlbum = Album.builder()
                .id(10)
                .userId(10)
                .title("Albumik").build();

        Response response = updateResource(updatedAlbum, ResourcePath.ALBUMS.getPath());
        Album album = response.getBody().as(Album.class);

        assertThat(album.getId()).isEqualTo(updatedAlbum.getId());
    }

    @Test
    public void patchAlbum() {
        Album updatedAlbum = Album.builder()
                .id(10)
                .title("nowy")
                .build();

        Response response = patchResource(updatedAlbum, ResourcePath.ALBUMS.getPath());
        Album album = response.getBody().as(Album.class);

        assertThat(album.getId()).isEqualTo(updatedAlbum.getId());
    }

    @Test
    public void deleteComment() {
        checkDeleteResource(1, ResourcePath.COMMENTS.getPath());
    }
}
