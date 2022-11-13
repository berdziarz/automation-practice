package rest;

import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.berdzik.rest.Photo;
import org.berdzik.rest.ResourcePath;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Photos tests")
public class PhotosTests extends RestApiTests {

    @Test
    public void getAllPhotos() {
        Response response = getAllResources(ResourcePath.PHOTOS.getPath());
        Photo[] photos = response.getBody().as(Photo[].class);

        assertThat(photos).hasSize(5000);
        assertThat(photos).allSatisfy(e ->
                assertThat(e.getUrl()).as("Photo with id=" + e.getId() + " url assertion")
                        .startsWith("https://via.placeholder.com/"));
    }

    @Test
    public void getPhotoById() {
        Photo expectedPhoto = Photo.builder()
                .id(1)
                .albumId(1)
                .title("accusamus beatae ad facilis cum similique qui sunt")
                .url("https://via.placeholder.com/600/92c952")
                .thumbnailUrl("https://via.placeholder.com/150/92c952").build();

        Response response = getResourcesById(1, ResourcePath.PHOTOS.getPath());
        Photo photo = response.getBody().as(Photo.class);

        assertThat(photo)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(expectedPhoto);
    }

    @Test
    public void getNonExistPhoto() {
        checkGetNonExistResource(99999, ResourcePath.PHOTOS.getPath());
    }

    @Test
    public void getPhotoByAlbumId() {
        long albumId = 1;

        Response response = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(ResourcePath.PHOTOS.getPath())
                .queryParam("albumId", albumId)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        Photo[] photos = response.getBody().as(Photo[].class);

        assertThat(photos).hasSize(50);
        assertThat(photos).allSatisfy(e -> assertThat(e.getAlbumId()).isEqualTo(albumId));
    }

    @Test
    public void createNewPhoto() {
        Photo newPhoto = Photo.builder()
                .albumId(2)
                .title("photo")
                .url("https://via.placeholder.com/000/000000")
                .thumbnailUrl("https://via.placeholder.com/111/22222")
                .build();

        Response response = createNewResource(newPhoto, ResourcePath.PHOTOS.getPath());
        Photo photo = response.getBody().as(Photo.class);

        assertThat(photo.getId()).isEqualTo(5001L);
    }


    @Test
    public void updatePhoto() {
        Photo updatedPhoto = Photo.builder()
                .id(1)
                .albumId(10)
                .title("photo")
                .url("https://via.placeholder.com/000/000000")
                .thumbnailUrl("https://via.placeholder.com/111/22222")
                .build();

        Response response = updateResource(updatedPhoto, ResourcePath.PHOTOS.getPath());
        Photo photo = response.getBody().as(Photo.class);

        assertThat(photo.getId()).isEqualTo(updatedPhoto.getId());
    }

    @Test
    public void patchPhoto() {
        Photo updatedPhoto = Photo.builder()
                .id(1)
                .url("https://via.placeholder.com/000/000000")
                .thumbnailUrl("https://via.placeholder.com/111/22222")
                .build();

        Response response = patchResource(updatedPhoto, ResourcePath.PHOTOS.getPath());
        Photo photo = response.getBody().as(Photo.class);

        assertThat(photo.getId()).isEqualTo(updatedPhoto.getId());
    }

    @Test
    public void deleteComment() {
        checkDeleteResource(1, ResourcePath.PHOTOS.getPath());
    }
}
