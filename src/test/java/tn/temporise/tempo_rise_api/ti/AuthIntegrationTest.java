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
    private UserRepo utilisateurRepository; // For interacting with the database
    @Autowired
    private AuthRepo authRepo; // For interacting with the database
    private static String jwtToken;
    @Autowired
    private RegMapper regMapper;
    @Autowired
    private AuthMapper authMapper;

    @Test
    @Order(1)
    void testSignupUser_Success() {
        utilisateurRepository.deleteAll();
        // Prepare the request body
        String requestBody = """
            {
                        "email": "test@example.com",
                        "password": "skandeR123",
                        "nom":"skander",
                        "role":"CLIENT"
                    }
        """;

        // Prepare the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the HTTP entity
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Perform the HTTP request
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/v1/auth/signup")
                .then()
                .extract()
                .as(Response.class);

        // Validate the response
        assertNotNull(response.getMessage());
        log.info("------------------------------response ------- : "+ response);
        assertEquals("201", response.getCode());
        assertEquals("utilisateur crée avec succés", response.getMessage());

        // Validate the database state
        UtilisateurModel user = utilisateurRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(user);
        assertEquals("test@example.com", user.email());
    }

    @Test
    @Order(2)
    void testSigninUser_Success() {
        // First, sign up a user
        String email = "test@example.com";
        String rawPassword = "password123";

        // Encode the password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Create and save UtilisateurEntity
        UtilisateurEntity user = new UtilisateurEntity();
        user.setEmail(email);
        user.setPassword(encodedPassword); // Store the encoded password
        UtilisateurModel utilisateurModel = regMapper.entityToModel(user);
        UtilisateurModel savedUserModel = utilisateurRepository.save(utilisateurModel); // Save UtilisateurModel

        // Map the saved UtilisateurModel back to UtilisateurEntity
        UtilisateurEntity savedUserEntity = regMapper.modelToEntity(savedUserModel);

        // Create and save AuthentificationEntity
        AuthentificationEntity newAuth = new AuthentificationEntity();
        newAuth.setUser(savedUserEntity); // Use the saved UtilisateurEntity
        newAuth.setPassword(encodedPassword);
        newAuth.setType(TypeAuthentification.EMAIL);
        newAuth.setProviderId("0");
        authRepo.save(authMapper.entityToModel(newAuth)); // Save AuthentificationEntity

        // Prepare the request body (password remains raw here)
        String requestBody = """
        {
            "email": "test@example.com",
            "password": "password123"
        }
    """;

        // Perform the HTTP request and extract the JWT cookie
        ValidatableResponse response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/v1/auth/signin")
                .then()
                .log().all(); // Log the response for debugging

        // Extract JWT token from cookies
        jwtToken = response.extract().cookie("jwt");  // Get JWT from cookies
        assertNotNull(jwtToken, "JWT token should not be null!");

        // Validate the response
        Response responseBody = response.extract().as(Response.class);
        assertNotNull(responseBody.getMessage());
        assertEquals("200", responseBody.getCode());
        assertEquals("Connecté avec succés", responseBody.getMessage());
    }

    @Test
    @Order(4)
    void testSigninUser_InvalidCredentials() {
        // Prepare the request body with invalid credentials
        String requestBody = """
            {
                "email": "test@example.com",
                "password": "wrong-password"
            }
        """;

        // Perform the HTTP request
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/v1/auth/signin")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value()) // Ensure the status code is 401
                .contentType(ContentType.JSON) // Ensure the response has a JSON content type
                .extract()
                .as(Response.class);

        // Validate the response
        assertEquals("6000", response.getCode());
    }

    @Test
    @Order(3)
    void testLogoutUser_Success() {
        assertNotNull(jwtToken, "JWT token must be set from the signin test!");
        // Perform the HTTP request
        Response response = RestAssured.given()
                .contentType("application/json")
                .cookie("jwt", jwtToken)
                .when()
                .post("/v1/auth/logout")
                .then()
                .extract()
                .as(Response.class);

        // Validate the response
        assertNotNull(response.getMessage());
        assertEquals("200", response.getCode());
        assertEquals("Déconnecter avec succés", response.getMessage());
    }
}