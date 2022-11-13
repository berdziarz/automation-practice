package rest;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.berdzik.rest.Resource;
import org.testng.annotations.BeforeClass;

@Epic("Rest API tests")
public abstract class RestApiTests {

    protected static final String EMAIL_REGEX = ".+@.+\\.[a-zA-Z]+";

    protected RequestSpecification requestSpecification;

    @BeforeClass
    public void setUp() {
        requestSpecification = RestAssured.given()
                .baseUri("https://jsonplaceholder.typicode.com");
    }

    protected Response getAllResources(String resourcePath) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .when()
                .get(resourcePath)
                .then()
                .statusCode(200)
                .extract().response();
    }

    protected Response getResourcesById(long resourceId, String resourcePath) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .pathParam("id", resourceId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract().response();
    }

    protected Response getResourceByUserId(long userId, String resourcePath) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .queryParam("userId", userId)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();
    }

    protected Response createNewResource(Resource resource, String resourcePath) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .body(resource)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().response();
    }

    protected Response updateResource(Resource resource, String resourcePath) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .pathParam("id", resource.getId())
                .body(resource)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .extract().response();
    }

    protected Response patchResource(Resource resource, String resourcePath) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .pathParam("id", resource.getId())
                .body(resource)
                .when()
                .patch("/{id}")
                .then()
                .statusCode(200)
                .extract().response();
    }


    protected void checkGetNonExistResource(long resourceId, String resourcePath) {
        RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .pathParam("id", resourceId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404)
                .extract().response();
    }

    protected void checkDeleteResource(long resourceId, String resourcePath) {
        RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(resourcePath)
                .pathParam("id", resourceId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .extract().response();
    }
}
