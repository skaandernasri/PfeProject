package tn.temporise.tempo_rise_api.ti;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tn.temporise.infrastructure.adapter.repository.UserRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    UserRepo userRepo;
//    @BeforeEach
//    void setUp() {
//        userRepo.deleteByEmail("test@example.com"); // Supprime l'utilisateur si déjà présent
//    }
    @Test
    void testRegisterUser() throws Exception {
        String userJson = """
            {   "nom": "test",
                "email": "test1@example.com",
                "password": "password123",
                "role": "CLIENT"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testAuthUser() throws Exception {
        String userJson = """
            {
                "email": "test@example.com",
                "password": "password123"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
