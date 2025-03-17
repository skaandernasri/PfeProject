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
        ProductRequest productRequest = createProductRequest("Smartphone", "High-end smartphone", 999.99, 10L, categoryId);

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
    @Order(3)
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
    @Order(4)
    void testUpdateProduct() {
        ProductRequest productRequest = createProductRequest("Updated Smartphone", "Updated description", 899.99, 5L, categoryId);

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
    @Order(5)
    void testDeleteProduct() {
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
    @Order(6)
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
    private ProductRequest createProductRequest(String nom, String description, double prix, long stock, Long categorieId) {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setNom(nom);
        productRequest.setDescription(description);
        productRequest.setPrix(prix);
        productRequest.setStock(stock);
        productRequest.setCategorie(categorieId);
        return productRequest;
    }
}