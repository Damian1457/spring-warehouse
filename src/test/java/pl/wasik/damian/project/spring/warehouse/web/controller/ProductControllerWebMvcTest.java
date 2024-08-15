package pl.wasik.damian.project.spring.warehouse.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasik.damian.project.spring.warehouse.service.ProductService;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(ProductController.class)
@DisplayName("ProductController Unit Tests with WebMvcTest")
public class ProductControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private static final String LIST_VIEW = "product/list";
    private static final String CREATE_VIEW = "product/create";
    private static final String READ_VIEW = "product/read";
    private static final String UPDATE_VIEW = "product/update";

    @Test
    @DisplayName("givenProductsExist_whenGetAll_thenReturnsProductListView")
    void givenProductsExist_whenGetAll_thenReturnsProductListView() throws Exception {
        // Given
        List<ProductDto> products = Arrays.asList(
                new ProductDto(1L, "Product1", 10, "Description1", 100, 10.0, null),
                new ProductDto(2L, "Product2", 20, "Description2", 200, 20.0, null)
        );

        // When & Then
        when(productService.findAll()).thenReturn(products);
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_VIEW))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", products));
    }

    @Test
    @DisplayName("givenProductRequest_whenCreateView_thenReturnsCreateView")
    void givenProductRequest_whenCreateView_thenReturnsCreateView() throws Exception {
        // When & Then
        mockMvc.perform(get("/products/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_VIEW))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @DisplayName("givenProductData_whenCreate_thenRedirectToProducts")
    void givenProductData_whenCreate_thenRedirectToProducts() throws Exception {
        // Given
        ProductDto productDto = new ProductDto(1L, "Product1", 10, "Description1", 100, 10.0, null);
        MockMultipartFile image = new MockMultipartFile("image", "product-image.jpg", "image/jpeg", "test-image-content".getBytes());

        // When & Then
        when(productService.create(any(ProductDto.class), any())).thenReturn(productDto);
        mockMvc.perform(multipart("/products/create")
                        .file(image)
                        .param("name", "Product1")
                        .param("capacity", "10")
                        .param("description", "Description1")
                        .param("stock", "100")
                        .param("price", "10.0"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productService).create(any(ProductDto.class), any());
    }

    @Test
    @DisplayName("givenProductId_whenRead_thenReturnsReadView")
    public void givenProductId_whenRead_thenReturnsReadView() throws Exception {
        // Given
        ProductDto productDto = new ProductDto(1L, "Product1", 10, "Description1", 100, 10.0, null);

        // When & Then
        when(productService.read(anyLong())).thenReturn(productDto);
        mockMvc.perform(get("/products/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(READ_VIEW))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", productDto));
    }

    @Test
    @DisplayName("givenProductId_whenUpdateView_thenReturnsUpdateView")
    void givenProductId_whenUpdateView_thenReturnsUpdateView() throws Exception {
        // Given
        ProductDto productDto = new ProductDto(1L, "Product1", 10, "Description1", 100, 10.0, null);

        // When & Then
        when(productService.read(anyLong())).thenReturn(productDto);
        mockMvc.perform(get("/products/update/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(UPDATE_VIEW))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", productDto));
    }

    @Test
    @DisplayName("givenProductData_whenUpdate_thenRedirectToProducts")
    void givenProductData_whenUpdate_thenRedirectToProducts() throws Exception {
        // Given
        ProductDto productDto = new ProductDto(1L, "Product1", 10, "Description1", 100, 10.0, null);
        MockMultipartFile image = new MockMultipartFile("image", "product-image.jpg", "image/jpeg", "test-image-content".getBytes());

        // When & Then
        when(productService.update(anyLong(), any(ProductDto.class), any())).thenReturn(productDto);
        mockMvc.perform(multipart("/products/update/{id}", 1L)
                        .file(image)
                        .param("name", "Product1")
                        .param("capacity", "10")
                        .param("description", "Description1")
                        .param("stock", "100")
                        .param("price", "10.0"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productService).update(anyLong(), any(ProductDto.class), any());
    }

    @Test
    @DisplayName("givenProductId_whenDelete_thenRedirectToProducts")
    public void givenProductId_whenDelete_thenRedirectToProducts() throws Exception {
        // When & Then
        mockMvc.perform(post("/products/delete/{id}", 1L))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productService).delete(1L);
    }
}
