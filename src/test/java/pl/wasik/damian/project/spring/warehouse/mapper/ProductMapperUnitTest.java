package pl.wasik.damian.project.spring.warehouse.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.wasik.damian.project.spring.warehouse.repository.entity.ProductEntity;
import pl.wasik.damian.project.spring.warehouse.web.model.ProductDto;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

@DisplayName("ProductMapper Unit Test")
class ProductMapperUnitTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Milk";
    private static final byte[] IMAGE_DATA = new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47,
            (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
    private static final String IMAGE_BASE64 = Base64.getEncoder().encodeToString(IMAGE_DATA);

    @Test
    @DisplayName("given ProductEntity when map to ProductDto then return ProductDto")
    void givenProductEntity_whenMapToDto_thenReturnProductDto() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        ProductMapper productMapper = new ProductMapper(modelMapper);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setImage(IMAGE_DATA);

        // When
        ProductDto productDto = productMapper.toDto(productEntity);

        // Then
        Assertions.assertAll("productDto",
                () -> Assertions.assertNotNull(productDto, "ProductDto should not be null"),
                () -> Assertions.assertEquals(PRODUCT_ID, productDto.getId(), "ID should match"),
                () -> Assertions.assertEquals(PRODUCT_NAME, productDto.getName(), "Name should match"),
                () -> Assertions.assertEquals(IMAGE_BASE64, productDto.getImageBase64(), "ImageBase64 should match")
        );
    }

    @Test
    @DisplayName("given ProductDto when map to ProductEntity then return ProductEntity")
    void givenProductDto_whenMapToEntity_thenReturnProductEntity() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        ProductMapper productMapper = new ProductMapper(modelMapper);
        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);
        productDto.setImageBase64(IMAGE_BASE64);

        // When
        ProductEntity productEntity = productMapper.toEntity(productDto);

        // Then
        Assertions.assertAll("productEntity",
                () -> Assertions.assertNotNull(productEntity, "ProductEntity should not be null"),
                () -> Assertions.assertEquals(PRODUCT_ID, productEntity.getId(), "ID should match"),
                () -> Assertions.assertEquals(PRODUCT_NAME, productEntity.getName(), "Name should match"),
                () -> Assertions.assertArrayEquals(IMAGE_DATA, productEntity.getImage(), "Image data should match")
        );
    }

    @Test
    @DisplayName("given List<ProductEntity> when map to List<ProductDto> then return List<ProductDto>")
    void givenProductEntityList_whenMapToDtoList_thenReturnProductDtoList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        ProductMapper productMapper = new ProductMapper(modelMapper);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setImage(IMAGE_DATA);
        List<ProductEntity> productEntities = Collections.singletonList(productEntity);

        // When
        List<ProductDto> productDtos = productMapper.toDtoList(productEntities);

        // Then
        Assertions.assertAll("productDtos",
                () -> Assertions.assertNotNull(productDtos, "ProductDtos should not be null"),
                () -> Assertions.assertEquals(1, productDtos.size(), "ProductDtos size should be 1"),
                () -> Assertions.assertEquals(PRODUCT_ID, productDtos.get(0).getId(), "ID should match"),
                () -> Assertions.assertEquals(PRODUCT_NAME, productDtos.get(0).getName(), "Name should match"),
                () -> Assertions.assertEquals(IMAGE_BASE64, productDtos.get(0).getImageBase64(), "ImageBase64 should match")
        );
    }

    @Test
    @DisplayName("given List<ProductDto> when map to List<ProductEntity> then return List<ProductEntity>")
    void givenProductDtoList_whenMapToEntityList_thenReturnProductEntityList() {
        // Given
        ModelMapper modelMapper = new ModelMapper();
        ProductMapper productMapper = new ProductMapper(modelMapper);
        ProductDto productDto = new ProductDto();
        productDto.setId(PRODUCT_ID);
        productDto.setName(PRODUCT_NAME);
        productDto.setImageBase64(IMAGE_BASE64);
        List<ProductDto> productDtos = Collections.singletonList(productDto);

        // When
        List<ProductEntity> productEntities = productMapper.toEntityList(productDtos);

        // Then
        Assertions.assertAll("productEntities",
                () -> Assertions.assertNotNull(productEntities, "ProductEntities should not be null"),
                () -> Assertions.assertEquals(1, productEntities.size(), "ProductEntities size should be 1"),
                () -> Assertions.assertEquals(PRODUCT_ID, productEntities.get(0).getId(), "ID should match"),
                () -> Assertions.assertEquals(PRODUCT_NAME, productEntities.get(0).getName(), "Name should match"),
                () -> Assertions.assertArrayEquals(IMAGE_DATA, productEntities.get(0).getImage(), "Image data should match")
        );
    }
}
