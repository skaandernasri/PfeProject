package tn.temporise.tempo_rise_api.ti;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tn.temporise.domain.model.ProductRequest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class ProductControllerIntegrationTest extends BaseIntegrationTest {


    @Test
    @Order(1)
    void testCreateProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setNom("Smartphone");
        productRequest.setDescription("High-end smartphone");
        productRequest.setPrix(999.99);
        productRequest.setStock(10L);
        productRequest.setCategorie(categoryId);
        given()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .cookie("jwt", jwtToken)
                .when()
                .post("/v1/produits")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("nom", equalTo("Smartphone"))
                .body("description", equalTo("High-end smartphone"));
    }

    @Test
    @Order(2)
    void testGetAllProducts() {
        given()
                .cookie("jwt", jwtToken)
                .when()
                .get("/v1/produits")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThanOrEqualTo(0)));
    }

    @Test
    @Order(2)
    void testGetProductById() {

        given()
                .cookie("jwt", jwtToken)
                .when()
                .get("/v1/produits/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(productId.intValue()));
    }

    @Test
    @Order(3)
    void testUpdateProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setNom("Updated Smartphone");
        productRequest.setDescription("Updated description");
        productRequest.setPrix(899.99);
        productRequest.setStock(5L);
        productRequest.setCategorie(categoryId);

        given()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .cookie("jwt", jwtToken)
                .when()
                .put("/v1/produits/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("nom", equalTo("Updated Smartphone"))
                .body("description", equalTo("Updated description"));
    }

    @Test
    @Order(4)
    void testDeleteProduct() {
     // Remplacez par un ID existant dans votre base de données

        given()
                .cookie("jwt", jwtToken)
                .when()
                .delete("/v1/produits/" + productId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo("200"))
                .body("message", equalTo("Produit supprimé !"));
    }

    @Test
    @Order(5)
    void testDeleteAllProducts() {
        given()
                .cookie("jwt", jwtToken)
                .when()
                .delete("/v1/produits")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo("200"))
                .body("message", equalTo("Tous les produits ont été supprimés avec succès"));
    }
}