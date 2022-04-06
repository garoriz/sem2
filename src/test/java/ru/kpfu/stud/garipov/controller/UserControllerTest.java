package ru.kpfu.stud.garipov.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.stud.garipov.dto.CreateUserDto;
import ru.kpfu.stud.garipov.dto.UserDto;
import ru.kpfu.stud.garipov.model.User;
import ru.kpfu.stud.garipov.repository.UserRepository;
import ru.kpfu.stud.garipov.Application;
import ru.kpfu.stud.garipov.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Before
    public void init() {
        User user = new User();
        user.setEmail("rg@test.ru");
        user.setName("Rizvan");
        user.setPassword("12345678");
        user.setVerificationCode("1234");
        userRepository.save(user);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Rizvan"));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("Rizvan"));
    }

    @Test
    public void testSignUp() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("Rizvan");
        createUserDto.setPassword("12345678");
        createUserDto.setEmail("rizvan@test.com");
        given(userService.signUp(any(CreateUserDto.class), anyString())).willReturn(new UserDto(
                1,
                "Rizvan",
                "rizvan@test.com",
                "12345678"
        ));
        mockMvc.perform(post("/sign_up")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("user", createUserDto)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("sign_up_success"));
    }
}