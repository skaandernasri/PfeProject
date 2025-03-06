//package tn.temporise.tempo_rise_api.ti;
//
//import io.restassured.RestAssured;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.testcontainers.containers.PostgreSQLContainer;
//import tn.temporise.domain.port.AuthRepo;
//import tn.temporise.domain.port.UserRepo;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@Transactional
//public class AuthIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    UserRepo userRepo;
//    @Autowired
//    AuthRepo authRepo;
//    @LocalServerPort
//    private Integer port;
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
//            "postgres:13.10"
//    );
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @BeforeAll
//    static void beforeAll() {
//        postgres.start();
//    }
//    @AfterAll
//    static void afterAll() {
//        postgres.stop();
//    }
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//    @BeforeEach
//    void setUp() {
//        RestAssured.baseURI = "http://localhost:" + port;
//        userRepo.deleteAll();
//        authRepo.deleteAll();
//    }
//
//    @Test
//    @Order(1)
//    void testRegisterUser() throws Exception {
//        String userJson = """
//            {   "nom": "test",
//                "email": "test@example.com",
//                "password": "password123",
//                "role": "CLIENT"
//            }
//        """;
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
////    @Test
////    @Order(2)
////    void testAuthUser() throws Exception {
////        String userJson = """
////            {
////                "email": "test@example.com",
////                "password": "password123"
////            }
////        """;
////
////        mockMvc.perform(MockMvcRequestBuilders.post("/login")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(userJson))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////    }
//}
