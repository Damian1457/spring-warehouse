package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import pl.wasik.damian.project.spring.warehouse.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("UserController Unit Tests with WebMvcTest")
public class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String CREATE_VIEW = "user/create";

    @Test
    @DisplayName("givenUserRequest_whenCreateView_thenReturnsCreateView")
    public void givenUserRequest_whenCreateView_thenReturnsCreateView() throws Exception {
        // When & Then
        mockMvc.perform(get("/users/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_VIEW));
    }
}