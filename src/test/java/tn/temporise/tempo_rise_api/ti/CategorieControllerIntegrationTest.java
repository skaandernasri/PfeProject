package tn.temporise.tempo_rise_api.ti;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tn.temporise.domain.model.CategorieRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
class CategorieControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @Order(1)
    void testCreateCategory() {
        CategorieRequest categorieRequest = new CategorieRequest();
        categorieRequest.setNom("Electronics");
        categorieRequest.setDescription("Gadgets and devices");

        given()
                .contentType(ContentType.JSON)
                .body(categorieRequest)
                .body(categorieRequest)
                .cookie("jwt", jwtToken)
                .when()
                .post("/v1/categories")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("nom", equalTo("Electronics"))
                .body("description", equalTo("Gadgets and devices"));

    }

    @Test
    @Order(2)
    void testGetAllCategories() {
        given()
                .cookie("jwt", jwtToken)
                .when()
                .get("/v1/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThanOrEqualTo(0)));
    }

    @Test
    @Order(3)
    void testGetCategoryById() {
        Long categoryId = 1L; // Remplacez par un ID existant dans votre base de données

        given()
                //.cookie("jwt", jwtToken)
                .when()
                .get("/v1/categories/" + categoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(categoryId.intValue()));
    }

    @Test
    @Order(4)
    void testUpdateCategory() {
        Long categoryId = 1L; // Remplacez par un ID existant dans votre base de données
        CategorieRequest categorieRequest = new CategorieRequest();
        categorieRequest.setNom("Updated Electronics");
        categorieRequest.setDescription("Updated description");

        given()
                .contentType(ContentType.JSON)
                .body(categorieRequest)
                .cookie("jwt", jwtToken)
                .when()
                .put("/v1/categories/" + categoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nom", equalTo("Updated Electronics"))
                .body("description", equalTo("Updated description"));
    }

    @Test
    @Order(5)
    void testDeleteCategory() {
        Long categoryId = 1L; // Remplacez par un ID existant dans votre base de données

        given()
                .cookie("jwt", jwtToken)
                .when()
                .delete("/v1/categories/" + categoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo("200"))
                .body("message", equalTo("Catégorie supprimé !"));
    }

    @Test
    @Order(6)
    void testDeleteAllCategories() {
        given()
                .cookie("jwt", jwtToken)
                .when()
                .delete("/v1/categories")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo("200"))
                .body("message", equalTo("Toutes les catégories ont été supprimés avec succès"));
    }
}