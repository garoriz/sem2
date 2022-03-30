package ru.kpfu.stud.garipov.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.stud.garipov.dto.UserDto;
import ru.kpfu.stud.garipov.model.User;
import ru.kpfu.stud.garipov.repository.UserRepository;
import ru.kpfu.stud.garipov.service.UserService;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void init() {
        User user = new User();
        user.setName("testName");
        given(userService.getAll()).willReturn(Collections.singletonList(UserDto.fromModel(user)));
        given(userService.getById(1)).willReturn(UserDto.fromModel(user));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("testName"));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("testName"));
    }
}
