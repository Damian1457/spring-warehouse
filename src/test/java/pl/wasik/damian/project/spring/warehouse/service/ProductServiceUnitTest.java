package pl.wasik.damian.project.spring.warehouse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.wasik.damian.project.spring.warehouse.mapper.ProductMapper;
import pl.wasik.damian.project.spring.warehouse.repository.ProductRepository;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Test")
public class ProductServiceUnitTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Milk";
    private static final byte[] PRODUCT_IMAGE = new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47};
    private static final String IMAGE_BASE64 = Base64.getEncoder().encodeToString(PRODUCT_IMAGE);

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private MultipartFile mockMultipartFile;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Given ProductDto and Image When CreateProduct Then Return Created ProductDto")
    void givenProductDtoAndImage_whenCreateProduct_thenReturnCreatedProductDto() throws IOException {
        // Given
        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setImage(PRODUCT_IMAGE);

        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(mockMultipartFile.getBytes()).thenReturn(PRODUCT_IMAGE);
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(productEntity);
        when(productMapper.toDto(any(ProductEntity.class))).thenReturn(productDto);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // When
        ProductDto createdProduct = productService.create(productDto, mockMultipartFile);

        //Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdProduct, "Expected created product to be not null"),
                () -> Assertions.assertEquals(PRODUCT_ID, createdProduct.getId(), "Product IDs are not the same"),
                () -> Assertions.assertEquals(PRODUCT_NAME, createdProduct.getName(), "Product names are not the same"),
                () -> Assertions.assertEquals(IMAGE_BASE64, createdProduct.getImageBase64(), "Product images are not the same")
        );
    }

    @Test
    @DisplayName("Given List of ProductEntities When GetAllProducts Then Return List of ProductDtos")
    void givenProductEntitiesList_whenGetAllProducts_thenReturnProductDtoList() {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setImage(PRODUCT_IMAGE);

        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);
        productDto.setImageBase64(IMAGE_BASE64);

        List<ProductEntity> productEntities = Collections.singletonList(productEntity);
        List<ProductDto> productDtos = Collections.singletonList(productDto);

        when(productRepository.findAll()).thenReturn(productEntities);
        when(productMapper.toDto(productEntity)).thenReturn(productDto);

        // When
        List<ProductDto> foundProducts = productService.findAll();

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundProducts, "Expected found products to be not null"),
                () -> Assertions.assertEquals(productDtos.size(), foundProducts.size(), "Expected list size to match"),
                () -> Assertions.assertEquals(productDtos.get(0), foundProducts.get(0), "Expected first product to match")
        );
    }

    @Test
    @DisplayName("Given ProductId When GetProductById Then Return ProductDto")
    void givenProductId_whenGetProductById_thenReturnProductDto() {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setImage(PRODUCT_IMAGE);

        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);
        productDto.setImageBase64(IMAGE_BASE64);

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(productEntity)).thenReturn(productDto);

        // When
        ProductDto foundProduct = productService.read(PRODUCT_ID);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(foundProduct, "Expected product to be present"),
                () -> Assertions.assertEquals(productDto, foundProduct, "Expected productDto to match the found productDto")
        );
    }

    @Test
    @DisplayName("Given ProductId, ProductDto and Image When UpdateProduct Then Return Updated ProductDto")
    void givenProductIdAndImage_whenUpdateProduct_thenReturnUpdatedProductDto() throws IOException {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setImage(PRODUCT_IMAGE);

        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);
        productDto.setImageBase64(IMAGE_BASE64);

        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(mockMultipartFile.getBytes()).thenReturn(PRODUCT_IMAGE);
        when(productMapper.toEntity(productDto)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toDto(productEntity)).thenReturn(productDto);

        // When
        ProductDto updatedProduct = productService.update(PRODUCT_ID, productDto, mockMultipartFile);

        // Then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(updatedProduct, "Expected updated product to be not null"),
                () -> Assertions.assertEquals(productDto.getId(), updatedProduct.getId(), "Expected updated product ID to match"),
                () -> Assertions.assertEquals(productDto.getName(), updatedProduct.getName(), "Expected updated product name to match"),
                () -> Assertions.assertEquals(productDto.getImageBase64(), updatedProduct.getImageBase64(), "Expected updated product image to match")
        );
    }

    @Test
    @DisplayName("Given ProductId When DeleteProductById Then Verify Deletion")
    void givenProductId_whenDeleteProductById_thenVerifyDeletion() {
        // Given
        Long productIdToDelete = PRODUCT_ID;

        // When
        productService.delete(productIdToDelete);

        // Then
        verify(productRepository).deleteById(productIdToDelete);
    }
}