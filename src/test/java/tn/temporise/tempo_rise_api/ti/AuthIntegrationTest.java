package tn.temporise.tempo_rise_api.ti;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.domain.model.Response;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class AuthIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserRepo utilisateurRepository;
    @Autowired
    private AuthRepo authRepo;
    private static String jwtToken;
    @Autowired
    private RegMapper regMapper;
    @Autowired
    private AuthMapper authMapper;

    @Test
    @Order(1)
    void testSignupUser_Success() {
        String requestBody = """
        {
            "email": "test123@example.com",
            "password": "skandeR123",
            "nom":"skander",
            "role":"CLIENT"
        }
    """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/auth/signup")
                .then()
                .extract()
                .as(Response.class);

        assertNotNull(response.getMessage());
        log.info("Response: {}", response);
        assertEquals("201", response.getCode());
        assertEquals("utilisateur crée avec succés", response.getMessage());

        UtilisateurModel user = utilisateurRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(user);
        assertEquals("test@example.com", user.email());
    }
    @Test
    @Order(2)
    void testSignupUser_Failed() {
        String requestBody = """
        {
            "email": "test@example.com",
            "password": "skandeR123",
            "nom":"skander",
            "role":"CLIENT"
        }
    """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/auth/signup")
                .then()
                .extract()
                .as(Response.class);

        assertNotNull(response.getMessage());
        log.info("Response: {}", response);
        assertEquals("5647", response.getCode());
        UtilisateurModel user = utilisateurRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(user);
    }

    @Test
    @Order(3)
    void testSigninUser_Success() {
        String requestBody = """
    {
        "email": "test@example.com",
        "password": "password123"
    }
    """;

        ValidatableResponse response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/v1/auth/signin")
                .then()
                .log().all();

        jwtToken = extractJwtToken(response);
        assertNotNull(jwtToken, "JWT token should not be null!");

        Response responseBody = response.extract().as(Response.class);
        assertNotNull(responseBody.getMessage());
        assertEquals("200", responseBody.getCode());
        assertEquals("Connecté avec succés", responseBody.getMessage());
    }

    private String extractJwtToken(ValidatableResponse response) {
        return response.extract().cookie("jwt");
    }

    @Test
    @Order(4)
    void testSigninUser_InvalidCredentials() {
        String requestBody = """
            {
                "email": "test@example.com",
                "password": "wrong-password"
            }
        """;

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/v1/auth/signin")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(Response.class);

        assertEquals("6000", response.getCode());
    }

    @Test
    @Order(5)
    void testLogoutUser_Success() {
        assertNotNull(jwtToken, "JWT token must be set from the signin test!");

        Response response = RestAssured.given()
                .contentType("application/json")
                .cookie("jwt", jwtToken)
                .when()
                .post("/v1/auth/logout")
                .then()
                .extract()
                .as(Response.class);

        assertNotNull(response.getMessage());
        assertEquals("200", response.getCode());
        assertEquals("Déconnecter avec succés", response.getMessage());
    }
    @Test
    @Order(6)
    void testLogoutUser_Failed() {
        Response response = RestAssured.given()
                .contentType("application/json")
                .cookie("jwt", null)
                .when()
                .post("/v1/auth/logout")
                .then()
                .extract()
                .as(Response.class);
        assertEquals(null, response.getCode());
    }
}