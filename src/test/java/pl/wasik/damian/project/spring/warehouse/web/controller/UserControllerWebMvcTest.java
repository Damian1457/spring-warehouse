package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasik.damian.project.spring.warehouse.service.UserService;
import pl.wasik.damian.project.spring.warehouse.web.model.UserDto;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@DisplayName("UserController Unit Tests with WebMvcTest")
public class UserControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String LIST_VIEW = "user/list";
    private static final String CREATE_VIEW = "user/create";
    private static final String READ_VIEW = "user/read";
    private static final String UPDATE_VIEW = "user/update";

    @Test
    @DisplayName("givenUsersExist_whenGetAll_thenReturnsUserListView")
    void givenUsersExist_whenGetAll_thenReturnsUserListView() throws Exception {
        // Given
        List<UserDto> users = Arrays.asList(
                new UserDto(1L, "1234567890", "user1@example.com", "123456789", null),
                new UserDto(2L, "0987654321", "user2@example.com", "987654321", null)
        );

        // When & Then
        when(userService.getAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_VIEW))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    @DisplayName("givenUserRequest_whenCreateView_thenReturnsCreateView")
    void givenUserRequest_whenCreateView_thenReturnsCreateView() throws Exception {
        // When & Then
        mockMvc.perform(get("/users/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_VIEW))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("givenUserData_whenCreate_thenRedirectToUsers")
    void givenUserData_whenCreate_thenRedirectToUsers() throws Exception {
        // Given
        UserDto userDto = new UserDto(1L, "1234567890", "test@example.com", "123456789", null);

        // When & Then
        when(userService.create(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post("/users/create")
                        .param("nip", "1234567890")
                        .param("email", "test@example.com")
                        .param("phoneNumber", "123456789"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).create(any(UserDto.class));
    }

    @Test
    @DisplayName("givenUserId_whenRead_thenReturnsReadView")
    public void givenUserId_whenRead_thenReturnsReadView() throws Exception {
        // Given
        UserDto userDto = new UserDto(1L, "1234567890", "test@example.com", "123456789", null);

        // When & Then
        when(userService.read(anyLong())).thenReturn(userDto);
        mockMvc.perform(get("/users/read/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(READ_VIEW))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", userDto));
    }

    @Test
    @DisplayName("givenUserId_whenUpdateView_thenReturnsUpdateView")
    void givenUserId_whenUpdateView_thenReturnsUpdateView() throws Exception {
        // Given
        UserDto userDto = new UserDto(1L, "1234567890", "test@example.com", "123456789", null);

        // When & Then
        when(userService.read(anyLong())).thenReturn(userDto);
        mockMvc.perform(get("/users/update/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_VIEW))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", userDto));
    }

    @Test
    @DisplayName("givenUserData_whenUpdate_thenRedirectToUsers")
    void givenUserData_whenUpdate_thenRedirectToUsers() throws Exception {
        // Given
        UserDto userDto = new UserDto(1L, "1234567890", "test@example.com", "123456789", null);

        // When & Then
        when(userService.update(anyLong(), any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post("/users/update/{id}", 1L)
                        .param("id", "1")
                        .param("nip", "1234567890")
                        .param("email", "test@example.com")
                        .param("phoneNumber", "123456789"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).update(anyLong(), any(UserDto.class));
    }

    @Test
    @DisplayName("givenUserId_whenDelete_thenRedirectToUsers")
    public void givenUserId_whenDelete_thenRedirectToUsers() throws Exception {
        // When & Then
        mockMvc.perform(post("/users/delete/{id}", 1L))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        verify(userService).delete(1L);
    }
}