package tn.temporise.tempo_rise_api.ti;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import lombok.extern.slf4j.Slf4j;
import tn.temporise.application.mapper.AuthMapper;
import tn.temporise.application.mapper.RegMapper;
import tn.temporise.config.TestUtils;
import tn.temporise.domain.model.Categorie;
import tn.temporise.domain.model.Produit;
import tn.temporise.domain.model.UtilisateurModel;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.domain.port.CategorieRepo;
import tn.temporise.domain.port.ProductRepo;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.AuthentificationEntity;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.UtilisateurEntity;
import tn.temporise.infrastructure.security.utils.JwtUtil;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {
    @Autowired
    private CategorieRepo categorieRepo;
    @Autowired
    private ProductRepo productRepo;
    public static Long productId;
    @LocalServerPort
    protected Integer port;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    protected UserRepo userRepo;
    @Autowired
    protected AuthRepo authRepo;
    @Autowired
    protected RegMapper regMapper;
    @Autowired
    protected AuthMapper authMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    protected static Long categoryId;
    protected static String jwtToken;
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.10")
            .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        RestAssured.config = RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> objectMapper));

        RestAssured.defaultParser = Parser.JSON;
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

        // Create and save a user
        UtilisateurEntity user = createUser("test@example.com", "password123", Role.ADMIN);
        UtilisateurModel savedUserModel = userRepo.save(regMapper.entityToModel(user));

        // Create and save authentication
        AuthentificationEntity newAuth = createAuthentication(savedUserModel, "password123");
        authRepo.save(authMapper.entityToModel(newAuth));

        // Generate JWT token
        jwtToken = TestUtils.generateTestJwtToken(jwtUtil,user.getEmail());

        // Create and save a category
        Categorie categorie = createCategory("Electronics", "Gadgets and devices");
        Categorie savedCategorie = categorieRepo.save(categorie);
        categoryId = savedCategorie.id();

        // Create and save a product
        Produit produit = createProduct("Smartphone", "High-end smartphone", 999.99, 10L, savedCategorie);
        Produit savedProd = productRepo.save(produit);
        productId = savedProd.id();
    }

    private UtilisateurEntity createUser(String email, String password, Role role) {
        UtilisateurEntity user = new UtilisateurEntity(email, role);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

    private AuthentificationEntity createAuthentication(UtilisateurModel userModel, String password) {
        UtilisateurEntity userEntity = regMapper.modelToEntity(userModel);
        AuthentificationEntity newAuth = new AuthentificationEntity();
        newAuth.setUser(userEntity);
        newAuth.setPassword(passwordEncoder.encode(password));
        newAuth.setType(TypeAuthentification.EMAIL);
        newAuth.setProviderId("0");
        return newAuth;
    }

    private Categorie createCategory(String nom, String description) {
        return new Categorie(null, nom, description);
    }

    private Produit createProduct(String nom, String description, double prix, long stock, Categorie categorie) {
        return new Produit(null, nom, description, prix, stock, null, categorie, 0);
    }

    @AfterEach
    void setUpAfterEach() {
        userRepo.deleteAll();
        categorieRepo.deleteAll();
        productRepo.deleteAll();
    }
}