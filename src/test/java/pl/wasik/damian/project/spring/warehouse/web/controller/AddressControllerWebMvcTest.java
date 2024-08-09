package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasik.damian.project.spring.warehouse.service.AddressService;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AddressController.class)
@DisplayName("AddressController Unit Tests with WebMvcTest")
public class AddressControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    private static final String LIST_VIEW = "address/list";

    @Test
    @DisplayName("givenAddressesExist_whenGetAll_thenReturnsAddressListView")
    void givenAddressesExist_whenGetAll_thenReturnsAddressListView() throws Exception {
        // Given
        List<AddressDto> addresses = Arrays.asList(
                new AddressDto(1L, "Powstancow", "Warka", "00000", "21"),
                new AddressDto(2L, "Gagarina", "Warszawa", "0002010", "6")
        );

        // When & Then
        when(addressService.getAll()).thenReturn(addresses);
        mockMvc.perform(get("/addresses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_VIEW))
                .andExpect(model().attributeExists("addresses"))
                .andExpect(model().attribute("addresses", addresses));
    }
}
