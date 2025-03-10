package tn.temporise.tempo_rise_api.ti;

import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import tn.temporise.domain.model.Response;
import tn.temporise.domain.model.TokenResponse;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AuthIntegrationTest {

    @Autowired
    private UserRepo utilisateurRepository; // For interacting with the database

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:13.10"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        utilisateurRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    @Order(1)
    void testSignupUser_Success() {
        // Prepare the request body
        String requestBody = """
            {
                "email": "test@example.com",
                "password": "password123"
            }
        """;

        // Prepare the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the HTTP entity
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Perform the HTTP request
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/signup")
                .then()
                .extract()
                .as(Response.class);

        // Validate the response
        assertNotNull(response.getMessage());
        assertEquals("201", response.getCode());
        assertEquals("utilisateur crée avec succés", response.getMessage());

        // Validate the database state
        UtilisateurEntity user = utilisateurRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    @Order(2)
    void testSigninUser_Success() {
        // First, sign up a user
        String email = "test@example.com";
        String password = "password123";
        UtilisateurEntity user = new UtilisateurEntity();
        user.setEmail(email);
        user.setPassword(password);
        utilisateurRepository.save(user);

        // Prepare the request body
        String requestBody = """
            {
                "email": "test@example.com",
                "password": "password123"
            }
        """;

        // Perform the HTTP request
        TokenResponse response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/signin")
                .then()
                .extract()
                .as(TokenResponse.class);

        // Validate the response
        assertNotNull(response.getToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    @Order(3)
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
                .post("/api/signin")
                .then()
                .extract()
                .as(Response.class);

        // Validate the response
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
    }

    @Test
    @Order(4)
    void testLogoutUser_Success() {
        // Perform the HTTP request
        Response response = RestAssured.given()
                .contentType("application/json")
                .when()
                .post("/api/logout")
                .then()
                .extract()
                .as(Response.class);

        // Validate the response
        assertNotNull(response.getMessage());
        assertEquals("200", response.getCode());
        assertEquals("Déconnecter avec succés", response.getMessage());
    }
}