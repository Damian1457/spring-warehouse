package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasik.damian.project.spring.warehouse.service.AddressService;
import pl.wasik.damian.project.spring.warehouse.web.model.AddressDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
    private static final String CREATE_VIEW = "address/create";
    private static final String READ_VIEW = "address/read";
    private static final String UPDATE_VIEW = "address/update";


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

    @Test
    @DisplayName("givenAddressRequest_whenCreateView_thenReturnsCreateView")
    void givenAddressRequest_whenCreateView_thenReturnsCreateView() throws Exception {
        // When & Then
        mockMvc.perform(get("/addresses/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_VIEW))
                .andExpect(model().attributeExists("address"));
    }


    @Test
    @DisplayName("givenAddressData_whenCreate_thenRedirectToAddresses")
    void givenAddressData_whenCreate_thenRedirectToAddresses() throws Exception {
        // Given
        AddressDto addressDto = new AddressDto(1L, "Powstancow", "Warka", "00000", "21");

        //When & Then
        when(addressService.create(any(AddressDto.class))).thenReturn(addressDto);
        mockMvc.perform(post("/addresses/create")
                        .param("street", "Powstancow")
                        .param("city", "Warka")
                        .param("postalCode", "00000")
                        .param("houseNumber", "21"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(addressService).create(any(AddressDto.class));
    }

    @Test
    @DisplayName("givenAddressId_whenRead_thenReturnsReadView")
    public void givenAddressId_whenRead_thenReturnsReadView() throws Exception {
        // Given
        AddressDto addressDto = new AddressDto(1L, "Powstancow", "Warka", "00000", "21");

        // When & Then
        when(addressService.read(anyLong())).thenReturn(addressDto);
        mockMvc.perform(get("/addresses/read/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(READ_VIEW))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attribute("address", addressDto));
    }

    @Test
    @DisplayName("givenAddressId_whenUpdateView_thenReturnsUpdateView")
    void givenAddressId_whenUpdateView_thenReturnsUpdateView() throws Exception {
        // Given
        AddressDto addressDto = new AddressDto(1L, "Powstancow", "Warka", "00000", "21");

        // When & Then
        when(addressService.read(anyLong())).thenReturn(addressDto);
        mockMvc.perform(get("/addresses/update/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_VIEW))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attribute("address", addressDto));
    }

    @Test
    @DisplayName("givenAddressData_whenUpdate_thenRedirectToAddresses")
    void givenUserData_whenUpdate_thenRedirectToUsers() throws Exception {
        // Given
        AddressDto addressDto = new AddressDto(1L, "Powstancow", "Warka", "00000", "21");

        // When & Then
        when(addressService.update(anyLong(), any(AddressDto.class))).thenReturn(addressDto);
        mockMvc.perform(post("/addresses/update/{id}", 1L)
                        .param("street", "Powstancow")
                        .param("city", "Warka")
                        .param("postalCode", "00000")
                        .param("houseNumber", "21"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(addressService).update(anyLong(), any(AddressDto.class));
    }

    @Test
    @DisplayName("givenAddressId_whenDelete_thenRedirectToAddresses")
    public void givenAddressId_whenDelete_thenRedirectToAddresses() throws Exception {
        // When & Then
        mockMvc.perform(post("/addresses/delete/{id}", 1L))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/addresses"));

        verify(addressService).delete(1L);
    }
}
