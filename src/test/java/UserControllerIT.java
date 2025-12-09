import com.app.App;
import com.app.app_user.AppUser;
import com.app.app_user.UserRepository;
import com.app.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = App.class
)
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private AppUser testUser;

    @BeforeEach
    void setUp() {
        // очищаем базу перед каждым тестом
        userRepository.deleteAll();

        testUser = new AppUser();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setRole(Role.ADMIN); // роль для базы
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);
    }

    // ---------- save user ----------
    @Test
    void save_shouldCreateUser() throws Exception {
        String json = """
                {
                  "username": "newUser",
                  "password": "1234",
                  "email": "email@mail.com"
                }
                """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Success Save"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    void findAll_shouldReturnUsers() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.data[0].username").value("testuser"));
    }

}
