package tn.temporise.tempo_rise_api.ti;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.parsing.Parser;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import lombok.extern.slf4j.Slf4j;
import tn.temporise.config.TestUtils;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.infrastructure.security.utils.JwtUtil;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    @LocalServerPort
    protected Integer port;
    @Autowired
    private JwtUtil jwtUtil; // Inject JwtUtil to generate tokens
    @Autowired
    protected UserRepo userRepo;
    @Autowired
    protected AuthRepo authRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    protected static String jwtToken;
    // PostgreSQL container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.10")
            .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        // Start the PostgreSQL container
        postgres.start();
        // Configure RestAssured with a custom ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RestAssured.config = RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> objectMapper));

        // Set the default parser to JSON
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    static void afterAll() {
        // Stop the PostgreSQL container
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // Register dynamic properties for the PostgreSQL container
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:" + port;
        UtilisateurEntity user=new UtilisateurEntity("testuser@example.com", Role.ADMIN);
        userRepo.save(user);
        AuthentificationEntity newAuth = new AuthentificationEntity();
        newAuth.setUser(user);
        newAuth.setPassword(passwordEncoder.encode(user.getPassword()));
        newAuth.setType(TypeAuthentification.EMAIL);
        newAuth.setProviderId("0");
        authRepo.save(newAuth);
        jwtToken = TestUtils.generateTestJwtToken(jwtUtil);


    }
    @AfterEach()
    void setUpAfterEach(){
        userRepo.deleteAll();
    }
}